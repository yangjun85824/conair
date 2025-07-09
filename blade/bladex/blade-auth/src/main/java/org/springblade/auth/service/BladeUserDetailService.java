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
package org.springblade.auth.service;

import lombok.RequiredArgsConstructor;
import org.springblade.auth.utils.TokenUtil;
import org.springblade.core.oauth2.exception.OAuth2ErrorCode;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.utils.OAuth2ExceptionUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.pojo.entity.UserInfo;
import org.springblade.system.pojo.enums.UserType;
import org.springblade.system.feign.IUserClient;

import java.util.Optional;

/**
 * BladeUserDetailService
 *
 * @author Chill
 */
@RequiredArgsConstructor
public class BladeUserDetailService implements OAuth2UserService {
	private final IUserClient userClient;

	@Override
	public OAuth2User loadByUserId(String userId, OAuth2Request request) {
		// 获取用户参数
		String userType = Optional.ofNullable(request.getUserType())
			.filter(s -> !StringUtil.isBlank(s))
			.orElse(UserType.WEB.getName());

		// 获取用户信息
		R<UserInfo> result = userClient.userInfo(Func.toLong(userId), userType);
		if (!result.isSuccess()) {
			OAuth2ExceptionUtil.throwFromCode(OAuth2ErrorCode.USER_NOT_FOUND);
		}
		// 构建oauth2用户信息
		return TokenUtil.convertUser(result.getData(), request);
	}

	@Override
	public OAuth2User loadByUsername(String username, OAuth2Request request) {
		// 获取用户参数
		String userType = Optional.ofNullable(request.getUserType())
			.filter(s -> !StringUtil.isBlank(s))
			.orElse(UserType.WEB.getName());
		String tenantId = request.getTenantId();

		// 获取用户信息
		R<UserInfo> result = userClient.userInfo(tenantId, username, userType);
		if (!result.isSuccess()) {
			return null;
		}
		// 构建oauth2用户信息
		return TokenUtil.convertUser(result.getData(), request);
	}

	@Override
	public OAuth2User loadByPhone(String phone, OAuth2Request request) {
		// 获取用户参数
		String userType = Optional.ofNullable(request.getUserType())
			.filter(s -> !StringUtil.isBlank(s))
			.orElse(UserType.WEB.getName());
		String tenantId = request.getTenantId();

		// 获取用户信息
		R<UserInfo> result = userClient.userInfoByPhone(tenantId, phone, userType);
		if (!result.isSuccess()) {
			return null;
		}
		// 构建oauth2用户信息
		return TokenUtil.convertUser(result.getData(), request);
	}

	@Override
	public boolean validateUser(OAuth2User user) {
		return Optional.ofNullable(user)
			.filter(u -> u.getUserId() != null && !u.getUserId().isEmpty()) // 检查userId不为空
			.filter(u -> u.getAuthorities() != null && !u.getAuthorities().isEmpty()) // 检查authorities不为空
			.isPresent(); // 如果上述条件都满足，则返回true，否则返回false
	}

}
