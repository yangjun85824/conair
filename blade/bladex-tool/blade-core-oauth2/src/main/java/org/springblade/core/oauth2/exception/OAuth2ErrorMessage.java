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
package org.springblade.core.oauth2.exception;

/**
 * OAuth2ErrorMessage
 *
 * @author BladeX
 */
public interface OAuth2ErrorMessage {
	String INVALID_GRANT_TYPE = "无效的授权类型: %s";

	String CLIENT_AUTHORIZATION_FAILED = "客户端认证失败, 请检查请求头 [Authorization] 信息";

	String CLIENT_TOKEN_PARSE_FAILED = "客户端令牌解析失败";

	String INVALID_CLIENT_TOKEN = "客户端令牌不合法";

	String AUTHORIZATION_NOT_FOUND = "请求头中未找到 [Authorization] 信息";
	String INVALID_ERROR_CODE = "无效的错误代码: %s";
	String USER_HAS_NO_TENANT = "未获得用户的租户信息";
	String USER_HAS_NO_TENANT_PERMISSION = "租户授权已过期,请联系管理员";
	String USER_HAS_TOO_MANY_FAILS = "登录错误次数过多,请稍后再试";
}
