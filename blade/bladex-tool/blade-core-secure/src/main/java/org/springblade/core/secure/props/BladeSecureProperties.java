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
package org.springblade.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端校验配置
 *
 * @author Chill
 */
@Data
@ConfigurationProperties("blade.secure")
public class BladeSecureProperties {

	/**
	 * 开启鉴权规则
	 */
	private Boolean enabled = false;

	/**
	 * 开启令牌严格模式
	 */
	private Boolean strictToken = true;

	/**
	 * 开启请求头严格模式
	 */
	private Boolean strictHeader = true;

	/**
	 * 鉴权放行请求
	 */
	private final List<String> skipUrl = new ArrayList<>();

	/**
	 * 开启授权规则
	 */
	private Boolean authEnabled = true;

	/**
	 * 授权配置
	 */
	private final List<AuthSecure> auth = new ArrayList<>();

	/**
	 * 开启基础认证规则
	 */
	private Boolean basicEnabled = true;

	/**
	 * 基础认证配置
	 */
	private final List<BasicSecure> basic = new ArrayList<>();

	/**
	 * 开启签名认证规则
	 */
	private Boolean signEnabled = true;

	/**
	 * 签名认证配置
	 */
	private final List<SignSecure> sign = new ArrayList<>();

	/**
	 * 开启客户端规则
	 */
	private Boolean clientEnabled = true;

	/**
	 * 客户端配置
	 */
	private final List<ClientSecure> client = new ArrayList<>();

}
