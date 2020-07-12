package org.springframework.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.Assert;

public class MethodParameter {

	private final Method method;

	private final Constructor<?> constructor;

	private final int parameterIndex;

	private Class<?> containingClass;

	private Class<?> parameterType;

	private Type genericParameterType;

	private Annotation[] parameterAnnotations;

	private ParameterNameDiscoverer parameterNameDiscoverer;

	private String parameterName;

	private int nestingLevel = 1;

	Map<Integer, Integer> typeIndexesPerLevel;

	private int hash = 0;

	public MethodParameter(Method method, int parameterIndex) {
		this(method, parameterIndex, 1);
	}

	public MethodParameter(Method method, int parameterIndex, int nestingLevel) {
		Assert.notNull(method, "Method must not be null");
		this.method = method;
		this.parameterIndex = parameterIndex;
		this.nestingLevel = nestingLevel;
		this.constructor = null;
	}

	public MethodParameter(Constructor<?> constructor, int parameterIndex) {
		this(constructor, parameterIndex, 1);
	}

	public MethodParameter(Constructor<?> constructor, int parameterIndex, int nestingLevel) {
		Assert.notNull(constructor, "Constructor must not be null");
		this.constructor = constructor;
		this.parameterIndex = parameterIndex;
		this.nestingLevel = nestingLevel;
		this.method = null;
	}

	public MethodParameter(MethodParameter original) {
		Assert.notNull(original, "Original must not be null");
		this.method = original.method;
		this.constructor = original.constructor;
		this.parameterIndex = original.parameterIndex;
		this.containingClass = original.containingClass;
		this.parameterType = original.parameterType;
		this.genericParameterType = original.genericParameterType;
		this.parameterAnnotations = original.parameterAnnotations;
		this.parameterNameDiscoverer = original.parameterNameDiscoverer;
		this.parameterName = original.parameterName;
		this.nestingLevel = original.nestingLevel;
		this.typeIndexesPerLevel = original.typeIndexesPerLevel;
		this.hash = original.hash;
	}

	public Method getMethod() {
		return this.method;
	}

	public Constructor<?> getConstructor() {
		return this.constructor;
	}

	public Member getMember() {
		if (this.method != null) {
			return this.method;
		}
		else {
			return this.constructor;
		}
	}

	public AnnotatedElement getAnnotatedElement() {
		if (this.method != null) {
			return this.method;
		}
		else {
			return this.constructor;
		}
	}

	public Class<?> getDeclaringClass() {
		return getMember().getDeclaringClass();
	}

	public int getParameterIndex() {
		return this.parameterIndex;
	}

	void setContainingClass(Class<?> containingClass) {
		this.containingClass = containingClass;
	}

	public Class<?> getContainingClass() {
		return (this.containingClass != null ? this.containingClass : getDeclaringClass());
	}

	void setParameterType(Class<?> parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * 返回下标对应的参数类型  如果下标为负数，则返回方法的返回值，如果方法为null，则返回null
	 * @return
	 */
	public Class<?> getParameterType() {
		if (this.parameterType == null) {
			if (this.parameterIndex < 0) {
				this.parameterType = (this.method != null ? this.method.getReturnType() : null);
			}
			else {
				this.parameterType = (this.method != null ?
					this.method.getParameterTypes()[this.parameterIndex] :
					this.constructor.getParameterTypes()[this.parameterIndex]);
			}
		}
		return this.parameterType;
	}

	/**
	 * 返回下标对应的带泛型参数类型  如果下标为负数，则返回带泛型方法的返回值，如果方法为null，则返回null
	 * @return
	 */
	public Type getGenericParameterType() {
		if (this.genericParameterType == null) {
			if (this.parameterIndex < 0) {
				this.genericParameterType = (this.method != null ? this.method.getGenericReturnType() : null);
			}
			else {
				this.genericParameterType = (this.method != null ?
					this.method.getGenericParameterTypes()[this.parameterIndex] :
					this.constructor.getGenericParameterTypes()[this.parameterIndex]);
			}
		}
		return this.genericParameterType;
	}

	/**
	 * 获取当前参数内的指定的泛型的class，如Map<Integer,String>,
	 * 从typeIndexesPerLevel取出键nestingLevel对应的index，然后取出对应index的泛型参数
	 * @return
	 */
	public Class<?> getNestedParameterType() {
		if (this.nestingLevel > 1) {
			Type type = getGenericParameterType();
			//判断是否是参数化类型，如Map<Integer,String>
			if (type instanceof ParameterizedType) {
				Integer index = getTypeIndexForCurrentLevel();
				//获取如Map<Integer,String> 中的Integer,String  Type数组
				Type[] args = ((ParameterizedType) type).getActualTypeArguments();
				Type arg = args[index != null ? index : args.length - 1];
				if (arg instanceof Class) {
					return (Class<?>) arg;
				}
				else if (arg instanceof ParameterizedType) {
					arg = ((ParameterizedType) arg).getRawType();
					if (arg instanceof Class) {
						return (Class<?>) arg;
					}
				}
			}
			return Object.class;
		}
		else {
			return getParameterType();
		}
	}

	/**
	 * 返回方法的所有注解
	 * @return
	 */
	public Annotation[] getMethodAnnotations() {
		return getAnnotatedElement().getAnnotations();
	}

	/**
	 * 返回方法特定类型的注解
	 * @param annotationType
	 * @param <T>
	 * @return
	 */
	public <T extends Annotation> T getMethodAnnotation(Class<T> annotationType) {
		return getAnnotatedElement().getAnnotation(annotationType);
	}

	/**
	 * 获取方法 对应下标参数的注解数组
	 * @return
	 */
	public Annotation[] getParameterAnnotations() {
		if (this.parameterAnnotations == null) {
			Annotation[][] annotationArray = (this.method != null ?
					this.method.getParameterAnnotations() : this.constructor.getParameterAnnotations());
			if (this.parameterIndex >= 0 && this.parameterIndex < annotationArray.length) {
				this.parameterAnnotations = annotationArray[this.parameterIndex];
			}
			else {
				this.parameterAnnotations = new Annotation[0];
			}
		}
		return this.parameterAnnotations;
	}

	/**
	 * 	获取方法 对应下标参数的某种类型的注解
	 * @param annotationType
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getParameterAnnotation(Class<T> annotationType) {
		Annotation[] anns = getParameterAnnotations();
		for (Annotation ann : anns) {
			if (annotationType.isInstance(ann)) {
				return (T) ann;
			}
		}
		return null;
	}

	/**
	 * 判断方法对应下标参数是否有注解
	 * @return
	 */
	public boolean hasParameterAnnotations() {
		return (getParameterAnnotations().length != 0);
	}

	/**
	 * 判断方法对应下标参数是否有某种类型的注解
	 * @param annotationType
	 * @param <T>
	 * @return
	 */
	public <T extends Annotation> boolean hasParameterAnnotation(Class<T> annotationType) {
		return (getParameterAnnotation(annotationType) != null);
	}

	public void initParameterNameDiscovery(ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}

	/**
	 * 获取方法对应下标参数名称
	 * @return
	 */
	public String getParameterName() {
		if (this.parameterNameDiscoverer != null) {
			String[] parameterNames = (this.method != null ?
					this.parameterNameDiscoverer.getParameterNames(this.method) :
					this.parameterNameDiscoverer.getParameterNames(this.constructor));
			if (parameterNames != null) {
				this.parameterName = parameterNames[this.parameterIndex];
			}
			this.parameterNameDiscoverer = null;
		}
		return this.parameterName;
	}

	public void increaseNestingLevel() {
		this.nestingLevel++;
	}

	public void decreaseNestingLevel() {
		getTypeIndexesPerLevel().remove(this.nestingLevel);
		this.nestingLevel--;
	}

	public int getNestingLevel() {
		return this.nestingLevel;
	}

	public void setTypeIndexForCurrentLevel(int typeIndex) {
		getTypeIndexesPerLevel().put(this.nestingLevel, typeIndex);
	}

	/**
	 * 根据nestingLevel 取值
	 * @return
	 */
	public Integer getTypeIndexForCurrentLevel() {
		return getTypeIndexForLevel(this.nestingLevel);
	}

	public Integer getTypeIndexForLevel(int nestingLevel) {
		return getTypeIndexesPerLevel().get(nestingLevel);
	}

	/**
	 * 获取typeIndexesPerLevel 的map，如果不存在，则创建并赋值
	 * @return
	 */
	private Map<Integer, Integer> getTypeIndexesPerLevel() {
		if (this.typeIndexesPerLevel == null) {
			this.typeIndexesPerLevel = new HashMap<Integer, Integer>(4);
		}
		return this.typeIndexesPerLevel;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && obj instanceof MethodParameter) {
			MethodParameter other = (MethodParameter) obj;

			if (this.parameterIndex != other.parameterIndex) {
				return false;
			}
			else if (this.getMember().equals(other.getMember())) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = this.hash;
		if (result == 0) {
			result = getMember().hashCode();
			result = 31 * result + this.parameterIndex;
			this.hash = result;
		}
		return result;
	}

	/**
	 * 构造MethodParameter对象
	 * @param methodOrConstructor
	 * @param parameterIndex
	 * @return
	 */
	public static MethodParameter forMethodOrConstructor(Object methodOrConstructor, int parameterIndex) {
		if (methodOrConstructor instanceof Method) {
			return new MethodParameter((Method) methodOrConstructor, parameterIndex);
		}
		else if (methodOrConstructor instanceof Constructor) {
			return new MethodParameter((Constructor<?>) methodOrConstructor, parameterIndex);
		}
		else {
			throw new IllegalArgumentException(
					"Given object [" + methodOrConstructor + "] is neither a Method nor a Constructor");
		}
	}

}
