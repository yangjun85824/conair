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
package org.springblade.gateway.provider;

import org.springblade.core.launch.constant.TokenConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 *
 * @author Chill
 */
public class AuthProvider {

	public static final String AUTH_KEY = TokenConstant.AUTH_HEADER;
	private static final List<String> DEFAULT_SKIP_URL = new ArrayList<>();

	static {
		DEFAULT_SKIP_URL.add("/example");
		DEFAULT_SKIP_URL.add("/oauth/token/**");
		DEFAULT_SKIP_URL.add("/oauth/captcha/**");
		DEFAULT_SKIP_URL.add("/oauth/sms/**");
		DEFAULT_SKIP_URL.add("/oauth/clear-cache/**");
		DEFAULT_SKIP_URL.add("/oauth/user-info");
		DEFAULT_SKIP_URL.add("/oauth/render/**");
		DEFAULT_SKIP_URL.add("/oauth/callback/**");
		DEFAULT_SKIP_URL.add("/oauth/revoke/**");
		DEFAULT_SKIP_URL.add("/oauth/refresh/**");
		DEFAULT_SKIP_URL.add("/token/**");
		DEFAULT_SKIP_URL.add("/actuator/**");
		DEFAULT_SKIP_URL.add("/v3/api-docs/**");
		DEFAULT_SKIP_URL.add("/tenant/info");
		DEFAULT_SKIP_URL.add("/process/resource-view");
		DEFAULT_SKIP_URL.add("/process/diagram-view");
		DEFAULT_SKIP_URL.add("/manager/check-upload");
		DEFAULT_SKIP_URL.add("/assets/**");
	}

	/**
	 * 默认无需鉴权的API
	 */
	public static List<String> getDefaultSkipUrl() {
		return DEFAULT_SKIP_URL;
	}

}
