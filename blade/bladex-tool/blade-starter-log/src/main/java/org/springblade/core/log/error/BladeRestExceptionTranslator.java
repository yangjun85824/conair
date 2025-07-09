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
package org.springblade.core.log.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.log.props.BladeRequestLogProperties;
import org.springblade.core.log.publisher.ErrorLogPublisher;
import org.springblade.core.secure.exception.SecureException;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.api.ResultCode;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.UrlUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.Servlet;

import java.util.Objects;

/**
 * 未知异常转译和发送，方便监听，对未知异常统一处理。Order 排序优先级低
 *
 * @author Chill
 */
@Slf4j
@Order
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@RestControllerAdvice
public class BladeRestExceptionTranslator {

	private final BladeProperties bladeProperties;
	private final BladeRequestLogProperties requestLogProperties;

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R handleError(ServiceException e) {
		log.error("业务异常", e);
		return R.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(SecureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public R handleError(SecureException e) {
		log.error("认证异常", e);
		return R.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleError(Throwable e) {
		log.error("服务器异常", e);
		if (requestLogProperties.getErrorLog()) {
			//发送服务异常事件
			ErrorLogPublisher.publishEvent(e, UrlUtil.getPath(Objects.requireNonNull(WebUtil.getRequest()).getRequestURI()));
		}
		// 生产环境屏蔽具体异常信息返回
		if (bladeProperties.isProd()) {
			return R.fail(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return R.fail(ResultCode.INTERNAL_SERVER_ERROR, (Func.isEmpty(e.getMessage()) ? ResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage()));
	}

}
