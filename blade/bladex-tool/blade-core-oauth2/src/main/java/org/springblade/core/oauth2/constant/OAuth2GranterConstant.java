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
 * GranterTypeConstant
 *
 * @author BladeX
 */
public interface OAuth2GranterConstant {

	/**
	 * 授权码模式
	 */
	String AUTHORIZATION_CODE = "authorization_code";
	/**
	 * 密码模式
	 */
	String PASSWORD = "password";
	/**
	 * 刷新token模式
	 */
	String REFRESH_TOKEN = "refresh_token";
	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";
	/**
	 * 简化模式
	 */
	String IMPLICIT = "implicit";
	/**
	 * 验证码模式
	 */
	String CAPTCHA = "captcha";
	/**
	 * 手机验证码模式
	 */
	String SMS_CODE = "sms_code";
	/**
	 * 微信小程序模式
	 */
	String WECHAT_APPLET = "wechat_applet";
	/**
	 * 开放平台模式
	 */
	String SOCIAL = "social";
	/**
	 * 注册模式
	 */
	String REGISTER = "register";

}
