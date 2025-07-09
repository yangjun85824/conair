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

import java.io.Serializable;

/**
 * 多终端详情接口
 *
 * @author BladeX
 */
public interface OAuth2Client extends Serializable {

	/**
	 * 获取客户端ID.
	 *
	 * @return String
	 */
	String getClientId();

	/**
	 * 获取客户端密钥.
	 *
	 * @return String
	 */
	String getClientSecret();

	/**
	 * 获取资源集合.
	 *
	 * @return String
	 */
	String getResourceIds();

	/**
	 * 获取授权范围.
	 *
	 * @return String
	 */
	String getScope();

	/**
	 * 获取授权类型.
	 *
	 * @return String
	 */
	String getAuthorizedGrantTypes();

	/**
	 * 获取回调地址.
	 *
	 * @return String
	 */
	String getWebServerRedirectUri();

	/**
	 * 获取权限.
	 *
	 * @return String
	 */
	String getAuthorities();

	/**
	 * 获取访问令牌有效期.
	 *
	 * @return Integer
	 */
	Integer getAccessTokenValidity();

	/**
	 * 获取刷新令牌有效期.
	 *
	 * @return Integer
	 */
	Integer getRefreshTokenValidity();

	/**
	 * 获取附加信息.
	 *
	 * @return String
	 */
	String getAdditionalInformation();

	/**
	 * 获取自动授权.
	 *
	 * @return String
	 */
	String getAutoapprove();
}
