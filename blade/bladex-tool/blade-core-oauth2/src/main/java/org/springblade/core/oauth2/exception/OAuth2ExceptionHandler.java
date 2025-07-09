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

import io.jsonwebtoken.JwtException;
import org.springblade.core.oauth2.endpoint.OAuth2AuthorizationEndpoint;
import org.springblade.core.oauth2.endpoint.OAuth2SocialEndpoint;
import org.springblade.core.oauth2.endpoint.OAuth2TokenEndPoint;
import org.springblade.core.oauth2.provider.OAuth2Response;
import org.springblade.core.secure.exception.SecureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * OAuth2ExceptionHandler
 *
 * @author BladeX
 */
@ControllerAdvice(basePackageClasses = {OAuth2AuthorizationEndpoint.class, OAuth2SocialEndpoint.class, OAuth2TokenEndPoint.class})
public class OAuth2ExceptionHandler {
	@ExceptionHandler(OAuth2Exception.class)
	public ResponseEntity<?> handleOAuth2Exception(OAuth2Exception ex) {
		// 统一处理验证失败的情况
		return ResponseEntity.ok(OAuth2Response.create().ofFailure(ex.getExceptionCode().getCode(), ex.getMessage()));
	}

	@ExceptionHandler(SecureException.class)
	public ResponseEntity<?> handleSecureException(SecureException ex) {
		// 统一处理验证失败的情况
		return ResponseEntity.ok(OAuth2Response.create().ofFailure(OAuth2ErrorCode.INVALID_USER, ex.getMessage()));
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<?> handleJwtException(JwtException ex) {
		// 统一处理验证失败的情况
		return ResponseEntity.ok(OAuth2Response.create().ofFailure(OAuth2ErrorCode.ACCESS_DENIED, ex.getMessage()));
	}
}
