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

import lombok.Data;
import org.springblade.core.tenant.TenantId;
import org.springblade.system.pojo.entity.*;
import org.springblade.system.service.IDictBizService;
import org.springblade.system.service.IMenuService;
import org.springblade.system.service.ITenantService;

import java.util.List;

/**
 * 租户上下文
 *
 * @author Chill
 */
@Data
public class TenantContext {

	/**
	 * 菜单业务
	 */
	private IMenuService menuService;

	/**
	 * 字典业务
	 */
	private IDictBizService dictBizService;

	/**
	 * 租户业务
	 */
	private ITenantService tenantService;

	/**
	 * 租户ID生成器
	 */
	private TenantId tenantIdGenerator;

	/**
	 * 租户
	 */
	private Tenant tenant;

	/**
	 * 角色
	 */
	private Role role;

	/**
	 * 角色菜单合集
	 */
	private List<RoleMenu> roleMenuList;

	/**
	 * 机构
	 */
	private Dept dept;

	/**
	 * 岗位
	 */
	private Post post;

	/**
	 * 业务字典合集
	 */
	private List<DictBiz> dictBizList;

	/**
	 * 用户
	 */
	private User user;

}
