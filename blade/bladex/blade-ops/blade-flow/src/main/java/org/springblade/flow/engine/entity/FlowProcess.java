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
import lombok.NoArgsConstructor;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.springblade.flow.engine.utils.FlowCache;

import java.io.Serializable;
import java.util.Date;

/**
 * FlowProcess
 *
 * @author Chill
 */
@Data
@NoArgsConstructor
public class FlowProcess implements Serializable {

	private String id;
	private String tenantId;
	private String name;
	private String key;
	private String category;
	private String categoryName;
	private Integer version;
	private String deploymentId;
	private String resourceName;
	private String diagramResourceName;
	private Integer suspensionState;
	private Date deploymentTime;

	public FlowProcess(ProcessDefinitionEntityImpl entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.tenantId = entity.getTenantId();
			this.name = entity.getName();
			this.key = entity.getKey();
			this.category = entity.getCategory();
			this.categoryName = FlowCache.getCategoryName(entity.getCategory());
			this.version = entity.getVersion();
			this.deploymentId = entity.getDeploymentId();
			this.resourceName = entity.getResourceName();
			this.diagramResourceName = entity.getDiagramResourceName();
			this.suspensionState = entity.getSuspensionState();
		}
	}

}
