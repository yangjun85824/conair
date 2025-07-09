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
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.pojo.entity.VisualComponent;
import org.springblade.modules.visual.pojo.vo.VisualComponentVO;
import org.springblade.modules.visual.service.IVisualComponentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 可视化组件模块 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/component")
@Tag(name = "可视化组件模块", description = "可视化组件接口")
public class VisualComponentController extends BladeController {

	private final IVisualComponentService visualComponentService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualComponent")
	public R<VisualComponent> detail(VisualComponent visualComponent) {
		VisualComponent detail = visualComponentService.getOne(Condition.getQueryWrapper(visualComponent));
		return R.data(detail);
	}

	/**
	 * 数据详情
	 */
	@GetMapping("/content")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "组件详情", description = "传入id")
	public Map<String, Object> content(Long id) {
		VisualComponent detail = visualComponentService.getById(id);
		return JsonUtil.toMap(detail.getContent());
	}

	/**
	 * 自定义分页 可视化组件表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "name", description = "名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "type", description = "类型", in = ParameterIn.QUERY, schema = @Schema(type = "integer"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入name,type")
	public R<IPage<VisualComponentVO>> list(VisualComponentVO visualComponent, Query query) {
		IPage<VisualComponentVO> pages = visualComponentService.selectVisualComponentPage(Condition.getPage(query), visualComponent);
		return R.data(pages);
	}

	/**
	 * 新增 可视化组件表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入visualComponent")
	public R save(@Valid @RequestBody VisualComponent visualComponent) {
		return R.status(visualComponentService.save(visualComponent));
	}

	/**
	 * 修改 可视化组件表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入visualComponent")
	public R update(@Valid @RequestBody VisualComponent visualComponent) {
		return R.status(visualComponentService.updateById(visualComponent));
	}


	/**
	 * 删除 可视化组件表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description =  "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualComponentService.removeByIds(Func.toLongList(ids)));
	}


}
