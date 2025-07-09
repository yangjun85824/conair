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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Hidden;
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
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.Tenant;
import org.springblade.system.pojo.entity.TenantPackage;
import org.springblade.system.service.ITenantPackageService;
import org.springblade.system.service.ITenantService;
import org.springblade.system.wrapper.TenantWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;
import static org.springblade.core.tenant.constant.TenantBaseConstant.TENANT_DATASOURCE_CACHE;
import static org.springblade.core.tenant.constant.TenantBaseConstant.TENANT_DATASOURCE_EXIST_KEY;
import static org.springblade.system.cache.SysCache.TENANT_PACKAGE_ID;
import static org.springblade.system.cache.SysCache.TENANT_TENANT_ID;

/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@Hidden
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Tag(name = "租户管理", description = "接口")
public class TenantController extends BladeController {

	private final ITenantService tenantService;

	private final ITenantPackageService tenantPackageService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<Tenant> detail(Tenant tenant) {
		Tenant detail = tenantService.getOne(Condition.getQueryWrapper(tenant));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "tenantId", description = "参数名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "tenantName", description = "角色别名", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "contactNumber", description = "联系电话", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<Tenant>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> tenant, Query query, BladeUser bladeUser) {
		TenantWrapper.build().entityQuery(tenant);
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant, Tenant.class);
		IPage<Tenant> pages = tenantService.page(Condition.getPage(query), (!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(pages);
	}

	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "下拉数据源", description = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<List<Tenant>> select(Tenant tenant, BladeUser bladeUser) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant);
		List<Tenant> list = tenantService.list((!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(list);
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "分页", description = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<Tenant>> page(Tenant tenant, Query query) {
		IPage<Tenant> pages = tenantService.selectTenantPage(Condition.getPage(query), tenant);
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public R submit(@Valid @RequestBody Tenant tenant) {
		return R.status(tenantService.submitTenant(tenant));
	}

	/**
	 * 删除至回收站
	 */
	@PostMapping("/recycle")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除至回收站", description = "传入ids")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public R recycle(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tenantService.recycleTenant(Func.toLongList(ids)));
	}

	/**
	 * 从回收站恢复
	 */
	@PostMapping("/pass")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "从回收站恢复", description = "传入ids")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public R pass(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tenantService.passTenant(Func.toLongList(ids)));
	}

	/**
	 * 从回收站删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "从回收站删除", description = "传入ids")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tenantService.removeTenant(Func.toLongList(ids)));
	}

	/**
	 * 授权配置
	 */
	@PostMapping("/setting")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "授权配置", description = "传入ids,accountNumber,expireTime")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public R setting(@Parameter(description = "主键集合", required = true) @RequestParam String ids, @Parameter(description = "账号额度") Integer accountNumber, @Parameter(description = "过期时间") Date expireTime) {
		return R.status(tenantService.setting(accountNumber, expireTime, ids));
	}

	/**
	 * 数据源配置
	 */
	@PostMapping("datasource")
	@ApiOperationSupport(order = 10)
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@Operation(summary = "数据源配置", description = "传入datasource_id")
	public R datasource(@Parameter(description = "租户ID", required = true) @RequestParam String tenantId, @Parameter(description = "数据源ID", required = true) @RequestParam Long datasourceId){
		CacheUtil.evict(TENANT_DATASOURCE_CACHE, TENANT_DATASOURCE_EXIST_KEY, tenantId, Boolean.FALSE);
		return R.status(tenantService.update(Wrappers.<Tenant>update().lambda().set(Tenant::getDatasourceId, datasourceId).eq(Tenant::getTenantId, tenantId)));
	}

	/**
	 * 根据名称查询列表
	 *
	 * @param name 租户名称
	 */
	@GetMapping("/find-by-name")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "详情", description = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<List<Tenant>> findByName(String name) {
		List<Tenant> list = tenantService.list(Wrappers.<Tenant>query().lambda().like(Tenant::getTenantName, name));
		return R.data(list);
	}

	/**
	 * 根据域名查询信息
	 *
	 * @param domain 域名
	 */
	@GetMapping("/info")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "配置信息", description = "传入domain")
	public R<Kv> info(String domain) {
		Tenant tenant = tenantService.getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getDomainUrl, domain));
		Kv kv = Kv.create();
		if (tenant != null) {
			kv.set("tenantId", tenant.getTenantId())
				.set("domain", tenant.getDomainUrl())
				.set("backgroundUrl", tenant.getBackgroundUrl());
		}
		return R.data(kv);
	}

	/**
	 * 根据租户ID查询产品包详情
	 *
	 * @param tenantId 租户ID
	 */
	@GetMapping("/package-detail")
	@ApiOperationSupport(order = 13)
	@Operation(summary = "产品包详情", description = "传入tenantId")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	public R<TenantPackage> packageDetail(Long tenantId) {
		Tenant tenant = tenantService.getById(tenantId);
		return R.data(tenantPackageService.getById(tenant.getPackageId()));
	}

	/**
	 * 产品包配置
	 */
	@PostMapping("/package-setting")
	@ApiOperationSupport(order = 14)
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@Operation(summary = "产品包配置", description = "传入packageId")
	public R packageSetting(@Parameter(description = "租户ID", required = true) @RequestParam String tenantId, @Parameter(description = "产品包ID") Long packageId) {
		CacheUtil.evict(SYS_CACHE, TENANT_TENANT_ID, tenantId, Boolean.FALSE);
		CacheUtil.evict(SYS_CACHE, TENANT_PACKAGE_ID, tenantId, Boolean.FALSE);
		return R.status(tenantService.update(Wrappers.<Tenant>update().lambda().set(Tenant::getPackageId, packageId).eq(Tenant::getTenantId, tenantId)));
	}



}
