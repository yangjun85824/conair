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
package org.springblade.core.oauth2.provider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springblade.core.oauth2.utils.OAuth2Util;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.WebUtil;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.springblade.core.oauth2.constant.OAuth2GranterConstant.PASSWORD;
import static org.springblade.core.oauth2.constant.OAuth2GranterConstant.REFRESH_TOKEN;
import static org.springblade.core.oauth2.constant.OAuth2GranterConstant.*;
import static org.springblade.core.oauth2.constant.OAuth2ParameterConstant.*;
import static org.springblade.core.oauth2.constant.OAuth2TokenConstant.*;

/**
 * OAuth2参数类
 *
 * @author BladeX
 */
@Data
public class OAuth2Request {

	/**
	 * 实例化
	 */
	public static OAuth2Request create() {
		return new OAuth2Request();
	}

	/**
	 * 客户端参数
	 */
	private Kv clientArgs = Kv.create();

	/**
	 * 请求参数
	 */
	private Kv parameterArgs = Kv.create();

	/**
	 * 头部参数
	 */
	private Kv headerArgs = Kv.create();

	/**
	 * 自动构建参数
	 *
	 * @return OAuth2Request
	 */
	public OAuth2Request buildArgs() {
		return this.buildClientArgs().buildParameterArgs().buildHeaderArgs();
	}

	/**
	 * 自动构建客户端参数
	 *
	 * @return OAuth2Request
	 */
	public OAuth2Request buildClientArgs() {
		String[] tokens = getClientFromAuthorization();
		assert tokens.length == 2;
		clientArgs.set(CLIENT_ID, tokens[0]);
		clientArgs.set(CLIENT_SECRET, tokens[1]);
		return this;
	}

	/**
	 * 自动构建请求头参数
	 *
	 * @return OAuth2Request
	 */
	public OAuth2Request buildParameterArgs() {
		HttpServletRequest request = Objects.requireNonNull(WebUtil.getRequest());
		Arrays.stream(new String[]{
			CLIENT_ID, CLIENT_SECRET, ACCESS_TOKEN, REFRESH_TOKEN, TENANT_ID, USERNAME, PASSWORD, NAME, PHONE, EMAIL, GRANT_TYPE, SCOPE, REDIRECT_URI, RESPONSE_TYPE, CODE, STATE, SOURCE
		}).forEach(param -> Optional.ofNullable(request.getParameter(param)).ifPresent(value -> parameterArgs.set(param, value)));
		return this;
	}

	/**
	 * 自动构建头部参数
	 *
	 * @return OAuth2Request
	 */
	public OAuth2Request buildHeaderArgs() {
		HttpServletRequest request = Objects.requireNonNull(WebUtil.getRequest());
		Arrays.stream(new String[]{
			HEADER_AUTHORIZATION, TENANT_HEADER, USER_HEADER, ROLE_HEADER, DEPT_HEADER, USER_TYPE_HEADER, CAPTCHA_HEADER_KEY, CAPTCHA_HEADER_CODE
		}).forEach(param -> Optional.ofNullable(request.getHeader(param)).ifPresent(value -> headerArgs.set(param, value)));
		return this;
	}

	/**
	 * 获取请求头中的客户端id
	 */
	public String getClientId() {
		return clientArgs.getStr(CLIENT_ID);
	}

	/**
	 * 获取请求头中的客户端密钥
	 */
	public String getClientSecret() {
		return clientArgs.getStr(CLIENT_SECRET);
	}

	/**
	 * 获取客户端ID和密钥
	 *
	 * @return String[]
	 */
	public String[] getClientFromAuthorization() {
		return OAuth2Util.extractAndDecodeAuthorization();
	}

	/**
	 * 获取客户端ID
	 *
	 * @return String
	 */
	public String getClientIdFromParameter() {
		return parameterArgs.getStr(CLIENT_ID);
	}

	/**
	 * 获取客户端密钥
	 *
	 * @return String
	 */
	public String getClientSecretFromParameter() {
		return parameterArgs.getStr(CLIENT_SECRET);
	}

	/**
	 * 获取令牌
	 *
	 * @return String
	 */
	public String getToken() {
		return headerArgs.getStr(TOKEN_HEADER);
	}

	/**
	 * 获取租户编号
	 *
	 * @return String
	 */
	public String getTenantId() {
		if (StringUtil.isBlank(headerArgs.getStr(TENANT_HEADER))) {
			return parameterArgs.getStr(TENANT_ID);
		}
		return headerArgs.getStr(TENANT_HEADER);
	}

	/**
	 * 获取用户ID
	 *
	 * @return String
	 */
	public String getUserId() {
		return headerArgs.getStr(USER_HEADER);
	}

	/**
	 * 获取用户名
	 *
	 * @return String
	 */
	public String getUsername() {
		return parameterArgs.getStr(USERNAME);
	}

	/**
	 * 获取密码
	 *
	 * @return String
	 */
	public String getPassword() {
		return parameterArgs.getStr(PASSWORD);
	}

	/**
	 * 获取用户名字
	 *
	 * @return String
	 */
	public String getName() {
		return parameterArgs.getStr(NAME);
	}

	/**
	 * 获取手机号
	 *
	 * @return String
	 */
	public String getPhone() {
		return parameterArgs.getStr(PHONE);
	}

	/**
	 * 获取电子游戏
	 *
	 * @return String
	 */
	public String getEmail() {
		return parameterArgs.getStr(EMAIL);
	}

	/**
	 * 获取用户名
	 *
	 * @return String
	 */
	public String getUserType() {
		return headerArgs.getStr(USER_TYPE_HEADER);
	}

	/**
	 * 获取用户部门
	 *
	 * @return String
	 */
	public String getUserDept() {
		return headerArgs.getStr(DEPT_HEADER);
	}


	/**
	 * 获取用户角色
	 *
	 * @return String
	 */
	public String getUserRole() {
		return headerArgs.getStr(ROLE_HEADER);
	}


	/**
	 * 获取验证码key
	 */
	public String getCaptchaKey() {
		return headerArgs.getStr(CAPTCHA_HEADER_KEY);
	}

	/**
	 * 获取验证码code
	 */
	public String getCaptchaCode() {
		return headerArgs.getStr(CAPTCHA_HEADER_CODE);
	}

	/**
	 * 获取授权类型
	 *
	 * @return String
	 */
	public String getGrantType() {
		return parameterArgs.getStr(GRANT_TYPE);
	}

	/**
	 * 获取刷新令牌
	 *
	 * @return String
	 */
	public String getRefreshToken() {
		return parameterArgs.getStr(REFRESH_TOKEN);
	}

	/**
	 * 获取验证code
	 *
	 * @return String
	 */
	public String getCode() {
		return parameterArgs.getStr(CODE);
	}

	/**
	 * 获取状态
	 *
	 * @return String
	 */
	public String getState() {
		return parameterArgs.getStr(STATE);
	}

	/**
	 * 获取来源
	 *
	 * @return String
	 */
	public String getSource() {
		return parameterArgs.getStr(SOURCE);
	}

	/**
	 * 获取回调地址
	 *
	 * @return String
	 */
	public String getRedirectUri() {
		return parameterArgs.getStr(REDIRECT_URI);
	}

	/**
	 * 是否密码模式
	 *
	 * @return Boolean
	 */
	public Boolean isPassword() {
		return PASSWORD.equals(getGrantType());
	}

	/**
	 * 是否刷新模式
	 *
	 * @return Boolean
	 */
	public Boolean isRefreshToken() {
		return REFRESH_TOKEN.equals(getGrantType());
	}

	/**
	 * 是否验证码模式
	 *
	 * @return Boolean
	 */
	public Boolean isCaptchaCode() {
		return CAPTCHA.equals(getGrantType());
	}

	/**
	 * 是否密码模式
	 *
	 * @return Boolean
	 */
	public Boolean isClientCredentials() {
		return CLIENT_CREDENTIALS.equals(getGrantType());
	}

	/**
	 * 是否简化模式
	 *
	 * @return Boolean
	 */
	public Boolean isImplicit() {
		return IMPLICIT.equals(getGrantType());
	}


	/**
	 * 是否开放平台模式
	 *
	 * @return Boolean
	 */
	public Boolean isSocial() {
		return SOCIAL.equals(getGrantType());
	}

	/**
	 * 设置租户ID
	 *
	 * @param tenantId 户ID
	 */
	public void setTenantId(String tenantId) {
		this.headerArgs.set(TENANT_HEADER, tenantId);
	}

	/**
	 * 设置用户ID
	 *
	 * @param userId 用户ID
	 */
	public void setUserId(String userId) {
		this.headerArgs.set(USER_HEADER, userId);
	}

	/**
	 * 设置用户名
	 *
	 * @param username 用户名
	 */
	public void setUsername(String username) {
		this.parameterArgs.set(USERNAME, username);
	}

}
