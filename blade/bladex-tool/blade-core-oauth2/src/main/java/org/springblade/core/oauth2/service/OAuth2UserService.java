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
package org.springblade.core.oauth2.service;

import org.springblade.core.oauth2.provider.OAuth2Request;

/**
 * OAuth2UserService
 *
 * @author BladeX
 */
public interface OAuth2UserService {

	/**
	 * 根据用户名获取用户信息
	 *
	 * @param userId  用户ID
	 * @param request 授权参数
	 * @return 用户信息
	 */
	OAuth2User loadByUserId(String userId, OAuth2Request request);

	/**
	 * 根据用户名获取用户信息
	 *
	 * @param username 用户名
	 * @param request  授权参数
	 * @return 用户信息
	 */
	OAuth2User loadByUsername(String username, OAuth2Request request);

	/**
	 * 根据手机号获取用户信息
	 *
	 * @param phone   手机号
	 * @param request 授权参数
	 * @return 用户信息
	 */
	OAuth2User loadByPhone(String phone, OAuth2Request request);

	/**
	 * 校验用户信息
	 *
	 * @param user 用户信息
	 * @return 是否通过
	 */
	boolean validateUser(OAuth2User user);

}
