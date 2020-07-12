package org.springframework.core.convert;

public interface ConversionService {

	boolean canConvert(Class<?> sourceType, Class<?> targetType);

	boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType);

	/**
	 * 将对象转换为另一种类型
	 * @param source
	 * @param targetType
	 * @param <T>
	 * @return
	 */
	<T> T convert(Object source, Class<T> targetType);

	Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType);

}
