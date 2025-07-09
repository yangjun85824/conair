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

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程模型
 *
 * @author Chill
 */
@Data
@TableName("ACT_DE_MODEL")
public class FlowModel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	public static final int MODEL_TYPE_BPMN = 0;
	public static final int MODEL_TYPE_FORM = 2;
	public static final int MODEL_TYPE_APP = 3;
	public static final int MODEL_TYPE_DECISION_TABLE = 4;
	public static final int MODEL_TYPE_CMMN = 5;

	private String id;
	private String name;
	private String modelKey;
	private String description;
	private Date created;
	private Date lastUpdated;
	private String createdBy;
	private String lastUpdatedBy;
	private Integer version;
	private String modelEditorJson;
	private String modelComment;
	private Integer modelType;
	private String tenantId;
	private byte[] thumbnail;
	private String modelEditorXml;

}
