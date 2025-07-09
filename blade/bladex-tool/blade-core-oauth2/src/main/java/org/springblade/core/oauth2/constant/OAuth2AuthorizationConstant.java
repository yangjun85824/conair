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
package org.springblade.core.oauth2.constant;

/**
 * OAuth2AuthorizationConstant
 *
 * @author BladeX
 */
public interface OAuth2AuthorizationConstant {

	/**
	 * 用户session key
	 */
	String AUTHORIZATION_SESSION_KEY = "user";

	/**
	 * 授权请求key
	 */
	String AUTHORIZATION_REQUEST_KEY = "authorizationRequest";

	/**
	 * 公钥
	 */
	String PUBLIC_KEY = "publicKey";

	/**
	 * 跳转参数
	 */
	String REDIRECT_URL = "redirect:";

	/**
	 * 授权地址
	 */
	String AUTHORIZE_URL = "/oauth/authorize";

	/**
	 * 登录地址
	 */
	String LOGIN_URL = "/oauth/login";

	/**
	 * 错误地址
	 */
	String ERROR_URL = "/oauth/error";

	/**
	 * 授权视图
	 */
	String AUTHORIZE_MODEL = "authorize";

	/**
	 * 登录视图
	 */
	String LOGIN_MODEL = "login";

	/**
	 * 错误视图
	 */
	String ERROR_MODEL = "error";
}
