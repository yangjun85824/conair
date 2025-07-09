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


import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.system.pojo.entity.User;
import org.springblade.system.pojo.entity.UserInfo;
import org.springblade.system.pojo.entity.UserOauth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
)
public interface IUserClient {

	String API_PREFIX = "/feign/client/user";
	String USER_INFO_BY_ID = API_PREFIX + "/info/info-by-id";
	String USER_INFO_BY_ID_TYPE = API_PREFIX + "/info/info-by-id-type";
	String USER_INFO_BY_TENANT_ID_ACCOUNT = API_PREFIX + "/info/info-by-tenant-id-account";
	String USER_INFO_BY_TENANT_ID_ACCOUNT_TYPE = API_PREFIX + "/info/info-by-tenant-id-account-type";
	String USER_INFO_BY_TENANT_ID_PHONE_TYPE = API_PREFIX + "/info/info-by-tenant-id-phone-type";
	String USER_BY_ID = API_PREFIX + "/user-by-id";
	String USER_BY_ACCOUNT = API_PREFIX + "/user-by-account";
	String USER_AUTH_INFO = API_PREFIX + "/user-auth-info";
	String SAVE_USER = API_PREFIX + "/save-user";
	String REGISTER_USER = API_PREFIX + "/register-user";
	String REMOVE_USER = API_PREFIX + "/remove-user";

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户id
	 * @return
	 */
	@GetMapping(USER_BY_ID)
	R<User> userInfoById(@RequestParam("userId") Long userId);


	/**
	 * 根据账号获取用户信息
	 *
	 * @param tenantId 租户id
	 * @param account  账号
	 * @return
	 */
	@GetMapping(USER_BY_ACCOUNT)
	R<User> userByAccount(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	@GetMapping(USER_INFO_BY_ID)
	R<UserInfo> userInfo(@RequestParam("userId") Long userId);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @param userType
	 * @return
	 */
	@GetMapping(USER_INFO_BY_ID_TYPE)
	R<UserInfo> userInfo(@RequestParam("userId") Long userId, @RequestParam("userType") String userType);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param account  账号
	 * @return
	 */
	@GetMapping(USER_INFO_BY_TENANT_ID_ACCOUNT)
	R<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param account  账号
	 * @param userType 用户平台
	 * @return
	 */
	@GetMapping(USER_INFO_BY_TENANT_ID_ACCOUNT_TYPE)
	R<UserInfo> userInfo(@RequestParam("tenantId") String tenantId, @RequestParam("account") String account, @RequestParam("userType") String userType);

	/**
	 * 获取用户信息
	 *
	 * @param tenantId 租户ID
	 * @param phone    手机号
	 * @param userType 用户平台
	 * @return
	 */
	@GetMapping(USER_INFO_BY_TENANT_ID_PHONE_TYPE)
	R<UserInfo> userInfoByPhone(@RequestParam("tenantId") String tenantId, @RequestParam("phone") String phone, @RequestParam("userType") String userType);

	/**
	 * 获取第三方平台信息
	 *
	 * @param userOauth 第三方授权用户信息
	 * @return UserInfo
	 */
	@PostMapping(USER_AUTH_INFO)
	R<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth);

	/**
	 * 新建用户
	 *
	 * @param user 用户实体
	 * @return
	 */
	@PostMapping(SAVE_USER)
	R<Boolean> saveUser(@RequestBody User user);

	/**
	 * 注册用户
	 *
	 * @param user 用户实体
	 * @return
	 */
	@PostMapping(REGISTER_USER)
	R<String> registerUser(@RequestBody User user);

	/**
	 * 删除用户
	 *
	 * @param tenantIds 租户id集合
	 * @return
	 */
	@PostMapping(REMOVE_USER)
	R<Boolean> removeUser(@RequestParam("tenantIds") String tenantIds);

}
