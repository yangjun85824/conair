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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 *
 * @author Chill
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "blade.tenant")
public class BladeTenantProperties {

	/**
	 * 是否增强多租户
	 */
	private Boolean enhance = Boolean.FALSE;

	/**
	 * 是否开启授权码校验
	 */
	private Boolean license = Boolean.FALSE;

	/**
	 * 是否开启动态数据源功能
	 */
	private Boolean dynamicDatasource = Boolean.FALSE;

	/**
	 * 是否开启动态数据源全局扫描
	 */
	private Boolean dynamicGlobal = Boolean.FALSE;

	/**
	 * 多租户字段名称
	 */
	private String column = "tenant_id";

	/**
	 * 是否开启注解排除
	 */
	private Boolean annotationExclude = Boolean.FALSE;

	/**
	 * 需要排除进行自定义的多租户表
	 */
	private List<String> excludeTables = new ArrayList<>();

}
