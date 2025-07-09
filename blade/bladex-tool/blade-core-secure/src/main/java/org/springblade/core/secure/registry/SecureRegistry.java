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
package org.springblade.core.secure.registry;

import lombok.Data;
import lombok.Getter;
import org.springblade.core.secure.props.AuthSecure;
import org.springblade.core.secure.props.BasicSecure;
import org.springblade.core.secure.props.SignSecure;
import org.springblade.core.secure.provider.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 安全框架统一配置
 *
 * @author Chill
 */
@Data
public class SecureRegistry {

	/**
	 * 是否开启鉴权
	 */
	private boolean enabled = true;

	/**
	 * 开启令牌严格模式
	 */
	private boolean strictToken = true;

	/**
	 * 开启请求头严格模式
	 */
	private boolean strictHeader = true;

	/**
	 * 是否开启授权
	 */
	private boolean authEnabled = true;

	/**
	 * 是否开启基础认证
	 */
	private boolean basicEnabled = true;

	/**
	 * 是否开启签名认证
	 */
	private boolean signEnabled = true;

	/**
	 * 是否开启客户端认证
	 */
	private boolean clientEnabled = true;

	/**
	 * 默认放行规则
	 */
	private final List<String> defaultExcludePatterns = new ArrayList<>();

	/**
	 * 自定义放行规则
	 */
	private final List<String> excludePatterns = new ArrayList<>();

	/**
	 * 自定义授权集合
	 */
	@Getter
	private final List<AuthSecure> authSecures = new ArrayList<>();

	/**
	 * 基础认证集合
	 */
	@Getter
	private final List<BasicSecure> basicSecures = new ArrayList<>();

	/**
	 * 签名认证集合
	 */
	@Getter
	private final List<SignSecure> signSecures = new ArrayList<>();

	public SecureRegistry() {
		this.defaultExcludePatterns.add("/actuator/health/**");
		this.defaultExcludePatterns.add("/v3/api-docs/**");
		this.defaultExcludePatterns.add("/swagger-ui/**");
		this.defaultExcludePatterns.add("/oauth/**");
		this.defaultExcludePatterns.add("/feign/client/**");
		this.defaultExcludePatterns.add("/process/resource-view");
		this.defaultExcludePatterns.add("/process/diagram-view");
		this.defaultExcludePatterns.add("/manager/check-upload");
		this.defaultExcludePatterns.add("/tenant/info");
		this.defaultExcludePatterns.add("/static/**");
		this.defaultExcludePatterns.add("/assets/**");
		this.defaultExcludePatterns.add("/error");
		this.defaultExcludePatterns.add("/favicon.ico");
	}

	/**
	 * 设置单个放行api
	 */
	public SecureRegistry excludePathPattern(String pattern) {
		this.excludePatterns.add(pattern);
		return this;
	}

	/**
	 * 设置放行api集合
	 */
	public SecureRegistry excludePathPatterns(String... patterns) {
		this.excludePatterns.addAll(Arrays.asList(patterns));
		return this;
	}

	/**
	 * 设置放行api集合
	 */
	public void excludePathPatterns(List<String> patterns) {
		this.excludePatterns.addAll(patterns);
	}

	/**
	 * 设置单个自定义授权
	 */
	public SecureRegistry addAuthPattern(HttpMethod method, String pattern, String expression) {
		this.authSecures.add(new AuthSecure(method, pattern, expression));
		return this;
	}

	/**
	 * 设置自定义授权集合
	 */
	public SecureRegistry addAuthPatterns(List<AuthSecure> authSecures) {
		this.authSecures.addAll(authSecures);
		return this;
	}

	/**
	 * 设置基础认证
	 */
	public SecureRegistry addBasicPattern(HttpMethod method, String pattern, String username, String password) {
		this.basicSecures.add(new BasicSecure(method, pattern, username, password));
		return this;
	}

	/**
	 * 设置基础认证集合
	 */
	public SecureRegistry addBasicPatterns(List<BasicSecure> basicSecures) {
		this.basicSecures.addAll(basicSecures);
		return this;
	}

	/**
	 * 设置签名认证
	 */
	public SecureRegistry addSignPattern(HttpMethod method, String pattern, String crypto) {
		this.signSecures.add(new SignSecure(method, pattern, crypto));
		return this;
	}

	/**
	 * 设置签名认证集合
	 */
	public SecureRegistry addSignPatterns(List<SignSecure> signSecures) {
		this.signSecures.addAll(signSecures);
		return this;
	}

	/**
	 * 设置是否开启令牌严格模式
	 */
	public SecureRegistry strictToken(boolean strictToken) {
		this.strictToken = strictToken;
		return this;
	}

	/**
	 * 设置是否开启请求头严格模式
	 */
	public SecureRegistry strictHeader(boolean strictHeader) {
		this.strictHeader = strictHeader;
		return this;
	}

}
