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
package org.springblade.core.oauth2.service;

import org.springblade.core.tool.support.Kv;

import java.io.Serializable;
import java.util.List;

/**
 * 用户基础信息
 *
 * @author BladeX
 */
public interface OAuth2User extends Serializable {

	/**
	 * 获取用户ID.
	 *
	 * @return Long
	 */
	String getUserId();

	/**
	 * 获取租户ID.
	 *
	 * @return String
	 */
	String getTenantId();

	/**
	 * 获取第三方认证ID.
	 *
	 * @return String
	 */
	String getOauthId();

	/**
	 * 获取昵称.
	 *
	 * @return String
	 */
	String getName();

	/**
	 * 获取真名.
	 *
	 * @return String
	 */
	String getRealName();

	/**
	 * 获取账号.
	 *
	 * @return String
	 */
	String getAccount();

	/**
	 * 获取密码.
	 *
	 * @return String
	 */
	String getPassword();

	/**
	 * 获取手机.
	 *
	 * @return String
	 */
	String getPhone();

	/**
	 * 获取邮箱.
	 *
	 * @return String
	 */
	String getEmail();

	/**
	 * 获取部门ID.
	 *
	 * @return String
	 */
	String getDeptId();

	/**
	 * 获取岗位ID.
	 *
	 * @return String
	 */
	String getPostId();

	/**
	 * 获取角色ID.
	 *
	 * @return String
	 */
	String getRoleId();

	/**
	 * 获取角色名.
	 *
	 * @return String
	 */
	String getRoleName();

	/**
	 * 获取头像.
	 *
	 * @return String
	 */
	String getAvatar();

	/**
	 * 获取权限集合.
	 *
	 * @return List<String>
	 */
	List<String> getPermissions();

	/**
	 * 获取角色集合.
	 *
	 * @return List<String>
	 */
	List<String> getAuthorities();

	/**
	 * 获取客户端信息.
	 *
	 * @return OAuth2Client
	 */
	OAuth2Client getClient();

	/**
	 * 设置客户端信息.
	 */
	void setClient(OAuth2Client client);

	/**
	 * 获取用户详情.
	 *
	 * @return Kv
	 */
	Kv getDetail();
}
