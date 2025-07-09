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
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.common.cache;

/**
 * 缓存名
 *
 * @author Chill
 */
public interface CacheNames {

	/**
	 * 返回拼接后的key
	 *
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String cacheKey(String cacheKey, String cacheKeyValue) {
		return cacheKey.concat(cacheKeyValue);
	}

	/**
	 * 返回租户格式的key
	 *
	 * @param tenantId      租户编号
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String tenantKey(String tenantId, String cacheKey, String cacheKeyValue) {
		return tenantId.concat(":").concat(cacheKey).concat(cacheKeyValue);
	}

	/**
	 * 验证码key
	 */
	String CAPTCHA_KEY = "blade:auth::blade:captcha:";

	/**
	 * 登录失败key
	 */
	String USER_FAIL_KEY = "blade:user::blade:fail:";

}
