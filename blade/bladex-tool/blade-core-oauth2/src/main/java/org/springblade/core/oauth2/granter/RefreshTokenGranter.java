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
package org.springblade.core.oauth2.granter;

import io.jsonwebtoken.Claims;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.oauth2.exception.OAuth2ErrorCode;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.utils.OAuth2ExceptionUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * PasswordTokenGranter
 *
 * @author BladeX
 */
@Component
public class RefreshTokenGranter extends PasswordTokenGranter {

	private final JwtProperties jwtProperties;

	public RefreshTokenGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler, JwtProperties jwtProperties) {
		super(clientService, userService, passwordHandler);
		this.jwtProperties = jwtProperties;
	}

	@Override
	public String type() {
		return REFRESH_TOKEN;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		String refreshToken = request.getRefreshToken();
		Claims refreshClaims = JwtUtil.parseJWT(refreshToken);
		if (refreshClaims == null) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_USER);
		}
		// 校验refreshToken的合法性
		if (!judgeRefreshToken(refreshClaims, refreshToken, request)) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_REFRESH_TOKEN);
		}
		// 校验refreshToken的格式
		String tokenType = String.valueOf(Objects.requireNonNull(refreshClaims).get(TokenConstant.TOKEN_TYPE));
		if (!StringUtil.equals(tokenType, TokenConstant.REFRESH_TOKEN)) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_REFRESH_TOKEN);
		}
		// 校验refreshToken是否可获取username
		String userId = String.valueOf(refreshClaims.get(TokenConstant.USER_ID));
		if (StringUtil.isBlank(userId)) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.USER_NOT_FOUND);
		}
		// 设置username
		request.setUserId(userId);
		return super.user(request);
	}


	/**
	 * 校验refreshToken的合法性
	 *
	 * @param refreshToken 待校验的refreshToken
	 * @return refreshToken是否合法
	 */
	private boolean judgeRefreshToken(Claims refreshClaims, String refreshToken, OAuth2Request request) {
		// 首先检查JWT是否启用单人登录模式，如果不是则直接返回true
		if (!jwtProperties.getState() || !jwtProperties.getSingle()) {
			return true;
		}

		// 解析JWT，如果无法解析则认为不合法
		return Optional.ofNullable(refreshClaims)
			.map(claims -> {
				// 提取 tenantId clientId 和 userId
				String tenantId = request.getTenantId();
				String clientId = request.getClientId();
				String userId = String.valueOf(claims.get(TokenConstant.USER_ID));

				// 根据提取的信息和refreshToken生成新的token
				String token = JwtUtil.getRefreshToken(tenantId, clientId, userId, refreshToken);

				// 比较新生成的token与传入的refreshToken是否一致，如果一致则认为合法
				return StringUtil.equalsIgnoreCase(token, refreshToken);
			})
			.orElse(false); // 如果claims为空，则返回false
	}

}
