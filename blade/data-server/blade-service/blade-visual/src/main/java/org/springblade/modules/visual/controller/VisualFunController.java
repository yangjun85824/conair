/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2024, https://bladex.cn. All rights reserved.
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
import org.springblade.modules.visual.pojo.entity.VisualFun;
import org.springblade.modules.visual.pojo.vo.VisualFunVO;
import org.springblade.modules.visual.service.IVisualFunService;
import org.springframework.web.bind.annotation.*;

/**
 * 可视化代码库表 控制器
 *
 * @author Blade
 */
@RestController
@AllArgsConstructor
@RequestMapping("/fun")
@Tag(name = "可视化代码库表", description = "可视化代码库表接口")
public class VisualFunController extends BladeController {

	private IVisualFunService visualFunService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualFun")
	public R<VisualFun> detail(VisualFun visualFun) {
		VisualFun detail = visualFunService.getOne(Condition.getQueryWrapper(visualFun));
		return R.data(detail);
	}

	/**
	 * 分页 可视化代码库表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入visualFun")
	public R<IPage<VisualFun>> list(VisualFun visualFun, Query query) {
		IPage<VisualFun> pages = visualFunService.page(Condition.getPage(query), Condition.getQueryWrapper(visualFun));
		return R.data(pages);
	}

	/**
	 * 自定义分页 可视化代码库表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入visualFun")
	public R<IPage<VisualFunVO>> page(VisualFunVO visualFun, Query query) {
		IPage<VisualFunVO> pages = visualFunService.selectVisualFunPage(Condition.getPage(query), visualFun);
		return R.data(pages);
	}

	/**
	 * 新增 可视化代码库表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入visualFun")
	public R save(@Valid @RequestBody VisualFun visualFun) {
		return R.status(visualFunService.save(visualFun));
	}

	/**
	 * 修改 可视化代码库表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入visualFun")
	public R update(@Valid @RequestBody VisualFun visualFun) {
		return R.status(visualFunService.updateById(visualFun));
	}

	/**
	 * 新增或修改 可视化代码库表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入visualFun")
	public R submit(@Valid @RequestBody VisualFun visualFun) {
		return R.status(visualFunService.saveOrUpdate(visualFun));
	}


	/**
	 * 删除 可视化代码库表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualFunService.removeBatchByIds(Func.toLongList(ids)));
	}


}
