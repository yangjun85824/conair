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
package org.springblade.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.cache.DictCache;
import org.springblade.system.cache.UserCache;
import org.springblade.system.pojo.entity.Dept;
import org.springblade.system.pojo.entity.User;
import org.springblade.system.pojo.enums.DictEnum;
import org.springblade.system.service.IDeptService;
import org.springblade.system.pojo.vo.DeptVO;
import org.springblade.system.wrapper.DeptWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Tag(name = "部门", description = "部门")
public class DeptController extends BladeController {

	private final IDeptService deptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入dept")
	public R<DeptVO> detail(Dept dept) {
		Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
		return R.data(DeptWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "deptName", description = "部门名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "fullName", description = "部门全称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "列表", description = "传入dept")
	public R<List<DeptVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> dept, BladeUser bladeUser) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		List<Dept> list = deptService.list((!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(DeptWrapper.build().listNodeVO(list));
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-list")
	@Parameters({
		@Parameter(name = "deptName", description = "部门名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "fullName", description = "部门全称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "懒加载列表", description = "传入dept")
	public R<List<DeptVO>> lazyList(@Parameter(hidden = true) @RequestParam Map<String, Object> dept, Long parentId, BladeUser bladeUser) {
		List<DeptVO> list = deptService.lazyList(bladeUser.getTenantId(), parentId, dept);
		return R.data(DeptWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 获取部门树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "树形结构", description = "树形结构")
	public R<List<DeptVO>> tree(String tenantId, BladeUser bladeUser) {
		List<DeptVO> tree = deptService.tree(Func.toStrWithEmpty(tenantId, bladeUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * 懒加载获取部门树形结构
	 */
	@GetMapping("/lazy-tree")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "懒加载树形结构", description = "树形结构")
	public R<List<DeptVO>> lazyTree(String tenantId, Long parentId, BladeUser bladeUser) {
		List<DeptVO> tree = deptService.lazyTree(Func.toStrWithEmpty(tenantId, bladeUser.getTenantId()), parentId);
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入dept")
	public R submit(@Valid @RequestBody Dept dept) {
		if (deptService.submit(dept)) {
			CacheUtil.clear(SYS_CACHE);
			CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
			// 返回懒加载树更新节点所需字段
			Kv kv = Kv.create().set("id", String.valueOf(dept.getId())).set("tenantId", dept.getTenantId())
				.set("deptCategoryName", DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory()));
			return R.data(kv);
		}
		return R.fail("操作失败");
	}

	/**
	 * 删除
	 */
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(deptService.removeDept(ids));
	}

	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "下拉数据源", description = "传入id集合")
	public R<List<Dept>> select(Long userId, String deptId) {
		if (Func.isNotEmpty(userId)) {
			User user = UserCache.getUser(userId);
			deptId = user.getDeptId();
		}
		List<Dept> list = deptService.list(Wrappers.<Dept>lambdaQuery().in(Dept::getId, Func.toLongList(deptId)));
		return R.data(list);
	}


}
