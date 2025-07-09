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
package org.springblade.core.oauth2.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.core.oauth2.constant.OAuth2ClientConstant;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Optional;

/**
 * 获取客户端详情
 *
 * @author BladeX
 */
@AllArgsConstructor
public class OAuth2ClientDetailService implements OAuth2ClientService {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public OAuth2Client loadByClientId(String clientId) {
		return loadByClientId(clientId, null);
	}

	@Override
	public OAuth2Client loadByClientId(String clientId, OAuth2Request request) {
		try {
			return jdbcTemplate.queryForObject(OAuth2ClientConstant.DEFAULT_SELECT_STATEMENT, new BeanPropertyRowMapper<>(OAuth2ClientDetail.class), clientId);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public boolean validateClient(OAuth2Client client, String clientId, String clientSecret) {
		return Optional.ofNullable(client)
			.map(c -> StringUtil.equals(clientId, c.getClientId()) && StringUtil.equals(clientSecret, c.getClientSecret()))
			.orElse(false);
	}

	@Override
	public boolean validateRedirectUri(OAuth2Client client, String redirectUri) {
		return Optional.ofNullable(client)
			.map(c -> StringUtil.equals(redirectUri, c.getWebServerRedirectUri()))
			.orElse(false);
	}

	@Override
	public boolean validateGranter(OAuth2Client client, String grantType) {
		return Optional.ofNullable(client)
			.map(c -> Arrays.stream(Func.split(c.getAuthorizedGrantTypes(), StringPool.COMMA))
				.anyMatch(s -> s.trim().equals(grantType)))
			.orElse(false);
	}

}
