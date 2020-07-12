package org.springframework.core.env;

import java.util.Map;

public class MapPropertySource extends EnumerablePropertySource<Map<String, Object>> {

	public MapPropertySource(String name, Map<String, Object> source) {
		super(name, source);
	}

	/**
	 * 从source这个map中获取对应的值
	 * @param name
	 * @return
	 */
	@Override
	public Object getProperty(String name) {
		return this.source.get(name);
	}

	@Override
	public String[] getPropertyNames() {
		return this.source.keySet().toArray(EMPTY_NAMES_ARRAY);
	}

}
