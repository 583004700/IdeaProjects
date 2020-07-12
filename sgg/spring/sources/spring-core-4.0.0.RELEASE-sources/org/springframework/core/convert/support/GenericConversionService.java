package org.springframework.core.convert.support;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class GenericConversionService implements ConfigurableConversionService {

	private static final GenericConverter NO_OP_CONVERTER = new NoOpConverter("NO_OP");

	private static final GenericConverter NO_MATCH = new NoOpConverter("NO_MATCH");

	private final Converters converters = new Converters();

	private final Map<ConverterCacheKey, GenericConverter> converterCache =
			new ConcurrentHashMap<ConverterCacheKey, GenericConverter>(64);

	@Override
	public void addConverter(Converter<?, ?> converter) {
		GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converter, Converter.class);
		Assert.notNull(typeInfo, "Unable to the determine sourceType <S> and targetType " +
				"<T> which your Converter<S, T> converts between; declare these generic types.");
		addConverter(new ConverterAdapter(typeInfo, converter));
	}

	@Override
	public void addConverter(Class<?> sourceType, Class<?> targetType, Converter<?, ?> converter) {
		GenericConverter.ConvertiblePair typeInfo = new GenericConverter.ConvertiblePair(sourceType, targetType);
		addConverter(new ConverterAdapter(typeInfo, converter));
	}

	@Override
	public void addConverter(GenericConverter converter) {
		this.converters.add(converter);
		invalidateCache();
	}

	@Override
	public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
		GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converterFactory, ConverterFactory.class);
		if (typeInfo == null) {
			throw new IllegalArgumentException("Unable to the determine sourceType <S> and " +
					"targetRangeType R which your ConverterFactory<S, R> converts between; " +
					"declare these generic types.");
		}
		addConverter(new ConverterFactoryAdapter(typeInfo, converterFactory));
	}

	@Override
	public void removeConvertible(Class<?> sourceType, Class<?> targetType) {
		this.converters.remove(sourceType, targetType);
		invalidateCache();
	}

	@Override
	public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
		Assert.notNull(targetType, "The targetType to convert to cannot be null");
		return canConvert(sourceType != null ?
				TypeDescriptor.valueOf(sourceType) : null,
				TypeDescriptor.valueOf(targetType));
	}

	@Override
	public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
		Assert.notNull(targetType,"The targetType to convert to cannot be null");
		if (sourceType == null) {
			return true;
		}
		GenericConverter converter = getConverter(sourceType, targetType);
		return (converter != null);
	}

	
	public boolean canBypassConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
		Assert.notNull(targetType, "The targetType to convert to cannot be null");
		if (sourceType == null) {
			return true;
		}
		GenericConverter converter = getConverter(sourceType, targetType);
		return (converter == NO_OP_CONVERTER);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T convert(Object source, Class<T> targetType) {
		Assert.notNull(targetType,"The targetType to convert to cannot be null");
		return (T) convert(source, TypeDescriptor.forObject(source), TypeDescriptor.valueOf(targetType));
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		Assert.notNull(targetType,"The targetType to convert to cannot be null");
		if (sourceType == null) {
			Assert.isTrue(source == null, "The source must be [null] if sourceType == [null]");
			return handleResult(sourceType, targetType, convertNullSource(sourceType, targetType));
		}
		if (source != null && !sourceType.getObjectType().isInstance(source)) {
			throw new IllegalArgumentException("The source to convert from must be an instance of " +
					sourceType + "; instead it was a " + source.getClass().getName());
		}
		GenericConverter converter = getConverter(sourceType, targetType);
		if (converter != null) {
			Object result = ConversionUtils.invokeConverter(converter, source, sourceType, targetType);
			return handleResult(sourceType, targetType, result);
		}
		return handleConverterNotFound(source, sourceType, targetType);
	}

	
	public Object convert(Object source, TypeDescriptor targetType) {
		return convert(source, TypeDescriptor.forObject(source), targetType);
	}

	@Override
	public String toString() {
		return this.converters.toString();
	}

	protected Object convertNullSource(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return null;
	}

	protected GenericConverter getConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
		ConverterCacheKey key = new ConverterCacheKey(sourceType, targetType);
		GenericConverter converter = this.converterCache.get(key);
		if (converter != null) {
			return (converter != NO_MATCH ? converter : null);
		}

		converter = this.converters.find(sourceType, targetType);
		if (converter == null) {
			converter = getDefaultConverter(sourceType, targetType);
		}

		if (converter != null) {
			this.converterCache.put(key, converter);
			return converter;
		}

		this.converterCache.put(key, NO_MATCH);
		return null;
	}

	
	protected GenericConverter getDefaultConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return (sourceType.isAssignableTo(targetType) ? NO_OP_CONVERTER : null);
	}

	// internal helpers

	private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object converter, Class<?> genericIfc) {
		ResolvableType resolvableType = ResolvableType.forClass(converter.getClass()).as(genericIfc);
		if(resolvableType.hasUnresolvableGenerics()) {
			return null;
		}
		return new GenericConverter.ConvertiblePair(resolvableType.resolveGeneric(0), resolvableType.resolveGeneric(1));
	}

	private void invalidateCache() {
		this.converterCache.clear();
	}

	private Object handleConverterNotFound(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (source == null) {
			assertNotPrimitiveTargetType(sourceType, targetType);
			return source;
		}
		if (sourceType.isAssignableTo(targetType) && targetType.getObjectType().isInstance(source)) {
			return source;
		}
		throw new ConverterNotFoundException(sourceType, targetType);
	}

	private Object handleResult(TypeDescriptor sourceType, TypeDescriptor targetType, Object result) {
		if (result == null) {
			assertNotPrimitiveTargetType(sourceType, targetType);
		}
		return result;
	}

	private void assertNotPrimitiveTargetType(TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (targetType.isPrimitive()) {
			throw new ConversionFailedException(sourceType, targetType, null,
					new IllegalArgumentException("A null value cannot be assigned to a primitive type"));
		}
	}


	
	@SuppressWarnings("unchecked")
	private final class ConverterAdapter implements ConditionalGenericConverter {

		private final ConvertiblePair typeInfo;

		private final Converter<Object, Object> converter;


		public ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
			this.converter = (Converter<Object, Object>) converter;
			this.typeInfo = typeInfo;
		}


		@Override
		public Set<ConvertiblePair> getConvertibleTypes() {
			return Collections.singleton(this.typeInfo);
		}

		@Override
		public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
			if(!this.typeInfo.getTargetType().equals(targetType.getObjectType())) {
				return false;
			}
			if (this.converter instanceof ConditionalConverter) {
				return ((ConditionalConverter) this.converter).matches(sourceType, targetType);
			}
			return true;
		}

		@Override
		public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
			if (source == null) {
				return convertNullSource(sourceType, targetType);
			}
			return this.converter.convert(source);
		}

		@Override
		public String toString() {
			return this.typeInfo.getSourceType().getName() + " -> " +
					this.typeInfo.getTargetType().getName() + " : " +
					this.converter.toString();
		}
	}


	
	@SuppressWarnings("unchecked")
	private final class ConverterFactoryAdapter implements ConditionalGenericConverter {

		private final ConvertiblePair typeInfo;

		private final ConverterFactory<Object, Object> converterFactory;


		public ConverterFactoryAdapter(ConvertiblePair typeInfo, ConverterFactory<?, ?> converterFactory) {
			this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
			this.typeInfo = typeInfo;
		}


		@Override
		public Set<ConvertiblePair> getConvertibleTypes() {
			return Collections.singleton(this.typeInfo);
		}

		@Override
		public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
			boolean matches = true;
			if (this.converterFactory instanceof ConditionalConverter) {
				matches = ((ConditionalConverter) this.converterFactory).matches(sourceType, targetType);
			}
			if(matches) {
				Converter<?, ?> converter = this.converterFactory.getConverter(targetType.getType());
				if(converter instanceof ConditionalConverter) {
					matches = ((ConditionalConverter) converter).matches(sourceType, targetType);
				}
			}
			return matches;
		}

		@Override
		public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
			if (source == null) {
				return convertNullSource(sourceType, targetType);
			}
			return this.converterFactory.getConverter(targetType.getObjectType()).convert(source);
		}

		@Override
		public String toString() {
			return this.typeInfo.getSourceType().getName() + " -> " +
					this.typeInfo.getTargetType().getName() + " : " +
					this.converterFactory.toString();
		}
	}

	private static final class ConverterCacheKey {

		private final TypeDescriptor sourceType;

		private final TypeDescriptor targetType;


		public ConverterCacheKey(TypeDescriptor sourceType, TypeDescriptor targetType) {
			this.sourceType = sourceType;
			this.targetType = targetType;
		}


		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof ConverterCacheKey)) {
				return false;
			}
			ConverterCacheKey otherKey = (ConverterCacheKey) other;
			return ObjectUtils.nullSafeEquals(this.sourceType, otherKey.sourceType)
				&& ObjectUtils.nullSafeEquals(this.targetType, otherKey.targetType);
		}

		@Override
		public int hashCode() {
			return ObjectUtils.nullSafeHashCode(this.sourceType) * 29
				+ ObjectUtils.nullSafeHashCode(this.targetType);
		}

		@Override
		public String toString() {
			return "ConverterCacheKey [sourceType = " + this.sourceType
				+ ", targetType = " + this.targetType + "]";
		}
	}

	
	private static class Converters {

		private final Set<GenericConverter> globalConverters =
				new LinkedHashSet<GenericConverter>();

		private final Map<ConvertiblePair, ConvertersForPair> converters =
			new LinkedHashMap<ConvertiblePair, ConvertersForPair>(36);

		public void add(GenericConverter converter) {
			Set<ConvertiblePair> convertibleTypes = converter.getConvertibleTypes();
			if (convertibleTypes == null) {
				Assert.state(converter instanceof ConditionalConverter,
						"Only conditional converters may return null convertible types");
				globalConverters.add(converter);
			}
			else {
				for (ConvertiblePair convertiblePair : convertibleTypes) {
					ConvertersForPair convertersForPair = getMatchableConverters(convertiblePair);
					convertersForPair.add(converter);
				}
			}
		}

		private ConvertersForPair getMatchableConverters(ConvertiblePair convertiblePair) {
			ConvertersForPair convertersForPair = this.converters.get(convertiblePair);
			if (convertersForPair == null) {
				convertersForPair = new ConvertersForPair();
				this.converters.put(convertiblePair, convertersForPair);
			}
			return convertersForPair;
		}

		public void remove(Class<?> sourceType, Class<?> targetType) {
			converters.remove(new ConvertiblePair(sourceType, targetType));
		}

		
		public GenericConverter find(TypeDescriptor sourceType, TypeDescriptor targetType) {
			// Search the full type hierarchy
			List<Class<?>> sourceCandidates = getClassHierarchy(sourceType.getType());
			List<Class<?>> targetCandidates = getClassHierarchy(targetType.getType());
			for (Class<?> sourceCandidate : sourceCandidates) {
				for (Class<?> targetCandidate : targetCandidates) {
					ConvertiblePair convertiblePair = new ConvertiblePair(sourceCandidate, targetCandidate);
					GenericConverter converter = getRegisteredConverter(
							sourceType, targetType, convertiblePair);
					if(converter != null) {
						return converter;
					}
				}
			}
			return null;
		}

		private GenericConverter getRegisteredConverter(TypeDescriptor sourceType,
				TypeDescriptor targetType, ConvertiblePair convertiblePair) {

			ConvertersForPair convertersForPair = converters.get(convertiblePair);
			GenericConverter converter = convertersForPair == null ? null
				: convertersForPair.getConverter(sourceType, targetType);
			if (converter != null) {
				return converter;
			}

			for (GenericConverter globalConverter : this.globalConverters) {
				if (((ConditionalConverter)globalConverter).matches(
						sourceType, targetType)) {
					return globalConverter;
				}
			}

			return null;
		}

		/**
		 * 返回类型的所有父类及接口的集合
		 * @param type 如果type是数组，则集合中的泛型也是类似于Integer[].class
		 * @return
		 *
		 * Integer[].class ,则 [Integer[].class,Number[].class, Serializable[].class,Object[].class,Object.class]
		 * Integer[].class ,则 [Integer.class,Number.class, Serializable.class,Object.class]
		 */
		private List<Class<?>> getClassHierarchy(Class<?> type) {
			List<Class<?>> hierarchy = new ArrayList<Class<?>>(20);
			Set<Class<?>> visited = new HashSet<Class<?>>(20);
			addToClassHierarchy(0, ClassUtils.resolvePrimitiveIfNecessary(type), false, hierarchy, visited);
			boolean array = type.isArray();
			int i = 0;
			while (i < hierarchy.size()) {
				Class<?> candidate = hierarchy.get(i);
				candidate = (array ? candidate.getComponentType()
						: ClassUtils.resolvePrimitiveIfNecessary(candidate));
				Class<?> superclass = candidate.getSuperclass();
				if (candidate.getSuperclass() != null && superclass != Object.class) {
					addToClassHierarchy(i + 1, candidate.getSuperclass(), array, hierarchy, visited);
				}
				for (Class<?> implementedInterface : candidate.getInterfaces()) {
					addToClassHierarchy(hierarchy.size(), implementedInterface, array, hierarchy, visited);
				}
				i++;
			}
			addToClassHierarchy(hierarchy.size(), Object.class, array, hierarchy, visited);
			addToClassHierarchy(hierarchy.size(), Object.class, false, hierarchy, visited);
			return hierarchy;
		}

		/**
		 * 如果visited中不存在，则将type 添加到 hierarchy和visited中
		 * @param index
		 * @param type
		 * @param asArray
		 * @param hierarchy
		 * @param visited
		 */
		private void addToClassHierarchy(int index, Class<?> type, boolean asArray,
				List<Class<?>> hierarchy, Set<Class<?>> visited) {
			if(asArray) {
				type = Array.newInstance(type, 0).getClass();
			}
			if(visited.add(type)) {
				hierarchy.add(index, type);
			}
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ConversionService converters = ").append("\n");
			for (String converterString : getConverterStrings()) {
				builder.append("\t");
				builder.append(converterString);
				builder.append("\n");
			}
			return builder.toString();
		}

		private List<String> getConverterStrings() {
			List<String> converterStrings = new ArrayList<String>();
			for (ConvertersForPair convertersForPair : converters.values()) {
				converterStrings.add(convertersForPair.toString());
			}
			Collections.sort(converterStrings);
			return converterStrings;
		}
	}

	private static class ConvertersForPair {

		private final LinkedList<GenericConverter> converters = new LinkedList<GenericConverter>();

		public void add(GenericConverter converter) {
			this.converters.addFirst(converter);
		}

		public GenericConverter getConverter(TypeDescriptor sourceType,
				TypeDescriptor targetType) {
			for (GenericConverter converter : this.converters) {
				if (!(converter instanceof ConditionalGenericConverter)
						|| ((ConditionalGenericConverter) converter).matches(sourceType,
								targetType)) {
					return converter;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return StringUtils.collectionToCommaDelimitedString(this.converters);
		}
	}

	private static class NoOpConverter implements GenericConverter {

		private String name;


		public NoOpConverter(String name) {
			this.name = name;
		}


		@Override
		public Set<ConvertiblePair> getConvertibleTypes() {
			return null;
		}

		@Override
		public Object convert(Object source, TypeDescriptor sourceType,
				TypeDescriptor targetType) {
			return source;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
