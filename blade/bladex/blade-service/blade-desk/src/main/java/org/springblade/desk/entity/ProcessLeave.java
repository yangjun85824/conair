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
package org.springblade.desk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.flow.core.pojo.entity.FlowEntity;

import java.io.Serial;
import java.util.Date;

/**
 * 请假流程实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_process_leave")
@EqualsAndHashCode(callSuper = true)
public class ProcessLeave extends FlowEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 流程定义id
	 */
	private String processDefinitionId;
	/**
	 * 流程实例id
	 */
	private String processInstanceId;
	/**
	 * 请假开始时间
	 */
	private Date startTime;
	/**
	 * 请假结束时间
	 */
	private Date endTime;
	/**
	 * 请假理由
	 */
	private String reason;
	/**
	 * 审批人
	 */
	private String taskUser;
	/**
	 * 流程申请时间
	 */
	private Date applyTime;

}
