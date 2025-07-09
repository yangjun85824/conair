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
package org.springblade.desk.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.desk.entity.ProcessLeave;
import org.springblade.desk.mapper.LeaveMapper;
import org.springblade.desk.service.ILeaveService;
import org.springblade.flow.core.constant.ProcessConstant;
import org.springblade.flow.core.pojo.entity.BladeFlow;
import org.springblade.flow.core.feign.IFlowClient;
import org.springblade.flow.core.utils.FlowUtil;
import org.springblade.flow.core.utils.TaskUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class LeaveServiceImpl extends BaseServiceImpl<LeaveMapper, ProcessLeave> implements ILeaveService {

	private final IFlowClient flowClient;

	@Override
	@Transactional(rollbackFor = Exception.class)
	// @GlobalTransactional
	public boolean startProcess(ProcessLeave leave) {
		String businessTable = FlowUtil.getBusinessTable(ProcessConstant.LEAVE_KEY);
		if (Func.isEmpty(businessTable)) {
			throw new ServiceException("流程启动失败,未找到相关业务表");
		}
		if (Func.isEmpty(leave.getId())) {
			// 保存leave
			leave.setApplyTime(DateUtil.now());
			save(leave);
			// 启动流程
			Kv variables = Kv.create()
				.set(ProcessConstant.TASK_VARIABLE_CREATE_USER, AuthUtil.getUserName())
				.set("taskUser", TaskUtil.getTaskUser(leave.getTaskUser()))
				.set("days", DateUtil.between(leave.getStartTime(), leave.getEndTime()).toDays());
			R<BladeFlow> result = flowClient.startProcessInstanceById(leave.getProcessDefinitionId(), FlowUtil.getBusinessKey(businessTable, String.valueOf(leave.getId())), variables);
			if (result.isSuccess()) {
				log.debug("流程已启动,流程ID:" + result.getData().getProcessInstanceId());
				// 返回流程id写入leave
				leave.setProcessInstanceId(result.getData().getProcessInstanceId());
				updateById(leave);
			} else {
				throw new ServiceException("开启流程失败");
			}
		} else {

			updateById(leave);
		}
		return true;
	}

}
