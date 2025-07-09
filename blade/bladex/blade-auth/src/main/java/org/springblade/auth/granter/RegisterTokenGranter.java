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

import org.jetbrains.annotations.NotNull;
import org.springblade.core.oauth2.exception.UserInvalidException;
import org.springblade.core.oauth2.granter.AbstractTokenGranter;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.service.impl.OAuth2UserDetail;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.NumberUtil;
import org.springblade.core.tool.utils.SM2Util;
import org.springblade.system.cache.ParamCache;
import org.springblade.system.feign.IUserClient;
import org.springblade.system.pojo.entity.User;
import org.springblade.system.pojo.enums.UserType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Predicate;

import static org.springblade.auth.constant.BladeAuthConstant.REGISTER_USER_VALUE;

/**
 * RegisterTokenGranter
 *
 * @author BladeX
 */
@Component
public class RegisterTokenGranter extends AbstractTokenGranter {

	private final IUserClient userClient;
	private final OAuth2Properties properties;

	public RegisterTokenGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler, IUserClient userClient, OAuth2Properties properties) {
		super(clientService, userService, passwordHandler);
		this.userClient = userClient;
		this.properties = properties;
	}

	@Override
	public String type() {
		return REGISTER;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		// 校验注册功能是否开启
		Boolean registerOpen = Func.toBoolean(ParamCache.getValue(REGISTER_USER_VALUE), false);
		if (!registerOpen) {
			throw new UserInvalidException("注册功能暂未开启，请联系管理员");
		}

		// 用户注册信息
		User user = new User();
		user.setUserType(UserType.WEB.getCategory());
		user.setTenantId(request.getTenantId());
		user.setAccount(request.getUsername());
		user.setPassword(SM2Util.decrypt(request.getPassword(), properties.getPublicKey(), properties.getPrivateKey()));
		user.setName(request.getName());
		user.setRealName(request.getName());
		user.setPhone(request.getPhone());
		user.setEmail(request.getEmail());

		// 校验用户格式
		validateUser(user);

		R<String> result = userClient.registerUser(user);

		// 执行用户注册
		if (result.isSuccess()) {
			// 构建oauth2所需用户信息
			user.setId(NumberUtil.toLong(result.getData()));
			return convertOAuth2UserDetail(user, client(request));
		}
		throw new UserInvalidException(result.getMsg());
	}

	private void validateUser(User user) {
		Predicate<String> isNameValid = name -> name.matches("^([\\u4e00-\\u9fa5]{2,20}|[a-zA-Z]{2,10})$");
		Predicate<String> isUsernameValid = username -> username.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9_\\-@]{3,20}$");
		Predicate<String> isPasswordValid = password -> password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])[\\w@-]{6,20}$");
		Predicate<String> isPhoneValid = phone -> phone.matches("^1[3-9]\\d{9}$");
		Predicate<String> isEmailValid = email -> email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
		if (!isNameValid.test(user.getName())) {
			throw new UserInvalidException("用户姓名长度必须在2-10之间，且仅能设置纯中文或纯英文");
		}
		if (!isUsernameValid.test(user.getAccount())) {
			throw new UserInvalidException("用户账号长度必须在3-20之间，且需要包含英文，可额外携带数字、下划线、横杠、@");
		}
		if (!isPasswordValid.test(user.getPassword())) {
			throw new UserInvalidException("用户密码长度必须在6-20之间，且需要包含英文与数字，可额外携带下划线、横杠、@");
		}
		if (!isPhoneValid.test(user.getPhone())) {
			throw new UserInvalidException("手机号格式不正确");
		}
		if (!isEmailValid.test(user.getEmail())) {
			throw new UserInvalidException("邮箱格式不正确");
		}
	}

	@NotNull
	private OAuth2UserDetail convertOAuth2UserDetail(User user, OAuth2Client client) {
		OAuth2UserDetail userDetail = new OAuth2UserDetail();
		userDetail.setUserId(String.valueOf(user.getId()));
		userDetail.setTenantId(user.getTenantId());
		userDetail.setName(user.getName());
		userDetail.setRealName(user.getName());
		userDetail.setAccount(user.getAccount());
		userDetail.setPassword(user.getPassword());
		userDetail.setPhone(user.getPhone());
		userDetail.setEmail(user.getEmail());
		userDetail.setAuthorities(Collections.singletonList(REGISTER));
		userDetail.setClient(client);
		return userDetail;
	}
}
