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

import lombok.Data;
import lombok.experimental.Accessors;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.StringUtil;

import java.io.Serial;
import java.io.Serializable;

/**
 * OAuth2Token
 *
 * @author BladeX
 */
@Data
@Accessors(chain = true)
public class OAuth2Token implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 实例化
	 */
	public static OAuth2Token create() {
		return new OAuth2Token();
	}

	/**
	 * 令牌值
	 */
	private String accessToken;

	/**
	 * 刷新令牌值
	 */
	private String refreshToken;

	/**
	 * 令牌过期秒数
	 */
	private int accessTokenExpire;

	/**
	 * 刷新令牌过期秒数
	 */
	private int refreshTokenExpire;

	/**
	 * 令牌参数
	 */
	private Kv args = Kv.create();


	/**
	 * 是否包含令牌
	 *
	 * @return Boolean
	 */
	public Boolean hasAccessToken() {
		return StringUtil.isNotBlank(accessToken);
	}

	/**
	 * 是否包含刷新令牌
	 *
	 * @return Boolean
	 */
	public Boolean hasRefreshToken() {
		return StringUtil.isNotBlank(refreshToken);
	}

}
