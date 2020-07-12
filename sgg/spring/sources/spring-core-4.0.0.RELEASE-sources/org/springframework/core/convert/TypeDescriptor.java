package org.springframework.core.convert;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

public class TypeDescriptor implements Serializable {

	private static final long serialVersionUID = 1L;

	static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];

	private static final Map<Class<?>, TypeDescriptor> commonTypesCache = new HashMap<Class<?>, TypeDescriptor>();

	private static final Class<?>[] CACHED_COMMON_TYPES = { Boolean.class, byte.class,
		Byte.class, char.class, Character.class, short.class, Short.class, int.class,
		Integer.class, long.class, Long.class, float.class, Float.class, double.class,
		Double.class, String.class };
	static {
		for (Class<?> preCachedClass : CACHED_COMMON_TYPES) {
			commonTypesCache.put(preCachedClass, valueOf(preCachedClass));
		}
	}

	private final Class<?> type;

	private final ResolvableType resolvableType;

	private final Annotation[] annotations;

	public TypeDescriptor(MethodParameter methodParameter) {
		Assert.notNull(methodParameter, "MethodParameter must not be null");
		if (methodParameter.getNestingLevel() != 1) {
			throw new IllegalArgumentException("MethodParameter argument must have its nestingLevel set to 1");
		}
		this.resolvableType = ResolvableType.forMethodParameter(methodParameter);
		this.type = this.resolvableType.resolve(Object.class);
		this.annotations = (methodParameter.getParameterIndex() == -1 ?
				nullSafeAnnotations(methodParameter.getMethodAnnotations()) :
				nullSafeAnnotations(methodParameter.getParameterAnnotations()));
	}

	public TypeDescriptor(Field field) {
		Assert.notNull(field, "Field must not be null");
		this.resolvableType = ResolvableType.forField(field);
		this.type = this.resolvableType.resolve(Object.class);
		this.annotations = nullSafeAnnotations(field.getAnnotations());
	}

	public TypeDescriptor(Property property) {
		Assert.notNull(property, "Property must not be null");
		this.resolvableType = ResolvableType.forMethodParameter(property.getMethodParameter());
		this.type = this.resolvableType.resolve(Object.class);
		this.annotations = nullSafeAnnotations(property.getAnnotations());
	}

	private TypeDescriptor(ResolvableType resolvableType, Class<?> type, Annotation[] annotations) {
		this.resolvableType = resolvableType;
		this.type = (type != null ? type : resolvableType.resolve(Object.class));
		this.annotations = nullSafeAnnotations(annotations);
	}

	private Annotation[] nullSafeAnnotations(Annotation[] annotations) {
		return annotations != null ? annotations : EMPTY_ANNOTATION_ARRAY;
	}

	public Class<?> getObjectType() {
		return ClassUtils.resolvePrimitiveIfNecessary(getType());
	}

	public Class<?> getType() {
		return this.type;
	}

	public Object getSource() {
		return (this.resolvableType == null ? null : this.resolvableType.getSource());
	}

	public TypeDescriptor narrow(Object value) {
		if (value == null) {
			return this;
		}
		ResolvableType narrowed = ResolvableType.forType(value.getClass(), this.resolvableType);
		return new TypeDescriptor(narrowed, null, this.annotations);
	}

	public TypeDescriptor upcast(Class<?> superType) {
		if (superType == null) {
			return null;
		}
		Assert.isAssignable(superType, getType());
		return new TypeDescriptor(this.resolvableType.as(superType), superType, this.annotations);
	}

	public String getName() {
		return ClassUtils.getQualifiedName(getType());
	}

	public boolean isPrimitive() {
		return getType().isPrimitive();
	}

	public Annotation[] getAnnotations() {
		return this.annotations;
	}

	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return getAnnotation(annotationType) != null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		for (Annotation annotation : getAnnotations()) {
			if (annotation.annotationType().equals(annotationType)) {
				return (T) annotation;
			}
		}
		for (Annotation metaAnn : getAnnotations()) {
			T ann = metaAnn.annotationType().getAnnotation(annotationType);
			if (ann != null) {
				return ann;
			}
		}
		return null;
	}

	public boolean isAssignableTo(TypeDescriptor typeDescriptor) {
		boolean typesAssignable = typeDescriptor.getObjectType().isAssignableFrom(getObjectType());
		if (!typesAssignable) {
			return false;
		}
		if (isArray() && typeDescriptor.isArray()) {
			return getElementTypeDescriptor().isAssignableTo(typeDescriptor.getElementTypeDescriptor());
		}
		else if (isCollection() && typeDescriptor.isCollection()) {
			return isNestedAssignable(getElementTypeDescriptor(), typeDescriptor.getElementTypeDescriptor());
		}
		else if (isMap() && typeDescriptor.isMap()) {
			return isNestedAssignable(getMapKeyTypeDescriptor(), typeDescriptor.getMapKeyTypeDescriptor()) &&
				isNestedAssignable(getMapValueTypeDescriptor(), typeDescriptor.getMapValueTypeDescriptor());
		}
		else {
			return true;
		}
	}

	private boolean isNestedAssignable(TypeDescriptor nestedTypeDescriptor, TypeDescriptor otherNestedTypeDescriptor) {
		if (nestedTypeDescriptor == null || otherNestedTypeDescriptor == null) {
			return true;
		}
		return nestedTypeDescriptor.isAssignableTo(otherNestedTypeDescriptor);
	}

	public boolean isCollection() {
		return Collection.class.isAssignableFrom(getType());
	}

	public boolean isArray() {
		return getType().isArray();
	}

	public TypeDescriptor getElementTypeDescriptor() {
		assertCollectionOrArray();
		if (this.resolvableType.isArray()) {
			return getRelatedIfResolvable(this, this.resolvableType.getComponentType());
		}
		return getRelatedIfResolvable(this, this.resolvableType.asCollection().getGeneric());

	}

	public TypeDescriptor elementTypeDescriptor(Object element) {
		return narrow(element, getElementTypeDescriptor());
	}

	public boolean isMap() {
		return Map.class.isAssignableFrom(getType());
	}

	public TypeDescriptor getMapKeyTypeDescriptor() {
		assertMap();
		return getRelatedIfResolvable(this, this.resolvableType.asMap().getGeneric(0));
	}

	public TypeDescriptor getMapKeyTypeDescriptor(Object mapKey) {
		return narrow(mapKey, getMapKeyTypeDescriptor());
	}

	public TypeDescriptor getMapValueTypeDescriptor() {
		assertMap();
		return getRelatedIfResolvable(this, this.resolvableType.asMap().getGeneric(1));
	}

	public TypeDescriptor getMapValueTypeDescriptor(Object mapValue) {
		return narrow(mapValue, getMapValueTypeDescriptor());
	}

	@Deprecated
	public Class<?> getElementType() {
		return getType(getElementTypeDescriptor());
	}

	@Deprecated
	public Class<?> getMapKeyType() {
		return getType(getMapKeyTypeDescriptor());
	}

	@Deprecated
	public Class<?> getMapValueType() {
		return getType(getMapValueTypeDescriptor());
	}

	private Class<?> getType(TypeDescriptor typeDescriptor) {
		return (typeDescriptor == null ? null : typeDescriptor.getType());
	}

	private void assertCollectionOrArray() {
		if (!isCollection() && !isArray()) {
			throw new IllegalStateException("Not a java.util.Collection or Array");
		}
	}

	private void assertMap() {
		if (!isMap()) {
			throw new IllegalStateException("Not a java.util.Map");
		}
	}

	private TypeDescriptor narrow(Object value, TypeDescriptor typeDescriptor) {
		if (typeDescriptor != null) {
			return typeDescriptor.narrow(value);
		}
		return (value != null ? new TypeDescriptor(this.resolvableType, value.getClass(), this.annotations) : null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TypeDescriptor)) {
			return false;
		}
		TypeDescriptor other = (TypeDescriptor) obj;
		if (!ObjectUtils.nullSafeEquals(this.type, other.type)) {
			return false;
		}
		if (getAnnotations().length != other.getAnnotations().length) {
			return false;
		}
		for (Annotation ann : this.getAnnotations()) {
			if (other.getAnnotation(ann.annotationType()) == null) {
				return false;
			}
		}
		if (isCollection() || isArray()) {
			return ObjectUtils.nullSafeEquals(this.getElementTypeDescriptor(), other.getElementTypeDescriptor());
		}
		else if (isMap()) {
			return ObjectUtils.nullSafeEquals(this.getMapKeyTypeDescriptor(), other.getMapKeyTypeDescriptor()) &&
					ObjectUtils.nullSafeEquals(this.getMapValueTypeDescriptor(), other.getMapValueTypeDescriptor());
		}
		else {
			return true;
		}
	}

	@Override
	public int hashCode() {
		return getType().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Annotation ann : getAnnotations()) {
			builder.append("@").append(ann.annotationType().getName()).append(' ');
		}
		builder.append(this.resolvableType.toString());
		return builder.toString();
	}

	public static TypeDescriptor valueOf(Class<?> type) {
		Assert.notNull(type, "Type must not be null");
		TypeDescriptor desc = commonTypesCache.get(type);
		return (desc != null ? desc : new TypeDescriptor(ResolvableType.forClass(type), null, null));
	}
	
	public static TypeDescriptor collection(Class<?> collectionType, TypeDescriptor elementTypeDescriptor) {
		Assert.notNull(collectionType, "CollectionType must not be null");
		if (!Collection.class.isAssignableFrom(collectionType)) {
			throw new IllegalArgumentException("collectionType must be a java.util.Collection");
		}
		ResolvableType element = (elementTypeDescriptor == null ? null
				: elementTypeDescriptor.resolvableType);
		return new TypeDescriptor(ResolvableType.forClassWithGenerics(collectionType,
				element), null, null);
	}

	public static TypeDescriptor map(Class<?> mapType, TypeDescriptor keyTypeDescriptor, TypeDescriptor valueTypeDescriptor) {
		if (!Map.class.isAssignableFrom(mapType)) {
			throw new IllegalArgumentException("mapType must be a java.util.Map");
		}
		ResolvableType key = (keyTypeDescriptor == null ? null : keyTypeDescriptor.resolvableType);
		ResolvableType value = (valueTypeDescriptor == null ? null : valueTypeDescriptor.resolvableType);
		return new TypeDescriptor(ResolvableType.forClassWithGenerics(mapType, key, value), null, null);
	}

	public static TypeDescriptor array(TypeDescriptor elementTypeDescriptor) {
		if(elementTypeDescriptor == null) {
			return null;
		}
		return new TypeDescriptor(
				ResolvableType.forArrayComponent(elementTypeDescriptor.resolvableType),
				null, elementTypeDescriptor.getAnnotations());
	}

	public static TypeDescriptor nested(MethodParameter methodParameter, int nestingLevel) {
		if (methodParameter.getNestingLevel() != 1) {
			throw new IllegalArgumentException("methodParameter nesting level must be 1: use the nestingLevel parameter to specify the desired nestingLevel for nested type traversal");
		}
		return nested(new TypeDescriptor(methodParameter), nestingLevel);
	}

	public static TypeDescriptor nested(Field field, int nestingLevel) {
		return nested(new TypeDescriptor(field), nestingLevel);
	}

	public static TypeDescriptor nested(Property property, int nestingLevel) {
		return nested(new TypeDescriptor(property), nestingLevel);
	}

	public static TypeDescriptor forObject(Object source) {
		return (source != null ? valueOf(source.getClass()) : null);
	}

	private static TypeDescriptor nested(TypeDescriptor typeDescriptor, int nestingLevel) {
		ResolvableType nested = typeDescriptor.resolvableType;
		for (int i = 0; i < nestingLevel; i++) {
			if (Object.class.equals(nested.getType())) {
				// could be a collection type but we don't know about its element type,
				// so let's just assume there is an element type of type Object
			} else {
				nested = nested.getNested(2);
			}
		}
		Assert.state(nested != ResolvableType.NONE, "Unable to obtain nested generic from "
					+ typeDescriptor + " at level " + nestingLevel);
		return getRelatedIfResolvable(typeDescriptor, nested);
	}

	private static TypeDescriptor getRelatedIfResolvable(TypeDescriptor source, ResolvableType type) {
		if (type.resolve() == null) {
			return null;
		}
		return new TypeDescriptor(type, null, source.annotations);
	}

}
