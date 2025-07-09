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
package org.springblade.flow.core.feign;

import org.springblade.core.tool.api.R;
import org.springblade.flow.core.pojo.entity.BladeFlow;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 流程远程调用失败处理类
 *
 * @author Chill
 */
@Component
public class IFlowClientFallback implements IFlowClient {

	@Override
	public R<BladeFlow> startProcessInstanceById(String processDefinitionId, String businessKey, Map<String, Object> variables) {
		return R.fail("远程调用失败");
	}

	@Override
	public R<BladeFlow> startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
		return R.fail("远程调用失败");
	}

	@Override
	public R completeTask(String taskId, String processInstanceId, String comment, Map<String, Object> variables) {
		return R.fail("远程调用失败");
	}

	@Override
	public R<Object> taskVariable(String taskId, String variableName) {
		return R.fail("远程调用失败");
	}

	@Override
	public R<Map<String, Object>> taskVariables(String taskId) {
		return R.fail("远程调用失败");
	}

}
