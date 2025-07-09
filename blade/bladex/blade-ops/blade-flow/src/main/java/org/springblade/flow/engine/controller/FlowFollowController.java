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
import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.flow.engine.entity.FlowExecution;
import org.springblade.flow.engine.service.FlowEngineService;
import org.springframework.web.bind.annotation.*;

/**
 * 流程状态控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@RequestMapping("/follow")
@AllArgsConstructor
@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
@Hidden
public class FlowFollowController {

	private final FlowEngineService flowEngineService;

	/**
	 * 流程状态列表
	 */
	@GetMapping("list")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "分页", description = "传入notice")
	public R<IPage<FlowExecution>> list(Query query, @Parameter(description = "流程实例id") String processInstanceId, @Parameter(description = "流程key") String processDefinitionKey) {
		IPage<FlowExecution> pages = flowEngineService.selectFollowPage(Condition.getPage(query), processInstanceId, processDefinitionKey);
		return R.data(pages);
	}

	/**
	 * 删除流程实例
	 */
	@PostMapping("delete-process-instance")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "删除", description = "传入主键集合")
	public R deleteProcessInstance(@Parameter(description = "流程实例id") @RequestParam String processInstanceId, @Parameter(description = "删除原因") @RequestParam String deleteReason) {
		boolean temp = flowEngineService.deleteProcessInstance(processInstanceId, deleteReason);
		return R.status(temp);
	}

}
