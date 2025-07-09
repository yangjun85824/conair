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
package org.springblade.auth.handler;

import org.springblade.core.oauth2.handler.OAuth2PasswordHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;

/**
 * BladePasswordHandler
 *
 * @author BladeX
 */
public class BladePasswordHandler extends OAuth2PasswordHandler {

	public BladePasswordHandler(OAuth2Properties properties) {
		super(properties);
	}

	/**
	 * 判断密码是否匹配
	 *
	 * @param rawPassword     请求时提交的原密码
	 * @param encodedPassword 数据库加密后的密码
	 * @return boolean
	 */
	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return super.matches(rawPassword, encodedPassword);
	}

	/**
	 * 加密密码规则
	 *
	 * @param rawPassword 密码
	 * @return 加密后的密码
	 */
	@Override
	public String encode(String rawPassword) {
		return super.encode(rawPassword);
	}
}
