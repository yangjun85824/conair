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

import lombok.Getter;

import java.io.Serial;

/**
 * OAuth2通用异常
 *
 * @author BladeX
 */
@Getter
public class OAuth2Exception extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	private final ExceptionCode exceptionCode;

	public OAuth2Exception(String message) {
		super(message);
		this.exceptionCode = ExceptionCode.ACCESS_DENIED;
	}

	public OAuth2Exception(ExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}

	public OAuth2Exception(ExceptionCode exceptionCode, String message) {
		super(message);
		this.exceptionCode = exceptionCode;
	}

	public OAuth2Exception(ExceptionCode exceptionCode, Throwable cause) {
		super(cause);
		this.exceptionCode = exceptionCode;
	}

	public OAuth2Exception(ExceptionCode exceptionCode, String message, Throwable cause) {
		super(message, cause);
		this.exceptionCode = exceptionCode;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
