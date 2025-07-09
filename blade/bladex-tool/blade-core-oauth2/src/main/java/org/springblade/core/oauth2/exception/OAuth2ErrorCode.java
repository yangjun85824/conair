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
 * OAuth2ErrorCodes
 *
 * @author BladeX
 */
public interface OAuth2ErrorCode {
	/**
	 * 无效请求 - 请求缺少必要的参数或格式不正确。
	 */
	int INVALID_REQUEST = 2000;
	/**
	 * 用户不存在 - 指定的用户ID不存在或无效。
	 */
	int USER_NOT_FOUND = 2001;
	/**
	 * 用户租户不存在 - 指定的用户租户未授权。
	 */
	int USER_TENANT_NOT_FOUND = 2002;
	/**
	 * 用户登录失败次数过多 - 用户登录失败次数过多。
	 */
	int USER_TOO_MANY_FAILS = 2003;
	/**
	 * 用户认证失败 - 指定的用户认证信息错误或无效。
	 */
	int INVALID_USER = 2004;
	/**
	 * 用户未授权 - 指定的用户未授权。
	 */
	int UNAUTHORIZED_USER = 2005;
	/**
	 * 用户租户未授权 - 指定的用户租户未授权。
	 */
	int UNAUTHORIZED_USER_TENANT = 2006;
	/**
	 * 令牌刷新错误或无效 - 刷新令牌认证信息错误或无效。
	 */
	int INVALID_REFRESH_TOKEN = 2010;
	/**
	 * 客户端不存在 - 指定的客户端ID不存在或无效。
	 */
	int CLIENT_NOT_FOUND = 3000;
	/**
	 * 客户端认证失败 - 客户端提供的认证信息错误或无效。
	 */
	int INVALID_CLIENT = 3001;
	/**
	 * 回调地址错误或无效 - 客户端回调地址错误或无效。
	 */
	int INVALID_CLIENT_REDIRECT_URI = 3002;
	/**
	 * 客户端未授权 - 客户端无权执行此操作。
	 */
	int UNAUTHORIZED_CLIENT = 3003;
	/**
	 * 不支持的授权类型 - 请求的授权类型不被服务器支持。
	 */
	int UNSUPPORTED_GRANT_TYPE = 4000;
	/**
	 * 无效的授权类型 - 提供的授权类型无效、过期或被撤销。
	 */
	int INVALID_GRANTER = 4001;
	/**
	 * 无效的授权范围 - 请求的授权范围无效、未知或格式不正确。
	 */
	int INVALID_SCOPE = 4002;
	/**
	 * 服务器错误 - 服务器内部错误，无法完成请求。
	 */
	int SERVER_ERROR = 5000;
	/**
	 * 访问被拒绝 - 由于各种原因，服务器拒绝执行此操作。
	 */
	int ACCESS_DENIED = 5001;
	/**
	 * 服务暂不可用 - 服务器暂时过载或维护，无法处理请求。
	 */
	int TEMPORARILY_UNAVAILABLE = 5002;
}
