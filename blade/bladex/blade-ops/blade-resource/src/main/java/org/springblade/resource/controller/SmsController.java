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
package org.springblade.resource.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.pojo.entity.Sms;
import org.springblade.resource.service.ISmsService;
import org.springblade.resource.pojo.vo.SmsVO;
import org.springblade.resource.wrapper.SmsWrapper;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;

import jakarta.validation.Valid;

import static org.springblade.core.cache.constant.CacheConstant.RESOURCE_CACHE;

/**
 * 短信配置表 控制器
 *
 * @author BladeX
 */
@NonDS
@Hidden
@RestController
@AllArgsConstructor
@RequestMapping("/sms")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
@Tag(name = "短信配置表", description = "短信配置表接口")
public class SmsController extends BladeController {

	private final ISmsService smsService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入sms")
	public R<SmsVO> detail(Sms sms) {
		Sms detail = smsService.getOne(Condition.getQueryWrapper(sms));
		return R.data(SmsWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 短信配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入sms")
	public R<IPage<SmsVO>> list(Sms sms, Query query) {
		IPage<Sms> pages = smsService.page(Condition.getPage(query), Condition.getQueryWrapper(sms));
		return R.data(SmsWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 短信配置表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入sms")
	public R<IPage<SmsVO>> page(SmsVO sms, Query query) {
		IPage<SmsVO> pages = smsService.selectSmsPage(Condition.getPage(query), sms);
		return R.data(pages);
	}

	/**
	 * 新增 短信配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入sms")
	public R save(@Valid @RequestBody Sms sms) {
		CacheUtil.clear(RESOURCE_CACHE);
		return R.status(smsService.save(sms));
	}

	/**
	 * 修改 短信配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入sms")
	public R update(@Valid @RequestBody Sms sms) {
		CacheUtil.clear(RESOURCE_CACHE);
		return R.status(smsService.updateById(sms));
	}

	/**
	 * 新增或修改 短信配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入sms")
	public R submit(@Valid @RequestBody Sms sms) {
		CacheUtil.clear(RESOURCE_CACHE);
		return R.status(smsService.submit(sms));
	}


	/**
	 * 删除 短信配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(RESOURCE_CACHE);
		return R.status(smsService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 启用
	 */
	@PostMapping("/enable")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "配置启用", description = "传入id")
	public R enable(@Parameter(description = "主键", required = true) @RequestParam Long id) {
		CacheUtil.clear(RESOURCE_CACHE);
		return R.status(smsService.enable(id));
	}


}
