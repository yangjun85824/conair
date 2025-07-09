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
 * OAuth2常量.
 *
 * @author BladeX
 */
public interface OAuth2ParameterConstant {

	/**
	 * 客户端id
	 */
	String CLIENT_ID = "client_id";
	/**
	 * 客户端密钥
	 */
	String CLIENT_SECRET = "client_secret";
	/**
	 * 令牌
	 */
	String ACCESS_TOKEN = "access_token";
	/**
	 * 刷新令牌
	 */
	String REFRESH_TOKEN = "refresh_token";
	/**
	 * 租户编号
	 */
	String TENANT_ID = "tenant_id";
	/**
	 * 用户名字
	 */
	String NAME = "name";
	/**
	 * 用户名
	 */
	String USERNAME = "username";
	/**
	 * 密码
	 */
	String PASSWORD = "password";
	/**
	 * 手机号
	 */
	String PHONE = "phone";
	/**
	 * 电子游戏
	 */
	String EMAIL = "email";
	/**
	 * 授权类型
	 */
	String GRANT_TYPE = "grant_type";
	/**
	 * 响应类型
	 */
	String SCOPE = "scope";
	/**
	 * 重定向地址
	 */
	String REDIRECT_URI = "redirect_uri";
	/**
	 * 返回类型
	 */
	String RESPONSE_TYPE = "response_type";
	/**
	 * 状态
	 */
	String STATE = "state";
	/**
	 * 验证
	 */
	String CODE = "code";
	/**
	 * 来源
	 */
	String SOURCE = "source";
	/**
	 * 自动授权
	 */
	String AUTO_APPROVE = "auto_approve";
}
