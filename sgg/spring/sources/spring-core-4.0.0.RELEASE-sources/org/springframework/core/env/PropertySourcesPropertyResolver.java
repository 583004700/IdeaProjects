package org.springframework.core.env;

import org.springframework.core.convert.ConversionException;
import org.springframework.util.ClassUtils;

public class PropertySourcesPropertyResolver extends AbstractPropertyResolver {

	private final PropertySources propertySources;

	public PropertySourcesPropertyResolver(PropertySources propertySources) {
		this.propertySources = propertySources;
	}

	/**
	 * 判断是否包含某个键
	 * @param key
	 * @return
	 */
	@Override
	public boolean containsProperty(String key) {
		if (this.propertySources != null) {
			for (PropertySource<?> propertySource : this.propertySources) {
				if (propertySource.containsProperty(key)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getProperty(String key) {
		return getProperty(key, String.class, true);
	}

	@Override
	public <T> T getProperty(String key, Class<T> targetValueType) {
		return getProperty(key, targetValueType, true);
	}

	@Override
	protected String getPropertyAsRawString(String key) {
		return getProperty(key, String.class, false);
	}

	/**
	 * 获取属性值并转换为对应的类型
	 * @param key
	 * @param targetValueType
	 * @param resolveNestedPlaceholders
	 * @param <T>
	 * @return
	 */
	protected <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
		boolean debugEnabled = logger.isDebugEnabled();
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("getProperty(\"%s\", %s)", key, targetValueType.getSimpleName()));
		}
		if (this.propertySources != null) {
			for (PropertySource<?> propertySource : this.propertySources) {
				if (debugEnabled) {
					logger.debug(String.format("Searching for key '%s' in [%s]", key, propertySource.getName()));
				}
				Object value;
				if ((value = propertySource.getProperty(key)) != null) {
					Class<?> valueType = value.getClass();
					if (resolveNestedPlaceholders && value instanceof String) {
						value = resolveNestedPlaceholders((String) value);
					}
					if (debugEnabled) {
						logger.debug(String.format("Found key '%s' in [%s] with type [%s] and value '%s'",
								key, propertySource.getName(), valueType.getSimpleName(), value));
					}
					if (!this.conversionService.canConvert(valueType, targetValueType)) {
						throw new IllegalArgumentException(String.format(
								"Cannot convert value [%s] from source type [%s] to target type [%s]",
								value, valueType.getSimpleName(), targetValueType.getSimpleName()));
					}
					return this.conversionService.convert(value, targetValueType);
				}
			}
		}
		if (debugEnabled) {
			logger.debug(String.format("Could not find key '%s' in any property source. Returning [null]", key));
		}
		return null;
	}

	@Override
	public <T> Class<T> getPropertyAsClass(String key, Class<T> targetValueType) {
		boolean debugEnabled = logger.isDebugEnabled();
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("getPropertyAsClass(\"%s\", %s)", key, targetValueType.getSimpleName()));
		}
		if (this.propertySources != null) {
			for (PropertySource<?> propertySource : this.propertySources) {
				if (debugEnabled) {
					logger.debug(String.format("Searching for key '%s' in [%s]", key, propertySource.getName()));
				}
				Object value = propertySource.getProperty(key);
				if (value != null) {
					if (debugEnabled) {
						logger.debug(String.format("Found key '%s' in [%s] with value '%s'", key, propertySource.getName(), value));
					}
					Class<?> clazz;
					if (value instanceof String) {
						try {
							clazz = ClassUtils.forName((String) value, null);
						}
						catch (Exception ex) {
							throw new ClassConversionException((String) value, targetValueType, ex);
						}
					}
					else if (value instanceof Class) {
						clazz = (Class<?>)value;
					}
					else {
						clazz = value.getClass();
					}
					if (!targetValueType.isAssignableFrom(clazz)) {
						throw new ClassConversionException(clazz, targetValueType);
					}
					@SuppressWarnings("unchecked")
					Class<T> targetClass = (Class<T>) clazz;
					return targetClass;
				}
			}
		}
		if (debugEnabled) {
			logger.debug(String.format("Could not find key '%s' in any property source. Returning [null]", key));
		}
		return null;
	}


	@SuppressWarnings("serial")
	private static class ClassConversionException extends ConversionException {

		public ClassConversionException(Class<?> actual, Class<?> expected) {
			super(String.format("Actual type %s is not assignable to expected type %s", actual.getName(), expected.getName()));
		}

		public ClassConversionException(String actual, Class<?> expected, Exception ex) {
			super(String.format("Could not find/load class %s during attempt to convert to %s", actual, expected.getName()), ex);
		}
	}

}
