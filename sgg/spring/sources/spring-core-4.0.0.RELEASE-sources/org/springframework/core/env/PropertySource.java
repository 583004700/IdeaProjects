
package org.springframework.core.env;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 主要用来从某个属性源中获取属性值的作用，比如source为map值，则从map中获取值
 * @param <T>
 */
public abstract class PropertySource<T> {

	protected final Log logger = LogFactory.getLog(this.getClass());

	protected final String name;

	protected final T source;

	public PropertySource(String name, T source) {
		Assert.hasText(name, "Property source name must contain at least one character");
		Assert.notNull(source, "Property source must not be null");
		this.name = name;
		this.source = source;
	}

	@SuppressWarnings("unchecked")
	public PropertySource(String name) {
		this(name, (T) new Object());
	}

	public String getName() {
		return this.name;
	}

	public T getSource() {
		return source;
	}

	public boolean containsProperty(String name) {
		return this.getProperty(name) != null;
	}

	public abstract Object getProperty(String name);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/**
	 * 重写比较规则，name.equals为真时，则为真
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PropertySource))
			return false;
		PropertySource<?> other = (PropertySource<?>) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (logger.isDebugEnabled()) {
			return String.format("%s@%s [name='%s', properties=%s]",
					this.getClass().getSimpleName(), System.identityHashCode(this), this.name, this.source);
		}

		return String.format("%s [name='%s']",
				this.getClass().getSimpleName(), this.name);
	}

	/**
	 * 返回一个新的子类对象
	 * @param name
	 * @return
	 */
	public static PropertySource<?> named(String name) {
		return new ComparisonPropertySource(name);
	}

	public static class StubPropertySource extends PropertySource<Object> {

		public StubPropertySource(String name) {
			super(name, new Object());
		}

		@Override
		public String getProperty(String name) {
			return null;
		}
	}

	static class ComparisonPropertySource extends StubPropertySource {

		private static final String USAGE_ERROR =
			"ComparisonPropertySource instances are for collection comparison " +
			"use only";

		public ComparisonPropertySource(String name) {
			super(name);
		}

		@Override
		public Object getSource() {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}

		@Override
		public boolean containsProperty(String name) {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}

		@Override
		public String getProperty(String name) {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}

		@Override
		public String toString() {
			return String.format("%s [name='%s']", getClass().getSimpleName(), this.name);
		}
	}

}
