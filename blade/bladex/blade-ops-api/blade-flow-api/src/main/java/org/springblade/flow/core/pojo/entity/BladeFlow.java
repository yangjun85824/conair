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
package org.springblade.flow.core.pojo.entity;

import lombok.Data;
import org.springblade.flow.core.constant.ProcessConstant;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 工作流通用实体类
 *
 * @author Chill
 */
@Data
public class BladeFlow implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 任务编号
	 */
	private String taskId;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务定义Key
	 */
	private String taskDefinitionKey;
	/**
	 * 任务执行人编号
	 */
	private String assignee;
	/**
	 * 任务执行人名称
	 */
	private String assigneeName;
	/**
	 * 流程分类
	 */
	private String category;
	/**
	 * 流程分类名
	 */
	private String categoryName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 签收时间
	 */
	private Date claimTime;
	/**
	 * 历史任务结束时间
	 */
	private Date historyTaskEndTime;
	/**
	 * 执行ID
	 */
	private String executionId;
	/**
	 * 流程实例ID
	 */
	private String processInstanceId;
	/**
	 * 流程ID
	 */
	private String processDefinitionId;
	/**
	 * 流程标识
	 */
	private String processDefinitionKey;
	/**
	 * 流程名
	 */
	private String processDefinitionName;
	/**
	 * 流程版本
	 */
	private int processDefinitionVersion;
	/**
	 * 流程说明
	 */
	private String processDefinitionDesc;
	/**
	 * 流程简图名
	 */
	private String processDefinitionDiagramResName;
	/**
	 * 流程重命名
	 */
	private String processDefinitionResName;
	/**
	 * 历史任务流程实例ID 查看流程图会用到
	 */
	private String historyProcessInstanceId;
	/**
	 * 流程实例是否结束
	 */
	private String processIsFinished;
	/**
	 * 历史活动ID
	 */
	private String historyActivityId;
	/**
	 * 历史活动流程
	 */
	private String historyActivityName;
	/**
	 * 历史活动耗时
	 */
	private String historyActivityDurationTime;
	/**
	 * 业务绑定Table
	 */
	private String businessTable;
	/**
	 * 业务绑定ID
	 */
	private String businessId;
	/**
	 * 任务状态
	 */
	private String status;
	/**
	 * 任务意见
	 */
	private String comment;
	/**
	 * 是否通过
	 */
	private boolean isPass;
	/**
	 * 是否通过代号
	 */
	private String flag;
	/**
	 * 开始查询日期
	 */
	private Date beginDate;
	/**
	 * 结束查询日期
	 */
	private Date endDate;
	/**
	 * 流程参数
	 */
	private Map<String, Object> variables;

	/**
	 * 获取是否通过
	 */
	public boolean isPass() {
		return ProcessConstant.PASS_ALIAS.equals(flag) || ProcessConstant.PASS_COMMENT.equals(comment);
	}

}
