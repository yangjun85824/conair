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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springblade.system.cache.ParamCache;
import org.springblade.core.tenant.TenantId;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.Tenant;
import org.springblade.system.service.ITenantService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springblade.common.constant.TenantConstant.ACCOUNT_NUMBER_KEY;
import static org.springblade.common.constant.TenantConstant.DEFAULT_ACCOUNT_NUMBER;

/**
 * 租户构建
 *
 * @author Chill
 */
@LiteflowComponent(id = "tenantRule", name = "租户构建")
public class TenantRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		// 获取上下文
		TenantContext contextBean = this.getFirstContextBean();
		Tenant tenant = contextBean.getTenant();
		TenantId tenantIdGenerator = contextBean.getTenantIdGenerator();
		ITenantService tenantService = contextBean.getTenantService();

		// 获取租户ID
		List<Tenant> tenants = tenantService.list(Wrappers.<Tenant>query().lambda().eq(Tenant::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		List<String> codes = tenants.stream().map(Tenant::getTenantId).collect(Collectors.toList());
		String tenantId = getTenantId(tenantIdGenerator, codes);
		tenant.setTenantId(tenantId);
		// 获取参数配置的账号额度
		int accountNumber = Func.toInt(ParamCache.getValue(ACCOUNT_NUMBER_KEY), DEFAULT_ACCOUNT_NUMBER);
		tenant.setAccountNumber(accountNumber);

		// 设置上下文
		contextBean.setTenant(tenant);

	}

	private String getTenantId(TenantId tenantIdGenerator, List<String> codes) {
		String code = tenantIdGenerator.generate();
		if (codes.contains(code)) {
			return getTenantId(tenantIdGenerator, codes);
		}
		return code;
	}
}
