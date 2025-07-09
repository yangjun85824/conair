/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
 * <p>
 * 1. This software is for development use only under a valid license
 * from BladeX.
 * <p>
 * 2. Redistribution of this software's source code to any third party
 * without a commercial license is strictly prohibited.
 * <p>
 * 3. Licensees may copyright their own code but cannot use segments
 * from this software for such purposes. Copyright of this software
 * remains with BladeX.
 * <p>
 * Using this software signifies agreement to this License, and the software
 * must not be used for illegal purposes.
 * <p>
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY. The author is
 * not liable for any claims arising from secondary or illegal development.
 * <p>
 * Author: DreamLu (596392912@qq.com)
 */

package org.springblade.core.http.cache;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

/**
 * Http Cache 服务
 *
 * @author L.cm
 */
public class HttpCacheService implements InitializingBean {
	private final BladeHttpCacheProperties properties;
	private final CacheManager cacheManager;
	private Cache cache;

	public HttpCacheService(BladeHttpCacheProperties properties, CacheManager cacheManager) {
		this.properties = properties;
		this.cacheManager = cacheManager;
	}

	public boolean get(String key) {
		Boolean result = cache.get(key, Boolean.class);
		return Boolean.TRUE.equals(result);
	}

	public void set(String key) {
		cache.put(key, Boolean.TRUE);
	}

	public void remove(String key) {
		cache.evict(key);
	}

	public void clear() {
		cache.clear();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cacheManager, "cacheManager must not be null!");
		String cacheName = properties.getCacheName();
		this.cache = cacheManager.getCache(cacheName);
		Assert.notNull(this.cache, "HttpCacheCache cacheName: " + cacheName + " is not config.");
	}
}
