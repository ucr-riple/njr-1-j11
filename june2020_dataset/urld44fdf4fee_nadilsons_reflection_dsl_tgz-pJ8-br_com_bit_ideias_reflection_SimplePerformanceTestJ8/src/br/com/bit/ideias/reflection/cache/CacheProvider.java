package br.com.bit.ideias.reflection.cache;

/**
 * @author Leonardo Campos
 * @date 25/11/2009
 * 
 *       This class works as a Cache factory to allow the use of Custom Caches
 *       or use of full fledged caches like ehcache. To use those, just create
 *       an Adapter which implements Cache interface
 */
public class CacheProvider {
	private static Cache cacheInstance;

	public static synchronized Cache getCache() {
		if (cacheInstance == null) {
			cacheInstance = createCache();
		}

		return cacheInstance;
	}

	private static Cache createCache() {
		return new LRUCache();
	}
}
