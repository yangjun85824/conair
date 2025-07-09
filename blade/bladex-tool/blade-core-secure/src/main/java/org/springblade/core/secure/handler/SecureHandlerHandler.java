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
package org.springblade.core.secure.handler;

import org.springblade.core.secure.interceptor.*;
import org.springblade.core.secure.props.AuthSecure;
import org.springblade.core.secure.props.BasicSecure;
import org.springblade.core.secure.props.BladeSecureProperties;
import org.springblade.core.secure.props.SignSecure;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * Secure处理器
 *
 * @author Chill
 */
public class SecureHandlerHandler implements ISecureHandler {

	@Override
	public HandlerInterceptor tokenInterceptor(BladeSecureProperties secureProperties) {
		return new TokenInterceptor(secureProperties);
	}

	@Override
	public HandlerInterceptor authInterceptor(BladeSecureProperties secureProperties, List<AuthSecure> authSecures) {
		return new AuthInterceptor(authSecures);
	}

	@Override
	public HandlerInterceptor basicInterceptor(List<BasicSecure> basicSecures) {
		return new BasicInterceptor(basicSecures);
	}

	@Override
	public HandlerInterceptor signInterceptor(List<SignSecure> signSecures) {
		return new SignInterceptor(signSecures);
	}

	@Override
	public HandlerInterceptor clientInterceptor(String clientId) {
		return new ClientInterceptor(clientId);
	}

}
