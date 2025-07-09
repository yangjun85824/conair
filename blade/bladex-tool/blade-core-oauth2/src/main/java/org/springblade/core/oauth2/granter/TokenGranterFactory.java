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

import lombok.AllArgsConstructor;
import org.springblade.core.oauth2.exception.GranterInvalidException;
import org.springblade.core.oauth2.props.OAuth2Properties;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springblade.core.oauth2.constant.OAuth2GranterConstant.*;
import static org.springblade.core.oauth2.exception.OAuth2ErrorMessage.INVALID_GRANT_TYPE;

/**
 * TokenGranterFactory
 *
 * @author BladeX
 */
@AllArgsConstructor
public class TokenGranterFactory {

	/**
	 * TokenGranter 集合
	 */
	private final List<TokenGranter> tokenGranters;

	/**
	 * TokenGranterEnhancer 集合
	 */
	private final List<TokenGranterEnhancer> tokenGranterEnhancers;

	/**
	 * OAuth2 属性配置
	 */
	private final OAuth2Properties properties;

	/**
	 * TokenGranter 缓存池，使用 ConcurrentHashMap 保证线程安全。
	 */
	private static final Map<String, TokenGranter> GRANTER_POOL = new ConcurrentHashMap<>();


	/**
	 * 根据授权模式获取 TokenGranter，如果缓存中不存在，则尝试创建。
	 *
	 * @param grantType 授权模式
	 * @return TokenGranter 实例
	 * @throws IllegalArgumentException 如果请求的授权类型不支持
	 */
	public TokenGranter create(String grantType) {
		// 使用 computeIfAbsent 实现延迟加载
		return GRANTER_POOL.computeIfAbsent(grantType, this::initializeTokenGranter);
	}

	/**
	 * 尝试根据授权类型初始化 TokenGranter。
	 *
	 * @param grantType 授权模式
	 * @return 初始化的 TokenGranter
	 * @throws GranterInvalidException 如果无法识别授权类型
	 */
	private TokenGranter initializeTokenGranter(String grantType) {
		// 根据授权类型查找对应的 TokenGranter 并应用第一个找到的增强类
		return tokenGranters.stream()
			.filter(granter -> granter.type().equals(grantType) && isGranterEnabled(granter))
			.peek(granter -> tokenGranterEnhancers.stream()
				.filter(enhancer -> enhancer.type().equals(grantType))
				.findFirst()
				.ifPresent(granter::enhancer))
			.findFirst()
			.orElseThrow(() -> new GranterInvalidException(String.format(INVALID_GRANT_TYPE, grantType)));
	}

	/**
	 * 判断 TokenGranter 是否启用。
	 *
	 * @param granter TokenGranter 实例
	 * @return 是否启用
	 */
	private boolean isGranterEnabled(TokenGranter granter) {
		return switch (granter.type()) {
			case AUTHORIZATION_CODE -> properties.getGranter().getAuthorizationCode();
			case PASSWORD -> properties.getGranter().getPassword();
			case REFRESH_TOKEN -> properties.getGranter().getRefreshToken();
			case CLIENT_CREDENTIALS -> properties.getGranter().getClientCredentials();
			case IMPLICIT -> properties.getGranter().getImplicit();
			case CAPTCHA -> properties.getGranter().getCaptcha();
			case SMS_CODE -> properties.getGranter().getSmsCode();
			case WECHAT_APPLET -> properties.getGranter().getWechatApplet();
			case SOCIAL -> properties.getGranter().getSocial();
			case REGISTER -> properties.getGranter().getRegister();
			default -> true;
		};
	}
}
