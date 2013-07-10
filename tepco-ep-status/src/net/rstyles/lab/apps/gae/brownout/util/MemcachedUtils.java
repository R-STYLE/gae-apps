package net.rstyles.lab.apps.gae.brownout.util;

import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class MemcachedUtils {

	public static final <K, V> void put(K key, V value) {
		MemcacheServiceFactory.getMemcacheService().put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public static final <K, V> V get(K key) {
		Object value = MemcacheServiceFactory.getMemcacheService().get(key);
		if (value == null) {return null;}
		return (V) value;
	}
}
