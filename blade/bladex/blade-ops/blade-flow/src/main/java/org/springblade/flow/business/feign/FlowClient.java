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
package org.springblade.flow.business.feign;

import lombok.AllArgsConstructor;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.flow.core.pojo.entity.BladeFlow;
import org.springblade.flow.core.feign.IFlowClient;
import org.springblade.flow.core.utils.TaskUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 流程远程调用实现类
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
public class FlowClient implements IFlowClient {

	private final RuntimeService runtimeService;
	private final IdentityService identityService;
	private final TaskService taskService;

	@Override
	@PostMapping(START_PROCESS_INSTANCE_BY_ID)
	public R<BladeFlow> startProcessInstanceById(String processDefinitionId, String businessKey, @RequestBody Map<String, Object> variables) {
		// 设置流程启动用户
		identityService.setAuthenticatedUserId(TaskUtil.getTaskUser());
		// 开启流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
		// 组装流程通用类
		BladeFlow flow = new BladeFlow();
		flow.setProcessInstanceId(processInstance.getId());
		return R.data(flow);
	}

	@Override
	@PostMapping(START_PROCESS_INSTANCE_BY_KEY)
	public R<BladeFlow> startProcessInstanceByKey(String processDefinitionKey, String businessKey, @RequestBody Map<String, Object> variables) {
		// 设置流程启动用户
		identityService.setAuthenticatedUserId(TaskUtil.getTaskUser());
		// 开启流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
		// 组装流程通用类
		BladeFlow flow = new BladeFlow();
		flow.setProcessInstanceId(processInstance.getId());
		return R.data(flow);
	}

	@Override
	@PostMapping(COMPLETE_TASK)
	public R completeTask(String taskId, String processInstanceId, String comment, @RequestBody Map<String, Object> variables) {
		// 增加评论
		if (StringUtil.isNoneBlank(processInstanceId, comment)) {
			taskService.addComment(taskId, processInstanceId, comment);
		}
		// 非空判断
		if (Func.isEmpty(variables)) {
			variables = Kv.create();
		}
		// 完成任务
		taskService.complete(taskId, variables);
		return R.success("流程提交成功");
	}

	@Override
	@GetMapping(TASK_VARIABLE)
	public R<Object> taskVariable(String taskId, String variableName) {
		return R.data(taskService.getVariable(taskId, variableName));
	}

	@Override
	@GetMapping(TASK_VARIABLES)
	public R<Map<String, Object>> taskVariables(String taskId) {
		return R.data(taskService.getVariables(taskId));
	}

}
