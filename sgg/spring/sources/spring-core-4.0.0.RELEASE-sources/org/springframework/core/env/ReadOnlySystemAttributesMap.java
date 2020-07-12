

package org.springframework.core.env;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;


abstract class ReadOnlySystemAttributesMap implements Map<String, String> {

	@Override
	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	
	@Override
	public String get(Object key) {
		Assert.isInstanceOf(String.class, key,
			String.format("expected key [%s] to be of type String, got %s",
					key, key.getClass().getName()));

		return this.getSystemAttribute((String) key);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	
	protected abstract String getSystemAttribute(String attributeName);

	// Unsupported

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String put(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> keySet() {
		return Collections.emptySet();
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> values() {
		return Collections.emptySet();
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		return Collections.emptySet();
	}

}
