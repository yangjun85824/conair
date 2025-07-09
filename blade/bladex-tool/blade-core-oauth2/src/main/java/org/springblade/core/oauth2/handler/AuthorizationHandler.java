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
package org.springblade.core.oauth2.handler;

import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Validation;
import org.springblade.core.oauth2.service.OAuth2User;

/**
 * OAuth2AuthorizationHandler
 *
 * @author BladeX
 */
public interface AuthorizationHandler {

	/**
	 * 认证校验
	 *
	 * @param user    用户信息
	 * @param request 请求信息
	 * @return boolean
	 */
	OAuth2Validation authValidation(OAuth2User user, OAuth2Request request);

	/**
	 * 认证成功回调
	 *
	 * @param user 用户信息
	 */
	void authSuccessful(OAuth2User user, OAuth2Request request);

	/**
	 * 认证失败回调
	 *
	 * @param user       用户信息
	 * @param validation 失败信息
	 */
	void authFailure(OAuth2User user, OAuth2Request request, OAuth2Validation validation);
}
