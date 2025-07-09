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

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.system.pojo.entity.Dept;
import org.springblade.system.pojo.entity.Tenant;

/**
 * 租户机构构建
 *
 * @author Chill
 */
@LiteflowComponent(id = "tenantDeptRule", name = "租户机构构建")
public class TenantDeptRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		// 获取上下文
		TenantContext contextBean = this.getFirstContextBean();
		Tenant tenant = contextBean.getTenant();

		// 新建租户对应的默认部门
		Dept dept = new Dept();
		dept.setTenantId(tenant.getTenantId());
		dept.setParentId(BladeConstant.TOP_PARENT_ID);
		dept.setAncestors(String.valueOf(BladeConstant.TOP_PARENT_ID));
		dept.setDeptName(tenant.getTenantName());
		dept.setFullName(tenant.getTenantName());
		dept.setDeptCategory(1);
		dept.setSort(2);
		dept.setIsDeleted(BladeConstant.DB_NOT_DELETED);

		// 设置上下文
		contextBean.setDept(dept);

	}
}
