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
package org.springblade.core.oauth2.granter;

import org.springblade.core.oauth2.constant.OAuth2GranterConstant;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Token;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2User;

/**
 * 授权认证统一接口.
 *
 * @author BladeX
 */
public interface TokenGranter extends OAuth2GranterConstant {

	/**
	 * 获取授权模式
	 *
	 * @return String
	 */
	String type();

	/**
	 * 获取客户端信息
	 *
	 * @param request 授权参数
	 * @return OAuth2Client
	 */
	OAuth2Client client(OAuth2Request request);

	/**
	 * 获取用户信息
	 *
	 * @param request 授权参数
	 * @return OAuth2User
	 */
	OAuth2User user(OAuth2Request request);

	/**
	 * 创建令牌
	 *
	 * @param user    用户信息
	 * @param request 授权参数
	 * @return OAuth2Token
	 */
	OAuth2Token token(OAuth2User user, OAuth2Request request);

	/**
	 * 自定义增强
	 *
	 * @param enhancer enhancer
	 */
	void enhancer(TokenGranterEnhancer enhancer);

}
