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
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.pojo.entity.VisualAssets;
import org.springblade.modules.visual.service.IVisualAssetsService;
import org.springframework.web.bind.annotation.*;

/**
 * 可视化资源模块 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/assets")
@Tag(name = "可视化资源模块", description = "可视化资源表接口")
public class VisualAssetsController extends BladeController {

	private final IVisualAssetsService visualAssetsService;

	/**
	 * 可视化资源表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualAssets")
	public R<VisualAssets> detail(VisualAssets visualAssets) {
		VisualAssets detail = visualAssetsService.getOne(Condition.getQueryWrapper(visualAssets));
		return R.data(detail);
	}

	/**
	 * 可视化资源表 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "assetsName", description = "名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "assetsType", description = "类型", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入visualAssets")
	public R<IPage<VisualAssets>> list(String assetsName, String assetsType, Query query) {
		IPage<VisualAssets> pages = visualAssetsService.page(Condition.getPage(query.setDescs("assets_time")),
			Condition.getQueryWrapper(Kv.create().set("assetsName", assetsName).set("assetsType", assetsType), VisualAssets.class));
		return R.data(pages);
	}

	/**
	 * 可视化资源表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入visualAssets")
	public R save(@Valid @RequestBody VisualAssets visualAssets) {
		return R.status(visualAssetsService.save(visualAssets));
	}

	/**
	 * 可视化资源表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入visualAssets")
	public R update(@Valid @RequestBody VisualAssets visualAssets) {
		return R.status(visualAssetsService.updateById(visualAssets));
	}

	/**
	 * 可视化资源表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入visualAssets")
	public R submit(@Valid @RequestBody VisualAssets visualAssets) {
		return R.status(visualAssetsService.saveOrUpdate(visualAssets));
	}

	/**
	 * 可视化资源表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualAssetsService.removeByIds(Func.toLongList(ids)));
	}

}
