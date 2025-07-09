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
package org.springblade.core.secure.auth;

import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.handler.IPermissionHandler;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

/**
 * 权限判断
 *
 * @author Chill
 */
public class AuthFun {

	/**
	 * 权限校验处理器
	 */
	private static IPermissionHandler permissionHandler;

	private static IPermissionHandler getPermissionHandler() {
		if (permissionHandler == null) {
			permissionHandler = SpringUtil.getBean(IPermissionHandler.class);
		}
		return permissionHandler;
	}

	/**
	 * 判断角色是否具有接口权限
	 *
	 * @return {boolean}
	 */
	public boolean permissionAll() {
		return getPermissionHandler().permissionAll();
	}

	/**
	 * 判断角色是否具有接口权限
	 *
	 * @param permission 权限编号
	 * @return {boolean}
	 */
	public boolean hasPermission(String permission) {
		return getPermissionHandler().hasPermission(permission);
	}

	/**
	 * 放行所有请求
	 *
	 * @return {boolean}
	 */
	public boolean permitAll() {
		return true;
	}

	/**
	 * 只有超管角色才可访问
	 *
	 * @return {boolean}
	 */
	public boolean denyAll() {
		return hasRole(RoleConstant.ADMIN);
	}

	/**
	 * 是否已授权
	 *
	 * @return {boolean}
	 */
	public boolean hasAuth() {
		return Func.isNotEmpty(AuthUtil.getUser());
	}

	/**
	 * 是否有时间授权
	 *
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return {boolean}
	 */
	public boolean hasTimeAuth(Integer start, Integer end) {
		Integer hour = DateUtil.hour();
		return hour >= start && hour <= end;
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param role 单角色
	 * @return {boolean}
	 */
	public boolean hasRole(String role) {
		return hasAnyRole(role);
	}

	/**
	 * 判断是否具有所有角色权限
	 *
	 * @param role 角色集合
	 * @return {boolean}
	 */
	public boolean hasAllRole(String... role) {
		for (String r : role) {
			if (!hasRole(r)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否有该角色权限
	 *
	 * @param role 角色集合
	 * @return {boolean}
	 */
	public boolean hasAnyRole(String... role) {
		BladeUser user = AuthUtil.getUser();
		if (user == null) {
			return false;
		}
		String userRole = user.getRoleName();
		if (StringUtil.isBlank(userRole)) {
			return false;
		}
		String[] roles = Func.toStrArray(userRole);
		for (String r : role) {
			if (CollectionUtil.contains(roles, r)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断请求是否为加密token
	 *
	 * @return {boolean}
	 */
	public boolean hasCrypto() {
		HttpServletRequest request = WebUtil.getRequest();
		String auth = Objects.requireNonNull(request).getHeader(TokenConstant.AUTH_HEADER);
		return JwtUtil.isCrypto(
			StringUtil.isNotBlank(auth) ? auth : request.getParameter(TokenConstant.AUTH_HEADER)
		);
	}

	/**
	 * 判断令牌是否符合严格模式
	 *
	 * @return {boolean}
	 */
	public boolean hasStrictToken() {
		BladeUser currentUser = AuthUtil.getUser();
		return AuthUtil.userIncomplete(currentUser);
	}

	/**
	 * 判断是否包含安全请求头
	 *
	 * @return {boolean}
	 */
	public boolean hasStrictHeader() {
		return !AuthUtil.secureHeaderIncomplete();
	}

	/**
	 * 判断是否有该请求头
	 *
	 * @param header 请求头
	 * @return {boolean}
	 */
	public boolean hasHeader(String header) {
		HttpServletRequest request = WebUtil.getRequest();
		String value = Objects.requireNonNull(request).getHeader(header);
		return StringUtil.isNotBlank(value);
	}

	/**
	 * 判断是否有该请求头
	 *
	 * @param header 请求头
	 * @param key    请求值
	 * @return {boolean}
	 */
	public boolean hasHeader(String header, String key) {
		HttpServletRequest request = WebUtil.getRequest();
		String value = Objects.requireNonNull(request).getHeader(header);
		return StringUtil.equals(value, key);
	}

}
