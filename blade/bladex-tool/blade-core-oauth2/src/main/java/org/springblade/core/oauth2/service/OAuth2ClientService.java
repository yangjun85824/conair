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
 * 多终端注册接口
 *
 * @author BladeX
 */
public interface OAuth2ClientService {

	/**
	 * 根据clientId获取Client详情
	 *
	 * @param clientId 客户端id
	 * @return 客户端信息
	 */
	OAuth2Client loadByClientId(String clientId);

	/**
	 * 根据clientId获取Client详情
	 *
	 * @param clientId 客户端id
	 * @param request  授权参数
	 * @return 客户端信息
	 */
	OAuth2Client loadByClientId(String clientId, OAuth2Request request);

	/**
	 * 验证Client信息
	 *
	 * @param client       client信息
	 * @param clientId     客户端id
	 * @param clientSecret 客户端密钥
	 * @return boolean
	 */
	boolean validateClient(OAuth2Client client, String clientId, String clientSecret);

	/**
	 * 验证Client信息
	 *
	 * @param client      client信息
	 * @param redirectUri 回调地址
	 * @return boolean
	 */
	boolean validateRedirectUri(OAuth2Client client, String redirectUri);

	/**
	 * 验证授权类型
	 *
	 * @param client    client信息
	 * @param grantType 授权类型
	 * @return boolean
	 */
	boolean validateGranter(OAuth2Client client, String grantType);

}
