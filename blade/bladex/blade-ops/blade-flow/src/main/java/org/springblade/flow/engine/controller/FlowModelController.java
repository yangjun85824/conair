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
package org.springblade.flow.engine.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.flow.engine.entity.FlowModel;
import org.springblade.flow.engine.service.FlowEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 流程模型控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@RequestMapping("/model")
@AllArgsConstructor
@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
@Hidden
public class FlowModelController {

	private final FlowEngineService flowEngineService;

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "modelKey", description = "模型标识", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "模型名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 1)
	@Operation(summary = "分页", description = "传入notice")
	public R<IPage<FlowModel>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> flow, Query query) {
		IPage<FlowModel> pages = flowEngineService.page(Condition.getPage(query), Condition.getQueryWrapper(flow, FlowModel.class)
			.select("id,model_key modelKey,name,description,version,created,last_updated lastUpdated")
			.orderByDesc("last_updated"));
		return R.data(pages);
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "删除", description = "传入主键集合")
	public R remove(@Parameter(description = "主键集合") @RequestParam String ids) {
		boolean temp = flowEngineService.removeByIds(Func.toStrList(ids));
		return R.status(temp);
	}

	/**
	 * 部署
	 */
	@PostMapping("/deploy")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "部署", description = "传入模型id和分类")
	public R deploy(@Parameter(description = "模型id") @RequestParam String modelId,
					@Parameter(description = "工作流分类") @RequestParam String category,
					@Parameter(description = "租户ID") @RequestParam(required = false, defaultValue = "") String tenantIds) {
		boolean temp = flowEngineService.deployModel(modelId, category, Func.toStrList(tenantIds));
		return R.status(temp);
	}

	@PostMapping("submit")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "保存/编辑")
	@Parameters({
		@Parameter(name = "id", description = "模型id"),
		@Parameter(name = "name", description = "模型名称", required = true),
		@Parameter(name = "modelKey", description = "模型key", required = true),
		@Parameter(name = "description", description = "模型描述"),
		@Parameter(name = "xml", description = "模型xml", required = true),
	})
	public R<FlowModel> submit(@RequestBody @Parameter(hidden = true) FlowModel model) {
		return R.data(flowEngineService.submitModel(model));
	}

	@GetMapping("detail")
	@Operation(summary = "详情")
	@ApiOperationSupport(order = 5)
	@Parameters({
		@Parameter(name = "id", description = "模型id", required = true),
	})
	public R<FlowModel> detail(String id) {
		return R.data(flowEngineService.getById(id));
	}

}
