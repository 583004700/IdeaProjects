package org.springframework.core;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;
import org.springframework.core.SerializableTypeWrapper.FieldTypeProvider;
import org.springframework.core.SerializableTypeWrapper.MethodParameterTypeProvider;
import org.springframework.core.SerializableTypeWrapper.TypeProvider;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public final class ResolvableType implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ConcurrentReferenceHashMap<ResolvableType, ResolvableType> cache =
			new ConcurrentReferenceHashMap<ResolvableType, ResolvableType>();

	public static final ResolvableType NONE = new ResolvableType(null, null, null, null);

	private static final ResolvableType[] EMPTY_TYPES_ARRAY = new ResolvableType[0];

	private final Type type;

	private TypeProvider typeProvider;

	private final VariableResolver variableResolver;

	private boolean isResolved = false;
	
	private Class<?> resolved;

	private final ResolvableType componentType;

	private ResolvableType(Type type, TypeProvider typeProvider,
			VariableResolver variableResolver, ResolvableType componentType) {
		this.type = type;
		this.typeProvider = typeProvider;
		this.variableResolver = variableResolver;
		this.componentType = componentType;
	}

	/**
	 * 获取自身类型
	 * @return
	 */
	public Type getType() {
		return this.type;
	}

	public Class<?> getRawClass() {
		Type rawType = this.type;
		if (rawType instanceof ParameterizedType) {
			rawType = ((ParameterizedType) rawType).getRawType();
		}
		return (rawType instanceof Class ? (Class<?>) rawType : null);
	}

	public Object getSource() {
		Object source = (this.typeProvider == null ? null : this.typeProvider.getSource());
		return (source == null ? this.type : source);
	}

	public boolean isAssignableFrom(ResolvableType type) {
		return isAssignableFrom(type, false);
	}

	private boolean isAssignableFrom(ResolvableType type, boolean checkingGeneric) {
		Assert.notNull(type, "Type must not be null");

		if (this == NONE || type == NONE) {
			return false;
		}

		if (isArray()) {
			return (type.isArray() && getComponentType().isAssignableFrom(type.getComponentType()));
		}

		WildcardBounds ourBounds = WildcardBounds.get(this);
		WildcardBounds typeBounds = WildcardBounds.get(type);

		if (typeBounds != null) {
			return (ourBounds != null && ourBounds.isSameKind(typeBounds) &&
					ourBounds.isAssignableFrom(typeBounds.getBounds()));
		}

		if (ourBounds != null) {
			return ourBounds.isAssignableFrom(type);
		}

		boolean rtn = resolve(Object.class).isAssignableFrom(type.resolve(Object.class));

		rtn &= (!checkingGeneric || resolve(Object.class).equals(type.resolve(Object.class)));

		for (int i = 0; i < getGenerics().length; i++) {
			rtn &= getGeneric(i).isAssignableFrom(type.as(resolve(Object.class)).getGeneric(i), true);
		}
		return rtn;
	}

	public boolean isArray() {
		if (this == NONE) {
			return false;
		}
		return (((this.type instanceof Class) &&
				((Class<?>) this.type).isArray()) ||
				this.type instanceof GenericArrayType ||
				this.resolveType().isArray());
	}

	/**
	 * 得到数组类型的 类类型
	 * @return
	 */
	public ResolvableType getComponentType() {
		if (this == NONE) {
			return NONE;
		}
		if (this.componentType != null) {
			return this.componentType;
		}
		if (this.type instanceof Class) {
			Class<?> componentType = ((Class<?>) this.type).getComponentType();
			return forType(componentType, this.variableResolver);
		}
		if (this.type instanceof GenericArrayType) {
			return forType(((GenericArrayType) this.type).getGenericComponentType(), this.variableResolver);
		}
		return resolveType().getComponentType();
	}

	public ResolvableType asCollection() {
		return as(Collection.class);
	}

	public ResolvableType asMap() {
		return as(Map.class);
	}

	/**
	 * 作为超类返回
	 * @param type
	 * @return
	 */
	public ResolvableType as(Class<?> type) {
		if (this == NONE) {
			return NONE;
		}
		if (ObjectUtils.nullSafeEquals(resolve(), type)) {
			return this;
		}
		for (ResolvableType interfaceType : getInterfaces()) {
			ResolvableType interfaceAsType = interfaceType.as(type);
			if (interfaceAsType != NONE) {
				return interfaceAsType;
			}
		}
		return getSuperType().as(type);
	}

	/**
	 * 获取父类
	 * @return
	 */
	public ResolvableType getSuperType() {
		Class<?> resolved = resolve();
		if (resolved == null || resolved.getGenericSuperclass() == null) {
			return NONE;
		}
		return forType(SerializableTypeWrapper.forGenericSuperclass(resolved), asVariableResolver());
	}

	public ResolvableType[] getInterfaces() {
		Class<?> resolved = resolve();
		if (resolved == null || ObjectUtils.isEmpty(resolved.getGenericInterfaces())) {
			return EMPTY_TYPES_ARRAY;
		}
		return forTypes(SerializableTypeWrapper.forGenericInterfaces(resolved), asVariableResolver());
	}

	public boolean hasGenerics() {
		return (getGenerics().length > 0);
	}

	public boolean hasUnresolvableGenerics() {
		for (Class<?> generic : resolveGenerics()) {
			if (generic == null) {
				return true;
			}
		}
		Class<?> resolved = resolve();
		if (resolved != null) {
			for (Type genericInterface : resolved.getGenericInterfaces()) {
				if (genericInterface instanceof Class) {
					if (forClass((Class<?>) genericInterface).hasGenerics()) {
						return true;
					}
				}
			}
			if (resolved.getGenericSuperclass() != null) {
				return getSuperType().hasUnresolvableGenerics();
			}
		}
		return false;
	}

	public ResolvableType getNested(int nestingLevel) {
		return getNested(nestingLevel, null);
	}

	public ResolvableType getNested(int nestingLevel, Map<Integer, Integer> typeIndexesPerLevel) {
		ResolvableType result = this;
		for (int i = 2; i <= nestingLevel; i++) {
			if (result.isArray()) {
				result = result.getComponentType();
			}
			else {
				// Handle derived types
				while (result != ResolvableType.NONE && !result.hasGenerics()) {
					result = result.getSuperType();
				}
				Integer index = (typeIndexesPerLevel != null ? typeIndexesPerLevel.get(i) : null);
				index = (index == null ? result.getGenerics().length - 1 : index);
				result = result.getGeneric(index);
			}
		}
		return result;
	}

	public ResolvableType getGeneric(int... indexes) {
		try {
			if (indexes == null || indexes.length == 0) {
				return getGenerics()[0];
			}
			ResolvableType rtn = this;
			for (int index : indexes) {
				rtn = rtn.getGenerics()[index];
			}
			return rtn;
		}
		catch (IndexOutOfBoundsException ex) {
			return NONE;
		}
	}

	public ResolvableType[] getGenerics() {
		if (this == NONE) {
			return EMPTY_TYPES_ARRAY;
		}
		if (this.type instanceof Class<?>) {
			Class<?> typeClass = (Class<?>) this.type;
			return forTypes(SerializableTypeWrapper.forTypeParameters(typeClass), this.variableResolver);
		}
		if (this.type instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) this.type).getActualTypeArguments();
			ResolvableType[] generics = new ResolvableType[actualTypeArguments.length];
			for (int i = 0; i < actualTypeArguments.length; i++) {
				generics[i] = forType(actualTypeArguments[i], this.variableResolver);
			}
			return generics;
		}
		return resolveType().getGenerics();
	}

	public Class<?>[] resolveGenerics() {
		return resolveGenerics(null);
	}

	public Class<?>[] resolveGenerics(Class<?> fallback) {
		ResolvableType[] generics = getGenerics();
		Class<?>[] resolvedGenerics = new Class<?>[generics.length];
		for (int i = 0; i < generics.length; i++) {
			resolvedGenerics[i] = generics[i].resolve(fallback);
		}
		return resolvedGenerics;
	}

	public Class<?> resolveGeneric(int... indexes) {
		return getGeneric(indexes).resolve();
	}

	public Class<?> resolve() {
		return resolve(null);
	}

	public Class<?> resolve(Class<?> fallback) {
		if (!this.isResolved) {
			this.resolved = resolveClass();
			this.isResolved = true;
		}
		return (this.resolved != null ? this.resolved : fallback);
	}

	private Class<?> resolveClass() {
		if (this.type instanceof Class<?> || this.type == null) {
			return (Class<?>) this.type;
		}
		if (this.type instanceof GenericArrayType) {
			Class<?> resolvedComponent = getComponentType().resolve();
			return (resolvedComponent != null ? Array.newInstance(resolvedComponent, 0).getClass() : null);
		}
		return resolveType().resolve();
	}

	ResolvableType resolveType() {
		if (this.type instanceof ParameterizedType) {
			return forType(((ParameterizedType) this.type).getRawType(), this.variableResolver);
		}

		if (this.type instanceof WildcardType) {
			Type resolved = resolveBounds(((WildcardType) this.type).getUpperBounds());
			if (resolved == null) {
				resolved = resolveBounds(((WildcardType) this.type).getLowerBounds());
			}
			return forType(resolved, this.variableResolver);
		}

		if (this.type instanceof TypeVariable) {
			TypeVariable<?> variable = (TypeVariable<?>) this.type;
			if (this.variableResolver != null) {
				ResolvableType resolved = this.variableResolver.resolveVariable(variable);
				if (resolved != null) {
					return resolved;
				}
			}
			return forType(resolveBounds(variable.getBounds()), this.variableResolver);
		}
		return NONE;
	}

	private Type resolveBounds(Type[] bounds) {
		if (ObjectUtils.isEmpty(bounds) || Object.class.equals(bounds[0])) {
			return null;
		}
		return bounds[0];
	}

	private ResolvableType resolveVariable(TypeVariable<?> variable) {
		if (this.type instanceof TypeVariable) {
			return resolveType().resolveVariable(variable);
		}

		if (this.type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) this.type;
			TypeVariable<?>[] variables = resolve().getTypeParameters();
			for (int i = 0; i < variables.length; i++) {
				if (ObjectUtils.nullSafeEquals(variables[i].getName(), variable.getName())) {
					Type actualType = parameterizedType.getActualTypeArguments()[i];
					return forType(actualType, this.variableResolver);
				}
			}

			if (parameterizedType.getOwnerType() != null) {
				return forType(parameterizedType.getOwnerType(), this.variableResolver).resolveVariable(variable);
			}
		}

		if (this.variableResolver != null) {
			return this.variableResolver.resolveVariable(variable);
		}

		return null;
	}

	@Override
	public String toString() {
		if (isArray()) {
			return getComponentType() + "[]";
		}
		StringBuilder result = new StringBuilder();
		result.append(resolve() == null ? "?" : resolve().getName());
		if (hasGenerics()) {
			result.append('<');
			result.append(StringUtils.arrayToDelimitedString(getGenerics(), ", "));
			result.append('>');
		}
		return result.toString();
	}

	/**
	 * 重写equals
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof ResolvableType) {
			ResolvableType other = (ResolvableType) obj;
			boolean equals = ObjectUtils.nullSafeEquals(this.type, other.type);
			equals &= ObjectUtils.nullSafeEquals(getSource(), other.getSource());
			equals &= variableResolverSourceEquals(this.variableResolver, other.variableResolver);
			equals &= ObjectUtils.nullSafeEquals(this.componentType, other.componentType);
			return equals;
		}
		return false;
	}

	/**
	 * 重写hashCode
	 * @return
	 */
	@Override
	public int hashCode() {
		int hashCode = ObjectUtils.nullSafeHashCode(this.type);
		hashCode = hashCode * 31 + ObjectUtils.nullSafeHashCode(
				this.variableResolver == null ? null : this.variableResolver.getSource());
		hashCode = hashCode * 31 + ObjectUtils.nullSafeHashCode(this.componentType);
		return hashCode;
	}

	private Object readResolve() throws ObjectStreamException {
		return (this.type == null ? NONE : this);
	}
	
	VariableResolver asVariableResolver() {
		if (this == NONE) {
			return null;
		}
		return new DefaultVariableResolver();
	}

	private static boolean variableResolverSourceEquals(VariableResolver o1, VariableResolver o2) {
		Object s1 = (o1 == null ? null : o1.getSource());
		Object s2 = (o2 == null ? null : o2.getSource());
		return ObjectUtils.nullSafeEquals(s1,s2);
	}

	private static ResolvableType[] forTypes(Type[] types, VariableResolver owner) {
		ResolvableType[] result = new ResolvableType[types.length];
		for (int i = 0; i < types.length; i++) {
			result[i] = forType(types[i], owner);
		}
		return result;
	}

	/**
	 * 构建ResolvableType对象
	 * @param sourceClass
	 * @return
	 */
	public static ResolvableType forClass(Class<?> sourceClass) {
		Assert.notNull(sourceClass, "Source class must not be null");
		return forType(sourceClass);
	}

	public static ResolvableType forClass(Class<?> sourceClass, Class<?> implementationClass) {
		Assert.notNull(sourceClass, "Source class must not be null");
		ResolvableType asType = forType(implementationClass).as(sourceClass);
		return (asType == NONE ? forType(sourceClass) : asType);
	}

	/**
	 * 构建ResolvableType对象
	 * @param field
	 * @return
	 */
	public static ResolvableType forField(Field field) {
		Assert.notNull(field, "Field must not be null");
		return forType(null, new FieldTypeProvider(field), null);
	}

	public static ResolvableType forField(Field field, Class<?> implementationClass) {
		Assert.notNull(field, "Field must not be null");
		ResolvableType owner = forType(implementationClass).as(field.getDeclaringClass());
		return forType(null, new FieldTypeProvider(field), owner.asVariableResolver());
	}

	public static ResolvableType forField(Field field, int nestingLevel) {
		Assert.notNull(field, "Field must not be null");
		return forType(null, new FieldTypeProvider(field), null).getNested(nestingLevel);
	}

	public static ResolvableType forField(Field field, int nestingLevel, Class<?> implementationClass) {
		Assert.notNull(field, "Field must not be null");
		ResolvableType owner = forType(implementationClass).as(field.getDeclaringClass());
		return forType(null, new FieldTypeProvider(field), owner.asVariableResolver()).getNested(nestingLevel);
	}

	public static ResolvableType forConstructorParameter(Constructor<?> constructor, int parameterIndex) {
		Assert.notNull(constructor, "Constructor must not be null");
		return forMethodParameter(new MethodParameter(constructor, parameterIndex));
	}

	public static ResolvableType forConstructorParameter(Constructor<?> constructor, int parameterIndex,
			Class<?> implementationClass) {
		Assert.notNull(constructor, "Constructor must not be null");
		MethodParameter methodParameter = new MethodParameter(constructor, parameterIndex);
		methodParameter.setContainingClass(implementationClass);
		return forMethodParameter(methodParameter);
	}

	/**
	 * 获取方法返回值类型
	 * @param method
	 * @return
	 */
	public static ResolvableType forMethodReturnType(Method method) {
		Assert.notNull(method, "Method must not be null");
		return forMethodParameter(MethodParameter.forMethodOrConstructor(method, -1));
	}

	public static ResolvableType forMethodReturnType(Method method, Class<?> implementationClass) {
		Assert.notNull(method, "Method must not be null");
		MethodParameter methodParameter = MethodParameter.forMethodOrConstructor(method, -1);
		methodParameter.setContainingClass(implementationClass);
		return forMethodParameter(methodParameter);
	}

	public static ResolvableType forMethodParameter(Method method, int parameterIndex) {
		Assert.notNull(method, "Method must not be null");
		return forMethodParameter(new MethodParameter(method, parameterIndex));
	}

	public static ResolvableType forMethodParameter(Method method, int parameterIndex, Class<?> implementationClass) {
		Assert.notNull(method, "Method must not be null");
		MethodParameter methodParameter = new MethodParameter(method, parameterIndex);
		methodParameter.setContainingClass(implementationClass);
		return forMethodParameter(methodParameter);
	}

	/**
	 * 构建ResolvableType
	 * @param methodParameter
	 * @return
	 */
	public static ResolvableType forMethodParameter(MethodParameter methodParameter) {
		Assert.notNull(methodParameter, "MethodParameter must not be null");
		ResolvableType owner = forType(methodParameter.getContainingClass()).as(methodParameter.getDeclaringClass());
		return forType(null, new MethodParameterTypeProvider(methodParameter),
				owner.asVariableResolver()).getNested(methodParameter.getNestingLevel(),
				methodParameter.typeIndexesPerLevel);
	}

	/**
	 * 构建ResolvableType
	 * @param componentType
	 * @return
	 */
	public static ResolvableType forArrayComponent(final ResolvableType componentType) {
		Assert.notNull(componentType, "ComponentType must not be null");
		Class<?> arrayClass = Array.newInstance(componentType.resolve(), 0).getClass();
		return new ResolvableType(arrayClass, null, null, componentType);
	}

	public static ResolvableType forClassWithGenerics(Class<?> sourceClass, Class<?>... generics) {
		Assert.notNull(sourceClass, "Source class must not be null");
		Assert.notNull(generics, "Generics must not be null");
		ResolvableType[] resolvableGenerics = new ResolvableType[generics.length];
		for (int i = 0; i < generics.length; i++) {
			resolvableGenerics[i] = forClass(generics[i]);
		}
		return forClassWithGenerics(sourceClass, resolvableGenerics);
	}

	public static ResolvableType forClassWithGenerics(Class<?> sourceClass, ResolvableType... generics) {
		Assert.notNull(sourceClass, "Source class must not be null");
		Assert.notNull(generics, "Generics must not be null");
		TypeVariable<?>[] typeVariables = sourceClass.getTypeParameters();
		return forType(sourceClass, new TypeVariablesVariableResolver(typeVariables, generics));
	}

	public static ResolvableType forType(Type type) {
		return forType(type, (VariableResolver) null);
	}

	public static ResolvableType forType(Type type, ResolvableType owner) {
		VariableResolver variableResolver = null;
		if (owner != null) {
			variableResolver = owner.asVariableResolver();
		}
		return forType(type, variableResolver);
	}

	static ResolvableType forType(Type type, VariableResolver variableResolver) {
		return forType(type, null, variableResolver);
	}

	static ResolvableType forType(Type type, TypeProvider typeProvider, VariableResolver variableResolver) {
		if (type == null && typeProvider != null) {
			type = SerializableTypeWrapper.forTypeProvider(typeProvider);
		}
		if (type == null) {
			return NONE;
		}

		ResolvableType key = new ResolvableType(type, typeProvider, variableResolver, null);
		ResolvableType resolvableType = cache.get(key);
		if (resolvableType == null) {
			resolvableType = key;
			cache.put(key, resolvableType);
		}
		return resolvableType;
	}

	static interface VariableResolver extends Serializable {

		
		Object getSource();

		
		ResolvableType resolveVariable(TypeVariable<?> variable);
	}

	@SuppressWarnings("serial")
	private class DefaultVariableResolver implements VariableResolver {

		@Override
		public ResolvableType resolveVariable(TypeVariable<?> variable) {
			return ResolvableType.this.resolveVariable(variable);
		}

		@Override
		public Object getSource() {
			return ResolvableType.this;
		}
	}

	@SuppressWarnings("serial")
	private static class TypeVariablesVariableResolver implements VariableResolver {

		private final TypeVariable<?>[] typeVariables;

		private final ResolvableType[] generics;

		public TypeVariablesVariableResolver(TypeVariable<?>[] typeVariables, ResolvableType[] generics) {
			Assert.isTrue(typeVariables.length == generics.length, "Mismatched number of generics specified");
			this.typeVariables = typeVariables;
			this.generics = generics;
		}

		@Override
		public ResolvableType resolveVariable(TypeVariable<?> variable) {
			for (int i = 0; i < this.typeVariables.length; i++) {
				if (this.typeVariables[i].equals(variable)) {
					return this.generics[i];
				}
			}
			return null;
		}

		@Override
		public Object getSource() {
			return this.generics;
		}
	}

	private static class WildcardBounds {

		private final Kind kind;

		private final ResolvableType[] bounds;

		
		public WildcardBounds(Kind kind, ResolvableType[] bounds) {
			this.kind = kind;
			this.bounds = bounds;
		}

		
		public boolean isSameKind(WildcardBounds bounds) {
			return this.kind == bounds.kind;
		}

		
		public boolean isAssignableFrom(ResolvableType... types) {
			for (ResolvableType bound : this.bounds) {
				for (ResolvableType type : types) {
					if (!isAssignable(bound, type)) {
						return false;
					}
				}
			}
			return true;
		}

		private boolean isAssignable(ResolvableType source, ResolvableType from) {
			return (this.kind == Kind.UPPER ? source.isAssignableFrom(from) : from.isAssignableFrom(source));
		}

		
		public ResolvableType[] getBounds() {
			return this.bounds;
		}

		
		public static WildcardBounds get(ResolvableType type) {
			ResolvableType resolveToWildcard = type;
			while (!(resolveToWildcard.getType() instanceof WildcardType)) {
				if (resolveToWildcard == NONE) {
					return null;
				}
				resolveToWildcard = resolveToWildcard.resolveType();
			}
			WildcardType wildcardType = (WildcardType) resolveToWildcard.type;
			Kind boundsType = (wildcardType.getLowerBounds().length > 0 ? Kind.LOWER : Kind.UPPER);
			Type[] bounds = boundsType == Kind.UPPER ? wildcardType.getUpperBounds() : wildcardType.getLowerBounds();
			ResolvableType[] resolvableBounds = new ResolvableType[bounds.length];
			for (int i = 0; i < bounds.length; i++) {
				resolvableBounds[i] = ResolvableType.forType(bounds[i], type.variableResolver);
			}
			return new WildcardBounds(boundsType, resolvableBounds);
		}
		static enum Kind {UPPER, LOWER}
	}
}
