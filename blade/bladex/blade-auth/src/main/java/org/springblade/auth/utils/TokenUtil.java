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
package org.springblade.auth.utils;

import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.impl.OAuth2UserDetail;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.User;
import org.springblade.system.pojo.entity.UserInfo;

/**
 * 认证工具类
 *
 * @author Chill
 */
public class TokenUtil {

	/**
	 * 系统用户转换为OAuth2标准用户
	 *
	 * @param userInfo 用户信息
	 * @param request  请求信息
	 * @return OAuth2User
	 */
	public static OAuth2User convertUser(UserInfo userInfo, OAuth2Request request) {
		// 为空则返回null
		if (userInfo == null || userInfo.getUser() == null) {
			return null;
		}
		User user = userInfo.getUser();
		String userDept = request.getUserDept();
		String userRole = request.getUserRole();
		// 多部门情况下指定单部门
		if (Func.isNotEmpty(userDept) && user.getDeptId().contains(userDept)) {
			user.setDeptId(userDept);
		}
		// 多角色情况下指定单角色
		if (Func.isNotEmpty(userRole) && user.getRoleId().contains(userRole)) {
			user.setRoleId(userRole);
		}
		// 构建oauth2所需用户信息
		OAuth2UserDetail userDetail = new OAuth2UserDetail();
		userDetail.setUserId(String.valueOf(user.getId()));
		userDetail.setOauthId(userInfo.getOauthId());
		userDetail.setTenantId(user.getTenantId());
		userDetail.setName(user.getName());
		userDetail.setRealName(user.getRealName());
		userDetail.setAccount(user.getAccount());
		userDetail.setPassword(user.getPassword());
		userDetail.setDeptId(user.getDeptId());
		userDetail.setPostId(user.getPostId());
		userDetail.setRoleId(user.getRoleId());
		userDetail.setRoleName(Func.join(userInfo.getRoles()));
		userDetail.setAvatar(user.getAvatar());
		userDetail.setAuthorities(userInfo.getRoles());
		userDetail.setDetail(userInfo.getDetail());
		return userDetail;
	}

}
