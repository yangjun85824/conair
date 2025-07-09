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

import org.springblade.core.tool.api.R;
import org.springblade.system.pojo.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author Chill
 */
@Component
public class ISysClientFallback implements ISysClient {

	@Override
	public R<Menu> getMenu(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Dept> getDept(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getDeptIds(String tenantId, String deptNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getDeptIdsByFuzzy(String tenantId, String deptNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getDeptName(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<String>> getDeptNames(String deptIds) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dept>> getDeptChild(Long deptId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Post> getPost(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getPostIds(String tenantId, String postNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getPostIdsByFuzzy(String tenantId, String postNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getPostName(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<String>> getPostNames(String postIds) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Role> getRole(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getRoleIds(String tenantId, String roleNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getRoleName(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getRoleAlias(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<String>> getRoleNames(String roleIds) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<String>> getRoleAliases(String roleIds) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Tenant> getTenant(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Tenant> getTenant(String tenantId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<TenantPackage> getTenantPackage(String tenantId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Param> getParam(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getParamValue(String paramKey) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Region> getRegion(String code) {
		return R.fail("获取数据失败");
	}


}
