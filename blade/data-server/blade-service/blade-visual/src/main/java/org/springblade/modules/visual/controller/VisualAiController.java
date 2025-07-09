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
import org.springblade.modules.visual.pojo.entity.VisualAi;
import org.springblade.modules.visual.service.IVisualAiService;
import org.springframework.web.bind.annotation.*;

/**
 * 可视化大模型表 控制器
 *
 * @author Blade
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ai")
@Tag(name = "可视化大模型表", description = "可视化大模型表接口")
public class VisualAiController extends BladeController {

	private IVisualAiService visualAiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualAi")
	public R<VisualAi> detail(VisualAi visualAi) {
		VisualAi detail = visualAiService.getOne(Condition.getQueryWrapper(visualAi));
		return R.data(detail);
	}

	/**
	 * 分页 可视化大模型表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "code", description = "编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入visualAi")
	public R<IPage<VisualAi>> list(String code, String name, Query query) {
		IPage<VisualAi> pages = visualAiService.page(Condition.getPage(query),
			Condition.getQueryWrapper(Kv.create().set("code", code).set("name", name), VisualAi.class));
		return R.data(pages);
	}

	/**
	 * 新增 可视化大模型表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description = "传入visualAi")
	public R save(@Valid @RequestBody VisualAi visualAi) {
		return R.status(visualAiService.save(visualAi));
	}

	/**
	 * 修改 可视化大模型表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description = "传入visualAi")
	public R update(@Valid @RequestBody VisualAi visualAi) {
		return R.status(visualAiService.updateById(visualAi));
	}

	/**
	 * 新增或修改 可视化大模型表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入visualAi")
	public R submit(@Valid @RequestBody VisualAi visualAi) {
		return R.status(visualAiService.saveOrUpdate(visualAi));
	}


	/**
	 * 删除 可视化大模型表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualAiService.removeByIds(Func.toLongList(ids)));
	}


}
