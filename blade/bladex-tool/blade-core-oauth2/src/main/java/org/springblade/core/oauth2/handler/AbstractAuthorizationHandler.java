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

import org.springblade.core.oauth2.exception.ExceptionCode;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Validation;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.tool.utils.Func;

/**
 * AbstractAuthorizationHandler
 *
 * @author BladeX
 */
public abstract class AbstractAuthorizationHandler implements AuthorizationHandler {

	/**
	 * 认证校验
	 *
	 * @param user    用户信息
	 * @param request 请求信息
	 * @return boolean
	 */
	@Override
	public OAuth2Validation authValidation(OAuth2User user, OAuth2Request request) {
		if (request.isClientCredentials() || request.isImplicit() || request.isSocial()) {
			return new OAuth2Validation();
		}
		if (Func.hasEmpty(user, user.getUserId())) {
			return buildValidationFailure(ExceptionCode.USER_NOT_FOUND);
		}
		if (Func.isEmpty(user.getAuthorities())) {
			return buildValidationFailure(ExceptionCode.UNAUTHORIZED_USER);
		}
		return new OAuth2Validation();
	}

	/**
	 * 认证成功回调
	 *
	 * @param user 用户信息
	 */
	@Override
	public abstract void authSuccessful(OAuth2User user, OAuth2Request request);

	/**
	 * 认证失败回调
	 *
	 * @param user       用户信息
	 * @param validation 失败信息
	 */
	@Override
	public abstract void authFailure(OAuth2User user, OAuth2Request request, OAuth2Validation validation);

	/**
	 * 构建认证失败返回
	 *
	 * @param errorCode 错误码
	 * @return 认证结果
	 */
	public OAuth2Validation buildValidationFailure(ExceptionCode errorCode) {
		return new OAuth2Validation().setSuccess(false)
			.setCode(errorCode.getCode())
			.setMessage(errorCode.getMessage());
	}

}
