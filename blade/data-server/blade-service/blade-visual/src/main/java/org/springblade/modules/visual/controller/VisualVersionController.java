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
import org.springblade.modules.visual.pojo.entity.VisualVersion;
import org.springblade.modules.visual.service.IVisualVersionService;
import org.springframework.web.bind.annotation.*;

/**
 * 可视化版本表 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/version")
@Tag(name = "可视化版本表", description = "可视化版本表接口")
public class VisualVersionController extends BladeController {

	private final IVisualVersionService visualVersionService;

	/**
	 * 可视化版本表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualVersion")
	public R<VisualVersion> detail(VisualVersion visualVersion) {
		VisualVersion detail = visualVersionService.getOne(Condition.getQueryWrapper(visualVersion));
		return R.data(detail);
	}

	/**
	 * 可视化版本表 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "versionId", description = "大屏主键", in = ParameterIn.QUERY, schema = @Schema(type = "number"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入visualVersion")
	public R<IPage<VisualVersion>> list(@RequestParam Long visualId, Query query) {
		IPage<VisualVersion> pages = visualVersionService.page(Condition.getPage(query.setDescs("create_time")),
			Condition.getQueryWrapper(Kv.create().set("visualId_equal", visualId), VisualVersion.class));
		return R.data(pages);
	}

	/**
	 * 可视化版本表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description = "传入visualVersion")
	public R save(@Valid @RequestBody VisualVersion visualVersion) {
		return R.status(visualVersionService.save(visualVersion));
	}

	/**
	 * 可视化版本表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description = "传入visualVersion")
	public R update(@Valid @RequestBody VisualVersion visualVersion) {
		return R.status(visualVersionService.updateById(visualVersion));
	}

	/**
	 * 可视化版本表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入visualVersion")
	public R submit(@Valid @RequestBody VisualVersion visualVersion) {
		return R.status(visualVersionService.saveOrUpdate(visualVersion));
	}

	/**
	 * 可视化版本表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualVersionService.removeBatchByIds(Func.toLongList(ids)));
	}

}
