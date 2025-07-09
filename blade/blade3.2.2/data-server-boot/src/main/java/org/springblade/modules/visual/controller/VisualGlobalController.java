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
package org.springblade.modules.visual.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.pojo.entity.VisualGlobal;
import org.springblade.modules.visual.service.IVisualGlobalService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

/**
 * 可视化全局配置表 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/visual-global")
@Tag(name = "可视化全局配置表", description = "可视化全局配置表接口")
public class VisualGlobalController extends BladeController {

	private final IVisualGlobalService visualGlobalService;

	/**
	 * 可视化全局配置表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualGlobal")
	public R<VisualGlobal> detail(VisualGlobal visualGlobal) {
		VisualGlobal detail = visualGlobalService.getOne(Condition.getQueryWrapper(visualGlobal));
		return R.data(detail);
	}

	/**
	 * 可视化全局配置表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入visualGlobal")
	public R<IPage<VisualGlobal>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> visualGlobal, Query query) {
		IPage<VisualGlobal> pages = visualGlobalService.page(Condition.getPage(query), Condition.getQueryWrapper(visualGlobal, VisualGlobal.class));
		return R.data(pages);
	}

	/**
	 * 可视化全局配置表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入visualGlobal")
	public R save(@Valid @RequestBody VisualGlobal visualGlobal) {
		return R.status(visualGlobalService.save(visualGlobal));
	}

	/**
	 * 可视化全局配置表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入visualGlobal")
	public R update(@Valid @RequestBody VisualGlobal visualGlobal) {
		return R.status(visualGlobalService.updateById(visualGlobal));
	}

	/**
	 * 可视化全局配置表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入visualGlobal")
	public R submit(@Valid @RequestBody VisualGlobal visualGlobal) {
		return R.status(visualGlobalService.saveOrUpdate(visualGlobal));
	}

	/**
	 * 可视化全局配置表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description =  "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualGlobalService.removeByIds(Func.toLongList(ids)));
	}

}
