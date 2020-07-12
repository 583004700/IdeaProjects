package org.springframework.core.env;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 枚举类型的属性源
 * @param <T>
 */
public abstract class EnumerablePropertySource<T> extends PropertySource<T> {

	protected static final String[] EMPTY_NAMES_ARRAY = new String[0];

	protected final Log logger = LogFactory.getLog(getClass());

	public EnumerablePropertySource(String name, T source) {
		super(name, source);
	}

	/**
	 * 从source中获取所有的属性名数组
	 * @return
	 */
	public abstract String[] getPropertyNames();

	/**
	 * 判断name是否在属性名数组中
	 * @param name
	 * @return
	 */
	@Override
	public boolean containsProperty(String name) {
		Assert.notNull(name, "property name must not be null");
		for (String candidate : this.getPropertyNames()) {
			if (candidate.equals(name)) {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("PropertySource [%s] contains '%s'", getName(), name));
				}
				return true;
			}
		}
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("PropertySource [%s] does not contain '%s'", getName(), name));
		}
		return false;
	}

}
