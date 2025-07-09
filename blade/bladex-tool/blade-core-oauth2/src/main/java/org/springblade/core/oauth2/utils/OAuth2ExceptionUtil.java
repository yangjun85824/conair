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
package org.springblade.core.oauth2.utils;

import org.springblade.core.oauth2.exception.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * OAuth2ExceptionUtil
 *
 * @author BladeX
 */
public class OAuth2ExceptionUtil {
	private static final Map<ExceptionCode, Supplier<OAuth2Exception>> OAUTH2_EXCEPTION = new ConcurrentHashMap<>(16);

	static {
		// 初始化异常映射
		OAUTH2_EXCEPTION.put(
			ExceptionCode.INVALID_REQUEST, () -> new OAuth2Exception(ExceptionCode.INVALID_REQUEST, ExceptionCode.INVALID_REQUEST.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.USER_NOT_FOUND, () -> new UsernameNotFoundException(ExceptionCode.USER_NOT_FOUND.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.USER_TENANT_NOT_FOUND, () -> new UserInvalidException(ExceptionCode.USER_TENANT_NOT_FOUND.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.USER_TOO_MANY_FAILS, () -> new UserInvalidException(ExceptionCode.USER_TOO_MANY_FAILS.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.INVALID_USER, () -> new UserInvalidException(ExceptionCode.INVALID_USER.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.UNAUTHORIZED_USER, () -> new UserUnauthorizedException(ExceptionCode.UNAUTHORIZED_USER.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.UNAUTHORIZED_USER_TENANT, () -> new UserUnauthorizedException(ExceptionCode.UNAUTHORIZED_USER_TENANT.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.INVALID_REFRESH_TOKEN, () -> new GranterInvalidException(ExceptionCode.INVALID_REFRESH_TOKEN.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.CLIENT_NOT_FOUND, () -> new ClientNotFoundException(ExceptionCode.CLIENT_NOT_FOUND.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.INVALID_CLIENT, () -> new ClientInvalidException(ExceptionCode.INVALID_CLIENT.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.INVALID_CLIENT_REDIRECT_URI, () -> new ClientInvalidException(ExceptionCode.INVALID_CLIENT_REDIRECT_URI.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.UNAUTHORIZED_CLIENT, () -> new ClientUnauthorizedException(ExceptionCode.UNAUTHORIZED_CLIENT.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.UNSUPPORTED_GRANT_TYPE, () -> new OAuth2Exception(ExceptionCode.UNSUPPORTED_GRANT_TYPE, ExceptionCode.UNSUPPORTED_GRANT_TYPE.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.INVALID_GRANTER, () -> new GranterInvalidException(ExceptionCode.INVALID_GRANTER.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.INVALID_SCOPE, () -> new OAuth2Exception(ExceptionCode.INVALID_SCOPE, ExceptionCode.INVALID_SCOPE.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.SERVER_ERROR, () -> new OAuth2Exception(ExceptionCode.SERVER_ERROR, ExceptionCode.SERVER_ERROR.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.ACCESS_DENIED, () -> new OAuth2Exception(ExceptionCode.ACCESS_DENIED, ExceptionCode.ACCESS_DENIED.getMessage())
		);
		OAUTH2_EXCEPTION.put(
			ExceptionCode.TEMPORARILY_UNAVAILABLE, () -> new OAuth2Exception(ExceptionCode.TEMPORARILY_UNAVAILABLE, ExceptionCode.TEMPORARILY_UNAVAILABLE.getMessage())
		);
	}

	/**
	 * 根据错误代码抛出异常
	 *
	 * @param code 错误代码
	 */
	public static void throwFromCode(int code) {
		Supplier<OAuth2Exception> exceptionSupplier = OAUTH2_EXCEPTION.get(ExceptionCode.of(code));
		if (exceptionSupplier != null) {
			throw exceptionSupplier.get();
		} else {
			throw new UserInvalidException(ExceptionCode.INVALID_USER.getMessage());
		}
	}

}
