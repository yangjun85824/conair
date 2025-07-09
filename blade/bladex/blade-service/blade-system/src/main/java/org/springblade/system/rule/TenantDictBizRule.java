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
package org.springblade.system.rule;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.system.pojo.entity.DictBiz;
import org.springblade.system.pojo.entity.Tenant;
import org.springblade.system.service.IDictBizService;

import java.util.LinkedList;
import java.util.List;

/**
 * 租户业务字典构建
 *
 * @author Chill
 */
@LiteflowComponent(id = "tenantDictBizRule", name = "租户业务字典构建")
public class TenantDictBizRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		// 获取上下文
		TenantContext contextBean = this.getFirstContextBean();
		Tenant tenant = contextBean.getTenant();
		IDictBizService dictBizService = contextBean.getDictBizService();

		// 新建租户对应的默认业务字典
		LinkedList<DictBiz> dictBizs = new LinkedList<>();
		List<DictBiz> dictBizList = getDictBizs(dictBizService, tenant.getTenantId(), dictBizs);

		// 设置上下文
		contextBean.setDictBizList(dictBizList);

	}


	private List<DictBiz> getDictBizs(IDictBizService dictBizService, String tenantId, LinkedList<DictBiz> dictBizs) {
		List<DictBiz> dictBizList = dictBizService.list(Wrappers.<DictBiz>query().lambda().eq(DictBiz::getParentId, BladeConstant.TOP_PARENT_ID).eq(DictBiz::getTenantId, BladeConstant.ADMIN_TENANT_ID).eq(DictBiz::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		dictBizList.forEach(dictBiz -> {
			Long oldParentId = dictBiz.getId();
			Long newParentId = IdWorker.getId();
			dictBiz.setId(newParentId);
			dictBiz.setTenantId(tenantId);
			dictBizs.add(dictBiz);
			recursionDictBiz(dictBizService, tenantId, oldParentId, newParentId, dictBizs);
		});
		return dictBizs;
	}

	private void recursionDictBiz(IDictBizService dictBizService, String tenantId, Long oldParentId, Long newParentId, LinkedList<DictBiz> dictBizs) {
		List<DictBiz> dictBizList = dictBizService.list(Wrappers.<DictBiz>query().lambda().eq(DictBiz::getParentId, oldParentId).eq(DictBiz::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		dictBizList.forEach(dictBiz -> {
			Long oldSubParentId = dictBiz.getId();
			Long newSubParentId = IdWorker.getId();
			dictBiz.setId(newSubParentId);
			dictBiz.setTenantId(tenantId);
			dictBiz.setParentId(newParentId);
			dictBizs.add(dictBiz);
			recursionDictBiz(dictBizService, tenantId, oldSubParentId, newSubParentId, dictBizs);
		});
	}

}
