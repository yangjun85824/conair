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
 * Author: DreamLu (596392912@qq.com)
 */

package org.springblade.core.api.crypto.core;

import lombok.RequiredArgsConstructor;
import org.springblade.core.api.crypto.annotation.decrypt.ApiDecrypt;
import org.springblade.core.api.crypto.bean.CryptoInfoBean;
import org.springblade.core.api.crypto.config.ApiCryptoProperties;
import org.springblade.core.api.crypto.util.ApiCryptoUtil;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Charsets;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Parameter;

/**
 * param 参数 解析
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class ApiDecryptParamResolver implements HandlerMethodArgumentResolver {
	private final ApiCryptoProperties properties;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return AnnotatedElementUtils.hasAnnotation(parameter.getParameter(), ApiDecrypt.class);
	}

	@Nullable
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Parameter parameter = methodParameter.getParameter();
		ApiDecrypt apiDecrypt = AnnotatedElementUtils.getMergedAnnotation(parameter, ApiDecrypt.class);
		String text = webRequest.getParameter(properties.getParamName());
		if (StringUtil.isBlank(text)) {
			return null;
		}
		CryptoInfoBean infoBean = new CryptoInfoBean(apiDecrypt.value(), apiDecrypt.secretKey());
		byte[] textBytes = text.getBytes(Charsets.UTF_8);
		byte[] decryptData = ApiCryptoUtil.decryptData(properties, textBytes, infoBean);
		return JsonUtil.readValue(decryptData, parameter.getType());
	}
}
