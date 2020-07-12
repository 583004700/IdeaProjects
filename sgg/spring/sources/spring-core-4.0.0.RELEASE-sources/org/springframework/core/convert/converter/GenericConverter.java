package org.springframework.core.convert.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.Assert;
import java.util.Set;

public interface GenericConverter {

	Set<ConvertiblePair> getConvertibleTypes();

	Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType);

	public static final class ConvertiblePair {

		private final Class<?> sourceType;
		private final Class<?> targetType;

		public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
			Assert.notNull(sourceType, "Source type must not be null");
			Assert.notNull(targetType, "Target type must not be null");
			this.sourceType = sourceType;
			this.targetType = targetType;
		}

		public Class<?> getSourceType() {
			return this.sourceType;
		}

		public Class<?> getTargetType() {
			return this.targetType;
		}

		/**
		 * 重写equals方法
		 * @param obj
		 * @return
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || obj.getClass() != ConvertiblePair.class) {
				return false;
			}
			ConvertiblePair other = (ConvertiblePair) obj;
			return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);

		}

		@Override
		public int hashCode() {
			return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
		}
	}

}
