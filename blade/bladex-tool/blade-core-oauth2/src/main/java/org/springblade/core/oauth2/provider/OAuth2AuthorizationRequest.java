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
package org.springblade.core.oauth2.provider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.WebUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springblade.core.oauth2.constant.OAuth2ParameterConstant.*;

/**
 * OAuth2AuthorizationRequest
 *
 * @author BladeX
 */
@Data
public class OAuth2AuthorizationRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String responseType;
	private String tenantId;
	private String clientId;
	private String redirectUri;
	private String scope;
	private String state;

	/**
	 * 实例化
	 */
	public static OAuth2AuthorizationRequest create() {
		return new OAuth2AuthorizationRequest();
	}

	/**
	 * 构建参数
	 *
	 * @return OAuth2AuthorizationRequest
	 */
	public OAuth2AuthorizationRequest buildParameters() {
		HttpServletRequest request = Objects.requireNonNull(WebUtil.getRequest());
		this.responseType = request.getParameter(RESPONSE_TYPE);
		this.tenantId = request.getParameter(TENANT_ID);
		this.clientId = request.getParameter(CLIENT_ID);
		this.redirectUri = request.getParameter(REDIRECT_URI);
		this.scope = request.getParameter(SCOPE);
		this.state = request.getParameter(STATE);
		return this;
	}

	/**
	 * 获取参数
	 *
	 * @return Map
	 */
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(RESPONSE_TYPE, this.responseType);
		parameters.put(CLIENT_ID, this.clientId);
		parameters.put(REDIRECT_URI, this.redirectUri);
		if (scope != null) {
			parameters.put(SCOPE, this.scope);
		}
		if (this.tenantId != null) {
			parameters.put(STATE, this.tenantId);
		}
		if (state != null) {
			parameters.put(STATE, this.state);
		}
		return parameters;
	}

	public String getState() {
		return StringUtil.isBlank(this.state) ? this.tenantId : this.state;
	}
}
