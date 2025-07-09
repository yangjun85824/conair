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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.TenantPackage;
import org.springblade.system.service.ITenantPackageService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 租户产品表 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant-package")
@Tag(name = "租户产品包", description = "租户产品包")
public class TenantPackageController extends BladeController {

	private final ITenantPackageService tenantPackageService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入tenantPackage")
	public R<TenantPackage> detail(TenantPackage tenantPackage) {
		TenantPackage detail = tenantPackageService.getOne(Condition.getQueryWrapper(tenantPackage));
		return R.data(detail);
	}

	/**
	 * 分页 租户产品表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "packageName", description = "产品包名", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入tenantPackage")
	public R<IPage<TenantPackage>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> tenantPackage, Query query) {
		IPage<TenantPackage> pages = tenantPackageService.page(Condition.getPage(query), Condition.getQueryWrapper(tenantPackage, TenantPackage.class));
		return R.data(pages);
	}

	/**
	 * 新增 租户产品表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description = "传入tenantPackage")
	public R save(@Valid @RequestBody TenantPackage tenantPackage) {
		return R.status(tenantPackageService.save(tenantPackage));
	}

	/**
	 * 修改 租户产品表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description = "传入tenantPackage")
	public R update(@Valid @RequestBody TenantPackage tenantPackage) {
		return R.status(tenantPackageService.updateById(tenantPackage));
	}

	/**
	 * 新增或修改 租户产品表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入tenantPackage")
	public R submit(@Valid @RequestBody TenantPackage tenantPackage) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(tenantPackageService.saveOrUpdate(tenantPackage));
	}


	/**
	 * 删除 租户产品表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(tenantPackageService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "下拉数据源", description = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<List<TenantPackage>> select(TenantPackage tenantPackage) {
		return R.data(tenantPackageService.list(Condition.getQueryWrapper(tenantPackage)));
	}


}
