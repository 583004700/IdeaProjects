package org.springframework.core.convert.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

final class ObjectToObjectConverter implements ConditionalGenericConverter {

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(Object.class, Object.class));
	}

	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (sourceType.getType().equals(targetType.getType())) {
			// no conversion required
			return false;
		}
		return hasValueOfMethodOrConstructor(targetType.getType(), sourceType.getType());
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (source == null) {
			return null;
		}
		Class<?> sourceClass = sourceType.getType();
		Class<?> targetClass = targetType.getType();
		Method method = getValueOfMethodOn(targetClass, sourceClass);
		try {
			if (method != null) {
				ReflectionUtils.makeAccessible(method);
				return method.invoke(null, source);
			}
			else {
				Constructor<?> constructor = getConstructor(targetClass, sourceClass);
				if (constructor != null) {
					return constructor.newInstance(source);
				}
			}
		}
		catch (InvocationTargetException ex) {
			throw new ConversionFailedException(sourceType, targetType, source, ex.getTargetException());
		}
		catch (Throwable ex) {
			throw new ConversionFailedException(sourceType, targetType, source, ex);
		}
		throw new IllegalStateException("No static valueOf(" + sourceClass.getName() +
				") method or Constructor(" + sourceClass.getName() + ") exists on " + targetClass.getName());
	}

	/**
	 * 判断是否有 valueOf方法或者构造方法
	 * @param clazz
	 * @param sourceParameterType 方法参数
	 * @return
	 */
	static boolean hasValueOfMethodOrConstructor(Class<?> clazz, Class<?> sourceParameterType) {
		return getValueOfMethodOn(clazz, sourceParameterType) != null || getConstructor(clazz, sourceParameterType) != null;
	}

	/**
	 * 获取clazz上的 valueOf 或者 of方法
	 * @param clazz
	 * @param sourceParameterType 方法参数
	 * @return
	 */
	private static Method getValueOfMethodOn(Class<?> clazz, Class<?> sourceParameterType) {
		Method method = ClassUtils.getStaticMethod(clazz, "valueOf", sourceParameterType);
		if (method == null) {
			method = ClassUtils.getStaticMethod(clazz, "of", sourceParameterType);
		}
		return method;
	}

	/**
	 * 获取clazz的构造方法
	 * @param clazz
	 * @param sourceParameterType	方法参数
	 * @return
	 */
	private static Constructor<?> getConstructor(Class<?> clazz, Class<?> sourceParameterType) {
		return ClassUtils.getConstructorIfAvailable(clazz, sourceParameterType);
	}

}
