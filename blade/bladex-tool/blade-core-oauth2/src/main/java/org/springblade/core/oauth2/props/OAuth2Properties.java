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
package org.springblade.core.oauth2.props;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OAuth2Property
 *
 * @author BladeX
 */
@Getter
@Setter
@ConfigurationProperties(OAuth2Properties.PREFIX)
public class OAuth2Properties {
	/**
	 * 配置前缀
	 */
	public static final String PREFIX = "blade.oauth2";

	/**
	 * 是否开启OAuth2
	 */
	private Boolean enabled = true;

	/**
	 * code缓存时间
	 */
	private long codeTimeout = 10 * 60L;

	/**
	 * sm2公钥
	 */
	private String publicKey;

	/**
	 * sm2私钥
	 */
	private String privateKey;

	/**
	 * 授权模式
	 */
	private Granter granter = new Granter();

	@Data
	@NoArgsConstructor
	public static class Granter {
		/**
		 * 是否开启授权码模式
		 */
		private Boolean authorizationCode = true;
		/**
		 * 是否开启验证码模式
		 */
		private Boolean captcha = true;
		/**
		 * 是否开启密码模式
		 */
		private Boolean password = true;
		/**
		 * 是否开启刷新token模式
		 */
		private Boolean refreshToken = true;
		/**
		 * 是否开启客户端模式
		 */
		private Boolean clientCredentials = true;
		/**
		 * 是否开启简化模式
		 */
		private Boolean implicit = true;
		/**
		 * 是否手机验证码模式
		 */
		private Boolean smsCode = true;
		/**
		 * 是否开启微信小程序模式
		 */
		private Boolean wechatApplet = true;
		/**
		 * 是否开启开放平台模式
		 */
		private Boolean social = true;
		/**
		 * 是否开启注册模式
		 */
		private Boolean register = true;
	}

}
