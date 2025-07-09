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
package org.springblade.flow.engine.utils;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.ProcessDefinition;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.flow.engine.entity.FlowProcess;
import org.springblade.system.cache.DictCache;

/**
 * 流程缓存
 *
 * @author Chill
 */
public class FlowCache {

	private static final String FLOW_CACHE = "flow:process";
	private static final String FLOW_DEFINITION_ID = "definition:id";
	private static RepositoryService repositoryService;

	private static RepositoryService getRepositoryService() {
		if (repositoryService == null) {
			repositoryService = SpringUtil.getBean(RepositoryService.class);
		}
		return repositoryService;
	}

	/**
	 * 获得流程定义对象
	 *
	 * @param processDefinitionId 流程对象id
	 * @return
	 */
	public static FlowProcess getProcessDefinition(String processDefinitionId) {
		return CacheUtil.get(FLOW_CACHE, FLOW_DEFINITION_ID, processDefinitionId, () -> {
			ProcessDefinition processDefinition = getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
			ProcessDefinitionEntityImpl processDefinitionEntity = BeanUtil.copyProperties(processDefinition, ProcessDefinitionEntityImpl.class);
			return new FlowProcess(processDefinitionEntity);
		});
	}

	/**
	 * 获取流程类型名
	 *
	 * @param category 流程类型
	 * @return
	 */
	public static String getCategoryName(String category) {
		if (Func.isEmpty(category)) {
			return StringPool.EMPTY;
		}
		String[] categoryArr = category.split(StringPool.UNDERSCORE);
		if (categoryArr.length <= 1) {
			return StringPool.EMPTY;
		} else {
			return DictCache.getValue(category.split(StringPool.UNDERSCORE)[0], Func.toInt(category.split(StringPool.UNDERSCORE)[1]));
		}
	}

}
