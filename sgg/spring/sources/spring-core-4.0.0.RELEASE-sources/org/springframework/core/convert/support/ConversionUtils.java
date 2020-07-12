package org.springframework.core.convert.support;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

abstract class ConversionUtils {

	/**
	 * 执行	GenericConverter
	 * @param converter
	 * @param source
	 * @param sourceType
	 * @param targetType
	 * @return
	 */
	public static Object invokeConverter(GenericConverter converter, Object source, TypeDescriptor sourceType,
			TypeDescriptor targetType) {
		try {
			return converter.convert(source, sourceType, targetType);
		}
		catch (ConversionFailedException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new ConversionFailedException(sourceType, targetType, source, ex);
		}
	}

	public static boolean canConvertElements(TypeDescriptor sourceElementType, TypeDescriptor targetElementType, ConversionService conversionService) {
		if (targetElementType == null) {
			// yes
			return true;
		}
		if (sourceElementType == null) {
			// maybe
			return true;
		}
		if (conversionService.canConvert(sourceElementType, targetElementType)) {
			// yes
			return true;
		}
		else if (sourceElementType.getType().isAssignableFrom(targetElementType.getType())) {
			// maybe;
			return true;
		}
		else {
			// no;
			return false;
		}
	}

}
