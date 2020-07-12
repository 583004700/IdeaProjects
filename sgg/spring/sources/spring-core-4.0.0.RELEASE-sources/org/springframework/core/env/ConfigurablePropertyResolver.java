package org.springframework.core.env;

import org.springframework.core.convert.support.ConfigurableConversionService;


public interface ConfigurablePropertyResolver extends PropertyResolver {

	
	ConfigurableConversionService getConversionService();

	
	void setConversionService(ConfigurableConversionService conversionService);

	
	void setPlaceholderPrefix(String placeholderPrefix);

	
	void setPlaceholderSuffix(String placeholderSuffix);

	
	void setValueSeparator(String valueSeparator);

	
	void setRequiredProperties(String... requiredProperties);

	
	void validateRequiredProperties() throws MissingRequiredPropertiesException;

	
	void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders);
}
