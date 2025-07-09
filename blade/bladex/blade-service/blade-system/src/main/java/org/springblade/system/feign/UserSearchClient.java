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

import lombok.AllArgsConstructor;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.User;
import org.springblade.system.service.IUserSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.List;

/**
 * 用户查询服务Feign实现类
 *
 * @author Chill
 */
@NonDS
@Hidden
@RestController
@AllArgsConstructor
public class UserSearchClient implements IUserSearchClient {

	private final IUserSearchService service;

	@Override
	@GetMapping(LIST_BY_USER)
	public R<List<User>> listByUser(String userId) {
		return R.data(service.listByUser(Func.toLongList(userId)));
	}

	@Override
	@GetMapping(LIST_BY_DEPT)
	public R<List<User>> listByDept(String deptId) {
		return R.data(service.listByDept(Func.toLongList(deptId)));
	}

	@Override
	@GetMapping(LIST_BY_POST)
	public R<List<User>> listByPost(String postId) {
		return R.data(service.listByPost(Func.toLongList(postId)));
	}

	@Override
	@GetMapping(LIST_BY_ROLE)
	public R<List<User>> listByRole(String roleId) {
		return R.data(service.listByRole(Func.toLongList(roleId)));
	}
}
