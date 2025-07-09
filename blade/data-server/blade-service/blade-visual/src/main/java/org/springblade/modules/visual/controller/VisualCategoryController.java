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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.pojo.entity.VisualCategory;
import org.springblade.modules.visual.service.IVisualCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 可视化分类模块 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/category")
@Tag(name = "可视化分类模块", description = "可视化分类接口")
public class VisualCategoryController extends BladeController {

	private final IVisualCategoryService visualCategoryService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualCategory")
	public R<VisualCategory> detail(VisualCategory visualCategory) {
		VisualCategory detail = visualCategoryService.getOne(Condition.getQueryWrapper(visualCategory));
		return R.data(detail);
	}

	/**
	 * 列表 可视化分类表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "列表", description = "传入visualCategory")
	public R<List<VisualCategory>> list(VisualCategory visualCategory) {
		List<VisualCategory> list = visualCategoryService.list(Condition.getQueryWrapper(visualCategory));
		return R.data(list);
	}

	/**
	 * 分页 可视化分类表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入visualCategory")
	public R<IPage<VisualCategory>> page(VisualCategory visualCategory, Query query) {
		IPage<VisualCategory> pages = visualCategoryService.page(Condition.getPage(query), Condition.getQueryWrapper(visualCategory));
		return R.data(pages);
	}

	/**
	 * 新增 可视化分类表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入visualCategory")
	public R save(@Valid @RequestBody VisualCategory visualCategory) {
		return R.status(visualCategoryService.submit(visualCategory));
	}

	/**
	 * 修改 可视化分类表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入visualCategory")
	public R update(@Valid @RequestBody VisualCategory visualCategory) {
		return R.status(visualCategoryService.submit(visualCategory));
	}

	/**
	 * 删除 可视化分类表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualCategoryService.removeByIds(Func.toLongList(ids)));
	}


}
