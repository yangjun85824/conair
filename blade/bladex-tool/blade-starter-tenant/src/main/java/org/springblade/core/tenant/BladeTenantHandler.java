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
package org.springblade.core.tenant;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.annotation.TableExclude;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;

import java.util.*;

/**
 * 租户信息处理器
 *
 * @author Chill, L.cm
 */
@Slf4j
@RequiredArgsConstructor
public class BladeTenantHandler implements TenantLineHandler, SmartInitializingSingleton {
	/**
	 * 匹配的多租户表
	 */
	private final List<String> tenantTableList = new ArrayList<>();
	/**
	 * 需要排除进行自定义的多租户表
	 */
	private final List<String> excludeTableList = Arrays.asList("blade_user", "blade_dept", "blade_role", "blade_tenant", "act_de_model");
	/**
	 * 多租户配置
	 */
	private final BladeTenantProperties tenantProperties;

	/**
	 * 获取租户ID
	 *
	 * @return 租户ID
	 */
	@Override
	public Expression getTenantId() {
		return new StringValue(Func.toStr(AuthUtil.getTenantId(), BladeConstant.ADMIN_TENANT_ID));
	}

	/**
	 * 获取租户字段名称
	 *
	 * @return 租户字段名称
	 */
	@Override
	public String getTenantIdColumn() {
		return tenantProperties.getColumn();
	}

	/**
	 * 根据表名判断是否忽略拼接多租户条件
	 * 默认都要进行解析并拼接多租户条件
	 *
	 * @param tableName 表名
	 * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
	 */
	@Override
	public boolean ignoreTable(String tableName) {
		if (BladeTenantHolder.isIgnore()) {
			return true;
		}
		return !(tenantTableList.contains(tableName) && StringUtil.isNotBlank(AuthUtil.getTenantId()));
	}

	@Override
	public void afterSingletonsInstantiated() {
		ApplicationContext context = SpringUtil.getContext();
		if (tenantProperties.getAnnotationExclude() && context != null) {
			Map<String, Object> tables = context.getBeansWithAnnotation(TableExclude.class);
			List<String> excludeTables = tenantProperties.getExcludeTables();
			for (Object o : tables.values()) {
				TableExclude annotation = o.getClass().getAnnotation(TableExclude.class);
				String value = annotation.value();
				excludeTables.add(value);
			}
		}
		List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
		tableFor:
		for (TableInfo tableInfo : tableInfos) {
			String tableName = tableInfo.getTableName();
			if (tenantProperties.getExcludeTables().contains(tableName) ||
				excludeTableList.contains(tableName.toLowerCase()) ||
				excludeTableList.contains(tableName.toUpperCase())) {
				continue;
			}
			List<TableFieldInfo> fieldList = tableInfo.getFieldList();
			for (TableFieldInfo fieldInfo : fieldList) {
				String column = fieldInfo.getColumn();
				if (tenantProperties.getColumn().equals(column)) {
					tenantTableList.add(tableName);
					continue tableFor;
				}
			}
		}
	}
}
