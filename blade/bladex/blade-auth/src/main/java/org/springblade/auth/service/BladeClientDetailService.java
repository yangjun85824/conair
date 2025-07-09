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
package org.springblade.auth.service;

import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.impl.OAuth2ClientDetailService;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * BladeClientDetailService
 *
 * @author Chill
 */
public class BladeClientDetailService extends OAuth2ClientDetailService {
	public BladeClientDetailService(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	public OAuth2Client loadByClientId(String clientId) {
		return super.loadByClientId(clientId);
	}

	@Override
	public OAuth2Client loadByClientId(String clientId, OAuth2Request request) {
		return super.loadByClientId(clientId, request);
	}

	@Override
	public boolean validateClient(OAuth2Client client, String clientId, String clientSecret) {
		return super.validateClient(client, clientId, clientSecret);
	}

	@Override
	public boolean validateGranter(OAuth2Client client, String grantType) {
		return super.validateGranter(client, grantType);
	}
}
