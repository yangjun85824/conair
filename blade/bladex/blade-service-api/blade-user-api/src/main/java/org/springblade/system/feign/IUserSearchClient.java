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
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User Search Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
)
public interface IUserSearchClient {

	String API_PREFIX = "/feign/client/user-search";
	String LIST_BY_USER = API_PREFIX + "/list-by-user";
	String LIST_BY_DEPT = API_PREFIX + "/list-by-dept";
	String LIST_BY_POST = API_PREFIX + "/list-by-post";
	String LIST_BY_ROLE = API_PREFIX + "/list-by-role";

	/**
	 * 根据用户ID查询用户列表
	 *
	 * @param userId 用户ID
	 * @return 用户列表
	 */
	@GetMapping(LIST_BY_USER)
	R<List<User>> listByUser(@RequestParam("userId") String userId);

	/**
	 * 根据部门ID查询用户列表
	 *
	 * @param deptId 部门ID
	 * @return 用户列表
	 */
	@GetMapping(LIST_BY_DEPT)
	R<List<User>> listByDept(@RequestParam("deptId") String deptId);

	/**
	 * 根据岗位ID查询用户列表
	 *
	 * @param postId 岗位ID
	 * @return 用户列表
	 */
	@GetMapping(LIST_BY_POST)
	R<List<User>> listByPost(@RequestParam("postId") String postId);

	/**
	 * 根据角色ID查询用户列表
	 *
	 * @param roleId 角色ID
	 * @return 用户列表
	 */
	@GetMapping(LIST_BY_ROLE)
	R<List<User>> listByRole(@RequestParam("roleId") String roleId);

}
