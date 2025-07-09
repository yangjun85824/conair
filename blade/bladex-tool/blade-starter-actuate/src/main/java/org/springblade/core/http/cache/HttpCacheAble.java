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

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Http cache
 * cache-control
 * <p>
 * max-age 大于0 时 直接从游览器缓存中 提取
 * max-age 小于或等于0 时 向server 发送http 请求确认 ,该资源是否有修改
 *
 * @author L.cm
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpCacheAble {

	/**
	 * 缓存的时间,默认0,单位秒
	 *
	 * @return {long}
	 */
	@AliasFor("maxAge")
	long value();

	/**
	 * 缓存的时间,默认0,单位秒
	 *
	 * @return {long}
	 */
	@AliasFor("value")
	long maxAge() default 0;

}
