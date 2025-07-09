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
package org.springblade.gateway.props;

import lombok.Data;
import org.springblade.gateway.provider.AuthSecure;
import org.springblade.gateway.provider.BasicSecure;
import org.springblade.gateway.provider.SignSecure;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 *
 * @author Chill
 */
@Data
@RefreshScope
@ConfigurationProperties("blade.secure")
public class AuthProperties {

	/**
	 * 放行API集合
	 */
	private final List<String> skipUrl = new ArrayList<>();

	/**
	 * 自定义授权配置
	 */
	private final List<AuthSecure> auth = new ArrayList<>();

	/**
	 * 基础认证配置
	 */
	private final List<BasicSecure> basic = new ArrayList<>();

	/**
	 * 签名认证配置
	 */
	private final List<SignSecure> sign = new ArrayList<>();

}
