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
package org.springblade.core.jwt.props;

import io.jsonwebtoken.JwtException;
import lombok.Data;
import org.springblade.core.jwt.constant.JwtConstant;
import org.springblade.core.jwt.enums.SingleLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置
 *
 * @author Chill
 */
@Data
@ConfigurationProperties("blade.token")
public class JwtProperties {

	/**
	 * token是否有状态
	 */
	private Boolean state = Boolean.FALSE;

	/**
	 * 是否只可同时在线一人
	 */
	private Boolean single = Boolean.FALSE;

	/**
	 * 单人模式级别(ALL 全部平台只能有一个，CLIENT 不同客户端只能有一个)
	 */
	private SingleLevel singleLevel = SingleLevel.ALL;

	/**
	 * token签名
	 */
	private String signKey = "";

	/**
	 * token密钥
	 */
	private String cryptoKey = "";

	/**
	 * 获取签名规则
	 */
	public String getSignKey() {
		if (this.signKey.length() < JwtConstant.SECRET_KEY_LENGTH) {
			throw new JwtException("请配置 blade.token.sign-key 的值, 长度32位以上");
		}
		return this.signKey;
	}

}
