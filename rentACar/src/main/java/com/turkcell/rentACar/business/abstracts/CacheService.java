package com.turkcell.rentACar.business.abstracts;

public interface CacheService {
	void evictSingleCacheValue(String cacheName, String cacheKey);
	void evictAllCacheValues(String cacheName);
	void evictAllCaches();
}
