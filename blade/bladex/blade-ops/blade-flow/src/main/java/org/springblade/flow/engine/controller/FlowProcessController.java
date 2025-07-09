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

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.flow.core.pojo.entity.BladeFlow;
import org.springblade.flow.engine.service.FlowEngineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流程通用控制器
 *
 * @author Chill
 */
@NonDS
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/process")
@Hidden
public class FlowProcessController {

	private static final String IMAGE_NAME = "image";
	private final FlowEngineService flowEngineService;

	/**
	 * 获取流转历史列表
	 *
	 * @param processInstanceId 流程实例id
	 * @param startActivityId   开始节点id
	 * @param endActivityId     结束节点id
	 */
	@GetMapping(value = "history-flow-list")
	public R<List<BladeFlow>> historyFlowList(@RequestParam String processInstanceId, String startActivityId, String endActivityId) {
		return R.data(flowEngineService.historyFlowList(processInstanceId, startActivityId, endActivityId));
	}

	/**
	 * 流程节点进程图
	 *
	 * @param processDefinitionId 流程id
	 * @param processInstanceId 流程实例id
	 */
	@GetMapping(value = "model-view")
	public R modelView(String processDefinitionId, String processInstanceId) {
		return R.data(flowEngineService.modelView(processDefinitionId, processInstanceId));
	}

	/**
	 * 流程节点进程图
	 *
	 * @param processInstanceId   流程实例id
	 * @param httpServletResponse http响应
	 */
	@GetMapping(value = "diagram-view")
	public void diagramView(String processInstanceId, HttpServletResponse httpServletResponse) {
		flowEngineService.diagramView(processInstanceId, httpServletResponse);
	}

	/**
	 * 流程图展示
	 *
	 * @param processDefinitionId 流程id
	 * @param processInstanceId   实例id
	 * @param resourceType        资源类型
	 * @param response            响应
	 */
	@GetMapping("resource-view")
	public void resourceView(@RequestParam String processDefinitionId, String processInstanceId, @RequestParam(defaultValue = IMAGE_NAME) String resourceType, HttpServletResponse response) {
		flowEngineService.resourceView(processDefinitionId, processInstanceId, resourceType, response);
	}

}
