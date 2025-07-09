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
package org.springblade.flow.engine.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 运行实体类
 *
 * @author Chill
 */
@Data
public class FlowExecution implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String startUserId;
	private String startUser;
	private Date startTime;
	private String taskDefinitionId;
	private String taskDefinitionKey;
	private String category;
	private String categoryName;
	private String processInstanceId;
	private String processDefinitionId;
	private String processDefinitionKey;
	private String activityId;
	private int suspensionState;
	private String executionId;

}
