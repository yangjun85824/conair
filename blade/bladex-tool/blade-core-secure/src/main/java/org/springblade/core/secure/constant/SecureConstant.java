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
package org.springblade.core.secure.constant;

/**
 * 授权校验常量
 *
 * @author Chill
 */
public interface SecureConstant {

	/**
	 * 认证请求头
	 */
	String BASIC_HEADER_KEY = "Authorization";

	/**
	 * 认证请求头前缀
	 */
	String BASIC_HEADER_PREFIX = "Basic ";

	/**
	 * 认证请求头前缀
	 */
	String BASIC_HEADER_PREFIX_EXT = "Basic%20";

	/**
	 * 认证请求头
	 */
	String BASIC_REALM_HEADER_KEY = "WWW-Authenticate";

	/**
	 * 认证请求值
	 */
	String BASIC_REALM_HEADER_VALUE = "basic realm=\"no auth\"";

	/**
	 * 授权认证失败
	 */
	String AUTHORIZATION_FAILED = "授权认证失败";

	/**
	 * 签名认证失败
	 */
	String SIGN_FAILED = "签名认证失败";

	/**
	 * 用户信息不完整
	 */
	String USER_INCOMPLETE = "用户信息不完整，签名认证失败";
	/**
	 * 请求头信息不完整
	 */
	String SECURE_HEADER_INCOMPLETE = "请求头信息不完整，签名认证失败";
	/**
	 * 客户端令牌解析失败
	 */
	String CLIENT_TOKEN_PARSE_FAILED = "客户端令牌解析失败";
	/**
	 * 客户端令牌不合法
	 */
	String INVALID_CLIENT_TOKEN = "客户端令牌不合法";
	/**
	 * Authorization未找到
	 */
	String AUTHORIZATION_NOT_FOUND = "请求头中未找到 [Authorization] 信息";

}
