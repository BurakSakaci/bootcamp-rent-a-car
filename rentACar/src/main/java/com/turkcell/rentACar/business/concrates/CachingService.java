package com.turkcell.rentACar.business.concrates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CacheService;

@Service
public class CachingService implements CacheService{
	private CacheManager cacheManager;

	@Autowired
	public CachingService(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	
	@Override
	public void evictSingleCacheValue(String cacheName, String cacheKey) {
		cacheManager.getCache(cacheName).evict(cacheKey);
	}

	@Override
	public void evictAllCacheValues(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}

	@Override
	public void evictAllCaches() {
	    cacheManager.getCacheNames().stream()
	      .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}
	
	
}
