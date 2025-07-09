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
import org.springblade.core.oauth2.constant.OAuth2UserConstant;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

/**
 * 获取用户详情
 *
 * @author BladeX
 */
@AllArgsConstructor
public class OAuth2UserDetailService implements OAuth2UserService {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public OAuth2User loadByUserId(String userId, OAuth2Request request) {
		try {
			return jdbcTemplate.queryForObject(OAuth2UserConstant.DEFAULT_USERID_SELECT_STATEMENT, new BeanPropertyRowMapper<>(OAuth2UserDetail.class), userId);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public OAuth2User loadByUsername(String username, OAuth2Request request) {
		try {
			return jdbcTemplate.queryForObject(OAuth2UserConstant.DEFAULT_USERNAME_SELECT_STATEMENT, new BeanPropertyRowMapper<>(OAuth2UserDetail.class), username);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public OAuth2User loadByPhone(String phone, OAuth2Request request) {
		try {
			return jdbcTemplate.queryForObject(OAuth2UserConstant.DEFAULT_PHONE_SELECT_STATEMENT, new BeanPropertyRowMapper<>(OAuth2UserDetail.class), phone);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public boolean validateUser(OAuth2User user) {
		return Optional.ofNullable(user)
			.filter(u -> u.getUserId() != null && !u.getUserId().isEmpty()) // 检查userId不为空
			.filter(u -> u.getAuthorities() != null && !u.getAuthorities().isEmpty()) // 检查authorities不为空
			.isPresent(); // 如果上述条件都满足，则返回true，否则返回false
	}
}
