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
package org.springblade.system.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.User;
import org.springblade.system.pojo.entity.UserInfo;
import org.springblade.system.pojo.entity.UserOauth;
import org.springblade.system.pojo.enums.UserType;
import org.springblade.system.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

/**
 * 用户服务Feign实现类
 *
 * @author Chill
 */
@NonDS
@Hidden
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

	private final IUserService service;

	@Override
	@GetMapping(USER_BY_ID)
	public R<User> userInfoById(Long userId) {
		return R.data(service.getById(userId));
	}

	@Override
	@GetMapping(USER_BY_ACCOUNT)
	public R<User> userByAccount(String tenantId, String account) {
		return R.data(service.userByAccount(tenantId, account));
	}

	@Override
	@GetMapping(USER_INFO_BY_ID)
	public R<UserInfo> userInfo(Long userId) {
		return R.data(service.userInfo(userId));
	}

	@Override
	@GetMapping(USER_INFO_BY_ID_TYPE)
	public R<UserInfo> userInfo(Long userId, String userType) {
		return R.data(service.userInfo(userId, UserType.of(userType)));
	}

	@Override
	@GetMapping(USER_INFO_BY_TENANT_ID_ACCOUNT)
	public R<UserInfo> userInfo(String tenantId, String account) {
		return R.data(service.userInfo(tenantId, account));
	}

	@Override
	@GetMapping(USER_INFO_BY_TENANT_ID_ACCOUNT_TYPE)
	public R<UserInfo> userInfo(String tenantId, String account, String userType) {
		return R.data(service.userInfo(tenantId, account, UserType.of(userType)));
	}

	@Override
	@GetMapping(USER_INFO_BY_TENANT_ID_PHONE_TYPE)
	public R<UserInfo> userInfoByPhone(String tenantId, String phone, String userType) {
		return R.data(service.userInfoByPhone(tenantId, phone, UserType.of(userType)));
	}

	@Override
	@PostMapping(USER_AUTH_INFO)
	public R<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth) {
		return R.data(service.userInfo(userOauth));
	}

	@Override
	@PostMapping(SAVE_USER)
	public R<Boolean> saveUser(@RequestBody User user) {
		return R.data(service.submit(user));
	}

	@Override
	@PostMapping(REGISTER_USER)
	public R<String> registerUser(User user) {
		boolean temp = service.registerUser(user);
		return temp ? R.data(String.valueOf(user.getId())) : R.fail("注册失败");
	}

	@Override
	@PostMapping(REMOVE_USER)
	public R<Boolean> removeUser(String tenantIds) {
		return R.data(service.remove(Wrappers.<User>query().lambda().in(User::getTenantId, Func.toStrList(tenantIds))));
	}

}
