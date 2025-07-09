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
package org.springblade.core.secure.provider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.api.ResultCode;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * ResponseProvider
 *
 * @author Chill
 */
@Slf4j
public class ResponseProvider {

	public static void logAuthFailure(HttpServletRequest request, HttpServletResponse response, String reason) {
		try {
			Map<String, String[]> parameterMap = request.getParameterMap();
			String paramsJson = JsonUtil.toJson(parameterMap);
			log.warn("{}，请求接口：{}，请求IP：{}，请求参数：{}", reason, request.getRequestURI(), WebUtil.getIP(request), paramsJson);
		} catch (Exception e) {
			log.error("日志记录失败", e);
		}
		ResponseProvider.write(response);
	}

	public static void write(HttpServletResponse response) {
		R result = R.fail(ResultCode.UN_AUTHORIZED);
		response.setCharacterEncoding(BladeConstant.UTF_8);
		response.addHeader(BladeConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		try {
			response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}

}
