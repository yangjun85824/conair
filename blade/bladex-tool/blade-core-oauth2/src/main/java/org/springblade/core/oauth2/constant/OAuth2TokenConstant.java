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
 * TokenConstant
 *
 * @author BladeX
 */
public interface OAuth2TokenConstant {

	String HEADER_AUTHORIZATION = "Authorization";
	String HEADER_AUTHORIZATION_PREFIX = "Basic ";
	String TOKEN_HEADER = "Blade-Auth";
	String TENANT_HEADER = "Tenant-Id";
	String DEFAULT_TENANT_ID = "000000";
	String USER_HEADER = "User-Id";
	String DEPT_HEADER = "Dept-Id";
	String ROLE_HEADER = "Role-Id";
	String USER_TYPE_HEADER = "User-Type";
	String DEFAULT_USER_TYPE = "web";
	String USER_FAIL_KEY = "blade:user::blade:fail:";
	String CAPTCHA_CACHE_KEY = "blade:auth::blade:captcha:";
	String CAPTCHA_HEADER_KEY = "Captcha-Key";
	String CAPTCHA_HEADER_CODE = "Captcha-Code";
	String CAPTCHA_NOT_CORRECT = "验证码不正确";
	String TOKEN_NOT_CORRECT = "令牌授权不正确";
	String TOKEN_NOT_PERMISSION = "令牌授权已过期";
	String USER_NOT_FOUND = "用户名或密码错误";
	String USER_PHONE_NOT_FOUND = "用户手机未注册";
	String USER_HAS_NO_ROLE = "未获得用户的角色信息";
	String USER_HAS_NO_TENANT = "未获得用户的租户信息";
	String USER_HAS_NO_TENANT_PERMISSION = "租户授权已过期,请联系管理员";
	String USER_HAS_TOO_MANY_FAILS = "登录错误次数过多,请稍后再试";
	String DEFAULT_AVATAR = "https://bladex.cn/images/logo.png";
}
