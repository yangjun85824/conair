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

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springblade.core.oauth2.exception.OAuth2ErrorMessage.INVALID_ERROR_CODE;

/**
 * OAuth2ExceptionCode
 *
 * @author BladeX
 */
@Getter
@AllArgsConstructor
public enum ExceptionCode {

	/**
	 * 无效请求 - 请求缺少必要的参数或格式不正确。
	 */
	INVALID_REQUEST(OAuth2ErrorCode.INVALID_REQUEST, "无效请求"),

	/**
	 * 用户不存在 - 指定的用户ID不存在或无效。
	 */
	USER_NOT_FOUND(OAuth2ErrorCode.USER_NOT_FOUND, "用户不存在"),

	/**
	 * 用户租户不存在 - 指定的用户租户未授权。
	 */
	USER_TENANT_NOT_FOUND(OAuth2ErrorCode.USER_TENANT_NOT_FOUND, "用户租户不存在"),

	/**
	 * 用户登录失败次数过多 - 用户登录失败次数过多。
	 */
	USER_TOO_MANY_FAILS(OAuth2ErrorCode.USER_TOO_MANY_FAILS, "用户登录失败次数过多"),

	/**
	 * 用户认证失败 - 指定的用户认证信息错误或无效。
	 */
	INVALID_USER(OAuth2ErrorCode.INVALID_USER, "认证信息错误或无效"),

	/**
	 * 用户未授权 - 指定的用户未授权。
	 */
	UNAUTHORIZED_USER(OAuth2ErrorCode.UNAUTHORIZED_USER, "认证信息错误或无效"),

	/**
	 * 用户租户未授权 - 指定的用户租户未授权。
	 */
	UNAUTHORIZED_USER_TENANT(OAuth2ErrorCode.UNAUTHORIZED_USER_TENANT, "用户租户未授权"),

	/**
	 * 用户未授权 - 指定的用户未授权。
	 */
	INVALID_REFRESH_TOKEN(OAuth2ErrorCode.INVALID_REFRESH_TOKEN, "令牌刷新错误或无效"),

	/**
	 * 客户端不存在 - 指定的客户端ID不存在或无效。
	 */
	CLIENT_NOT_FOUND(OAuth2ErrorCode.CLIENT_NOT_FOUND, "客户端不存在"),

	/**
	 * 客户端认证失败 - 客户端提供的认证信息错误或无效。
	 */
	INVALID_CLIENT(OAuth2ErrorCode.INVALID_CLIENT, "客户端认证失败"),

	/**
	 * 回调地址错误或无效 - 客户端回调地址错误或无效。
	 */
	INVALID_CLIENT_REDIRECT_URI(OAuth2ErrorCode.INVALID_CLIENT_REDIRECT_URI, "客户端未授权"),

	/**
	 * 客户端未授权 - 客户端无权执行此操作。
	 */
	UNAUTHORIZED_CLIENT(OAuth2ErrorCode.UNAUTHORIZED_CLIENT, "客户端未授权"),

	/**
	 * 不支持的授权类型 - 请求的授权类型不被服务器支持。
	 */
	UNSUPPORTED_GRANT_TYPE(OAuth2ErrorCode.UNSUPPORTED_GRANT_TYPE, "不支持的授权类型"),

	/**
	 * 无效的授权类型 - 提供的授权令牌无效、过期或被撤销。
	 */
	INVALID_GRANTER(OAuth2ErrorCode.INVALID_GRANTER, "无效的授权类型"),

	/**
	 * 无效的无效的授权范围 - 请求的无效的授权范围无效、未知或格式不正确。
	 */
	INVALID_SCOPE(OAuth2ErrorCode.INVALID_SCOPE, "授权范围"),

	/**
	 * 服务器错误 - 服务器内部错误，无法完成请求。
	 */
	SERVER_ERROR(OAuth2ErrorCode.SERVER_ERROR, "服务器错误"),

	/**
	 * 访问被拒绝 - 由于各种原因，服务器拒绝执行此操作。
	 */
	ACCESS_DENIED(OAuth2ErrorCode.ACCESS_DENIED, "访问被拒绝"),

	/**
	 * 服务暂不可用 - 服务器暂时过载或维护，无法处理请求。
	 */
	TEMPORARILY_UNAVAILABLE(OAuth2ErrorCode.TEMPORARILY_UNAVAILABLE, "服务暂不可用");

	final int code;
	final String message;

	/**
	 * 通过错误代码获取枚举
	 *
	 * @param code 错误代码
	 * @return ExceptionCodeEnum
	 */
	public static ExceptionCode of(int code) {
		for (ExceptionCode value : ExceptionCode.values()) {
			if (value.code == code) {
				return value;
			}
		}
		throw new IllegalArgumentException(String.format(INVALID_ERROR_CODE, code));
	}
}

