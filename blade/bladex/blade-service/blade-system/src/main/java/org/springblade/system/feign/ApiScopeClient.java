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

import lombok.RequiredArgsConstructor;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.utils.Func;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springblade.core.secure.constant.PermissionConstant.permissionAllStatement;
import static org.springblade.core.secure.constant.PermissionConstant.permissionStatement;

/**
 * 接口权限Feign实现类
 *
 * @author Chill
 */
@NonDS
@Hidden
@RestController
@RequiredArgsConstructor
public class ApiScopeClient implements IApiScopeClient {

	private final JdbcTemplate jdbcTemplate;

	@Override
	@GetMapping(PERMISSION_PATH)
	public List<String> permissionPath(String roleId) {
		List<Long> roleIds = Func.toLongList(roleId);
		return jdbcTemplate.queryForList(permissionAllStatement(roleIds.size()), roleIds.toArray(), String.class);
	}

	@Override
	@GetMapping(PERMISSION_CODE)
	public List<String> permissionCode(String permission, String roleId) {
		List<Object> args = new ArrayList<>(Collections.singletonList(permission));
		List<Long> roleIds = Func.toLongList(roleId);
		args.addAll(roleIds);
		return jdbcTemplate.queryForList(permissionStatement(roleIds.size()), args.toArray(), String.class);
	}

}
