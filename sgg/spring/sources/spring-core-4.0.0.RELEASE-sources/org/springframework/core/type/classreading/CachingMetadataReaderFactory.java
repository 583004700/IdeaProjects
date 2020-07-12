package org.springframework.core.type.classreading;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class CachingMetadataReaderFactory extends SimpleMetadataReaderFactory {

	/** Default maximum number of entries for the MetadataReader cache: 256 */
	public static final int DEFAULT_CACHE_LIMIT = 256;

	private volatile int cacheLimit = DEFAULT_CACHE_LIMIT;

	@SuppressWarnings("serial")
	private final Map<Resource, MetadataReader> metadataReaderCache =
			new LinkedHashMap<Resource, MetadataReader>(DEFAULT_CACHE_LIMIT, 0.75f, true) {
				@Override
				protected boolean removeEldestEntry(Map.Entry<Resource, MetadataReader> eldest) {
					return size() > getCacheLimit();
				}
			};

	public CachingMetadataReaderFactory() {
		super();
	}

	public CachingMetadataReaderFactory(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	public CachingMetadataReaderFactory(ClassLoader classLoader) {
		super(classLoader);
	}

	public void setCacheLimit(int cacheLimit) {
		this.cacheLimit = cacheLimit;
	}

	public int getCacheLimit() {
		return this.cacheLimit;
	}

	@Override
	public MetadataReader getMetadataReader(Resource resource) throws IOException {
		if (getCacheLimit() <= 0) {
			return super.getMetadataReader(resource);
		}
		synchronized (this.metadataReaderCache) {
			MetadataReader metadataReader = this.metadataReaderCache.get(resource);
			if (metadataReader == null) {
				metadataReader = super.getMetadataReader(resource);
				this.metadataReaderCache.put(resource, metadataReader);
			}
			return metadataReader;
		}
	}

	public void clearCache() {
		synchronized (this.metadataReaderCache) {
			this.metadataReaderCache.clear();
		}
	}

}
