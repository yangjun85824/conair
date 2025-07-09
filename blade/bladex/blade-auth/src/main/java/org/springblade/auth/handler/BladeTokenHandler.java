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
package org.springblade.auth.handler;

import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.oauth2.handler.OAuth2TokenHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Token;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.tool.support.Kv;

/**
 * BladeTokenHandler
 *
 * @author BladeX
 */
public class BladeTokenHandler extends OAuth2TokenHandler {

	public BladeTokenHandler(JwtProperties properties) {
		super(properties);
	}

	@Override
	public OAuth2Token enhance(OAuth2User user, OAuth2Token token, OAuth2Request request) {
		// 父类令牌状态配置
		OAuth2Token enhanceToken = super.enhance(user, token, request);

		// 令牌统一处理，增加或删减字段
		Kv args = enhanceToken.getArgs();
		args.set(TokenConstant.USER_NAME, user.getAccount());

		// 返回令牌
		return enhanceToken;
	}
}
