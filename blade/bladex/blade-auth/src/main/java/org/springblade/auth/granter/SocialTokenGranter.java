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
package org.springblade.auth.granter;

import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springblade.auth.utils.TokenUtil;
import org.springblade.core.oauth2.exception.OAuth2ErrorCode;
import org.springblade.core.oauth2.granter.AbstractTokenGranter;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.utils.OAuth2ExceptionUtil;
import org.springblade.core.social.props.SocialProperties;
import org.springblade.core.social.utils.SocialUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.system.pojo.entity.UserInfo;
import org.springblade.system.pojo.entity.UserOauth;
import org.springblade.system.feign.IUserClient;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * SocialTokenGranter
 *
 * @author Chill
 */
@Component
public class SocialTokenGranter extends AbstractTokenGranter {


	private static final Integer AUTH_SUCCESS_CODE = 2000;

	private final IUserClient userClient;
	private final SocialProperties socialProperties;


	public SocialTokenGranter(OAuth2ClientService clientService, OAuth2UserService oAuth2UserService, PasswordHandler passwordHandler, IUserClient userClient, SocialProperties socialProperties) {
		super(clientService, oAuth2UserService, passwordHandler);
		this.userClient = userClient;
		this.socialProperties = socialProperties;
	}

	@Override
	public String type() {
		return SOCIAL;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		String tenantId = request.getTenantId();
		// 开放平台来源
		String sourceParameter = request.getSource();
		// 匹配是否有别名定义
		String source = socialProperties.getAlias().getOrDefault(sourceParameter, sourceParameter);
		// 开放平台授权码
		String code = request.getCode();
		// 开放平台状态吗
		String state = request.getState();

		// 获取开放平台授权数据
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		AuthCallback authCallback = new AuthCallback();
		authCallback.setCode(code);
		authCallback.setState(state);
		AuthResponse<?> authResponse = authRequest.login(authCallback);
		AuthUser authUser = null;
		if (authResponse.getCode() == AUTH_SUCCESS_CODE) {
			authUser = (AuthUser) authResponse.getData();
		} else {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.INVALID_USER);
		}

		// 组装数据
		UserOauth userOauth = Objects.requireNonNull(BeanUtil.copyProperties(authUser, UserOauth.class));
		userOauth.setSource(authUser.getSource());
		userOauth.setTenantId(tenantId);
		userOauth.setUuid(authUser.getUuid());
		R<UserInfo> result = userClient.userAuthInfo(userOauth);
		if (!result.isSuccess()) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.USER_NOT_FOUND);
		}

		// 设置Oauth2用户信息
		OAuth2User user = TokenUtil.convertUser(result.getData(), request);
		// 设置客户端信息
		user.setClient(client(request));
		return user;
	}

}
