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
package org.springblade.core.secure.handler;

import org.springblade.core.secure.props.AuthSecure;
import org.springblade.core.secure.props.BasicSecure;
import org.springblade.core.secure.props.BladeSecureProperties;
import org.springblade.core.secure.props.SignSecure;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * secure 拦截器集合
 *
 * @author Chill
 */
public interface ISecureHandler {

	/**
	 * token拦截器
	 *
	 * @param secureProperties 授权配置
	 * @return tokenInterceptor
	 */
	HandlerInterceptor tokenInterceptor(BladeSecureProperties secureProperties);

	/**
	 * auth拦截器
	 *
	 * @param authSecures 授权集合
	 * @return HandlerInterceptor
	 */
	HandlerInterceptor authInterceptor(BladeSecureProperties secureProperties, List<AuthSecure> authSecures);

	/**
	 * basic拦截器
	 *
	 * @param basicSecures 基础认证集合
	 * @return HandlerInterceptor
	 */
	HandlerInterceptor basicInterceptor(List<BasicSecure> basicSecures);

	/**
	 * sign拦截器
	 *
	 * @param signSecures 签名认证集合
	 * @return HandlerInterceptor
	 */
	HandlerInterceptor signInterceptor(List<SignSecure> signSecures);

	/**
	 * client拦截器
	 *
	 * @param clientId 客户端id
	 * @return clientInterceptor
	 */
	HandlerInterceptor clientInterceptor(String clientId);

}
