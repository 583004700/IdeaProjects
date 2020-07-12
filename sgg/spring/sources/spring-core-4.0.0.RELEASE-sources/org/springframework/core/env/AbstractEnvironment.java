
package org.springframework.core.env;

import java.security.AccessControlException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import static java.lang.String.*;
import static org.springframework.util.StringUtils.*;

public abstract class AbstractEnvironment implements ConfigurableEnvironment {

	public static final String ACTIVE_PROFILES_PROPERTY_NAME = "spring.profiles.active";

	public static final String DEFAULT_PROFILES_PROPERTY_NAME = "spring.profiles.default";

	protected static final String RESERVED_DEFAULT_PROFILE_NAME = "default";


	protected final Log logger = LogFactory.getLog(getClass());

	private Set<String> activeProfiles = new LinkedHashSet<String>();

	private Set<String> defaultProfiles = new LinkedHashSet<String>(getReservedDefaultProfiles());

	private final MutablePropertySources propertySources = new MutablePropertySources(this.logger);

	private final ConfigurablePropertyResolver propertyResolver =
			new PropertySourcesPropertyResolver(this.propertySources);

	public AbstractEnvironment() {
		String name = getClass().getSimpleName();
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(format("Initializing new %s", name));
		}
		customizePropertySources(this.propertySources);
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(format(
					"Initialized %s with PropertySources %s", name, this.propertySources));
		}
	}

	/**
	 *	给子类重写
	 * @param propertySources
	 */
	protected void customizePropertySources(MutablePropertySources propertySources) {
	}


	protected Set<String> getReservedDefaultProfiles() {
		return Collections.singleton(RESERVED_DEFAULT_PROFILE_NAME);
	}


	@Override
	public String[] getActiveProfiles() {
		return StringUtils.toStringArray(doGetActiveProfiles());
	}

	protected Set<String> doGetActiveProfiles() {
		if (this.activeProfiles.isEmpty()) {
			String profiles = this.getProperty(ACTIVE_PROFILES_PROPERTY_NAME);
			if (StringUtils.hasText(profiles)) {
				setActiveProfiles(commaDelimitedListToStringArray(trimAllWhitespace(profiles)));
			}
		}
		return this.activeProfiles;
	}

	@Override
	public void setActiveProfiles(String... profiles) {
		Assert.notNull(profiles, "Profile array must not be null");
		this.activeProfiles.clear();
		for (String profile : profiles) {
			validateProfile(profile);
			this.activeProfiles.add(profile);
		}
	}

	@Override
	public void addActiveProfile(String profile) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(format("Activating profile '%s'", profile));
		}
		validateProfile(profile);
		doGetActiveProfiles();
		this.activeProfiles.add(profile);
	}


	@Override
	public String[] getDefaultProfiles() {
		return StringUtils.toStringArray(doGetDefaultProfiles());
	}

	protected Set<String> doGetDefaultProfiles() {
		if (this.defaultProfiles.equals(getReservedDefaultProfiles())) {
			String profiles = this.getProperty(DEFAULT_PROFILES_PROPERTY_NAME);
			if (StringUtils.hasText(profiles)) {
				setDefaultProfiles(commaDelimitedListToStringArray(trimAllWhitespace(profiles)));
			}
		}
		return this.defaultProfiles;
	}

	@Override
	public void setDefaultProfiles(String... profiles) {
		Assert.notNull(profiles, "Profile array must not be null");
		this.defaultProfiles.clear();
		for (String profile : profiles) {
			validateProfile(profile);
			this.defaultProfiles.add(profile);
		}
	}

	@Override
	public boolean acceptsProfiles(String... profiles) {
		Assert.notEmpty(profiles, "Must specify at least one profile");
		for (String profile : profiles) {
			if (profile != null && profile.length() > 0 && profile.charAt(0) == '!') {
				if (!isProfileActive(profile.substring(1))) {
					return true;
				}
			}
			else if (isProfileActive(profile)) {
				return true;
			}
		}
		return false;
	}

	protected boolean isProfileActive(String profile) {
		validateProfile(profile);
		return doGetActiveProfiles().contains(profile) ||
				(doGetActiveProfiles().isEmpty() && doGetDefaultProfiles().contains(profile));
	}

	protected void validateProfile(String profile) {
		if (!StringUtils.hasText(profile)) {
			throw new IllegalArgumentException("Invalid profile [" + profile + "]: must contain text");
		}
		if (profile.charAt(0) == '!') {
			throw new IllegalArgumentException("Invalid profile [" + profile + "]: must not begin with ! operator");
		}
	}

	@Override
	public MutablePropertySources getPropertySources() {
		return this.propertySources;
	}

	/**
	 * 获取系统环境的map
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getSystemEnvironment() {
		Map<String, ?> systemEnvironment;
		try {
			systemEnvironment = System.getenv();
		}
		catch (AccessControlException ex) {
			systemEnvironment = new ReadOnlySystemAttributesMap() {
				@Override
				protected String getSystemAttribute(String variableName) {
					try {
						return System.getenv(variableName);
					}
					catch (AccessControlException ex) {
						if (logger.isInfoEnabled()) {
							logger.info(format("Caught AccessControlException when " +
									"accessing system environment variable [%s]; its " +
									"value will be returned [null]. Reason: %s",
									variableName, ex.getMessage()));
						}
						return null;
					}
				}
			};
		}
		return (Map<String, Object>) systemEnvironment;
	}

	/**
	 * 返回系统属性的map
	 * @return
	 */
	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, Object> getSystemProperties() {
		Map systemProperties;
		try {
			systemProperties = System.getProperties();
		}
		catch (AccessControlException ex) {
			//调用这个map的get方法时，会调用getSystemAttribute方法。
			systemProperties = new ReadOnlySystemAttributesMap() {
				@Override
				protected String getSystemAttribute(String propertyName) {
					try {
						return System.getProperty(propertyName);
					}
					catch (AccessControlException ex) {
						if (logger.isInfoEnabled()) {
							logger.info(format("Caught AccessControlException when " +
									"accessing system property [%s]; its value will be " +
									"returned [null]. Reason: %s",
									propertyName, ex.getMessage()));
						}
						return null;
					}
				}
			};
		}
		return systemProperties;
	}

	@Override
	public void merge(ConfigurableEnvironment parent) {
		for (PropertySource<?> ps : parent.getPropertySources()) {
			if (!this.propertySources.contains(ps.getName())) {
				this.propertySources.addLast(ps);
			}
		}
		for (String profile : parent.getActiveProfiles()) {
			this.activeProfiles.add(profile);
		}
		if (parent.getDefaultProfiles().length > 0) {
			this.defaultProfiles.remove(RESERVED_DEFAULT_PROFILE_NAME);
			for (String profile : parent.getDefaultProfiles()) {
				this.defaultProfiles.add(profile);
			}
		}
	}

	@Override
	public boolean containsProperty(String key) {
		return this.propertyResolver.containsProperty(key);
	}

	@Override
	public String getProperty(String key) {
		return this.propertyResolver.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return this.propertyResolver.getProperty(key, defaultValue);
	}

	@Override
	public <T> T getProperty(String key, Class<T> targetType) {
		return this.propertyResolver.getProperty(key, targetType);
	}

	@Override
	public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		return this.propertyResolver.getProperty(key, targetType, defaultValue);
	}

	@Override
	public <T> Class<T> getPropertyAsClass(String key, Class<T> targetType) {
		return this.propertyResolver.getPropertyAsClass(key, targetType);
	}

	@Override
	public String getRequiredProperty(String key) throws IllegalStateException {
		return this.propertyResolver.getRequiredProperty(key);
	}

	@Override
	public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
		return this.propertyResolver.getRequiredProperty(key, targetType);
	}

	@Override
	public void setRequiredProperties(String... requiredProperties) {
		this.propertyResolver.setRequiredProperties(requiredProperties);
	}

	@Override
	public void validateRequiredProperties() throws MissingRequiredPropertiesException {
		this.propertyResolver.validateRequiredProperties();
	}

	@Override
	public String resolvePlaceholders(String text) {
		return this.propertyResolver.resolvePlaceholders(text);
	}

	@Override
	public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
		return this.propertyResolver.resolveRequiredPlaceholders(text);
	}

	@Override
	public void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders) {
		this.propertyResolver.setIgnoreUnresolvableNestedPlaceholders(ignoreUnresolvableNestedPlaceholders);
	}

	@Override
	public void setConversionService(ConfigurableConversionService conversionService) {
		this.propertyResolver.setConversionService(conversionService);
	}

	@Override
	public ConfigurableConversionService getConversionService() {
		return this.propertyResolver.getConversionService();
	}

	@Override
	public void setPlaceholderPrefix(String placeholderPrefix) {
		this.propertyResolver.setPlaceholderPrefix(placeholderPrefix);
	}

	@Override
	public void setPlaceholderSuffix(String placeholderSuffix) {
		this.propertyResolver.setPlaceholderSuffix(placeholderSuffix);
	}

	@Override
	public void setValueSeparator(String valueSeparator) {
		this.propertyResolver.setValueSeparator(valueSeparator);
	}


	@Override
	public String toString() {
		return format("%s {activeProfiles=%s, defaultProfiles=%s, propertySources=%s}",
				getClass().getSimpleName(), this.activeProfiles, this.defaultProfiles,
				this.propertySources);
	}

}
