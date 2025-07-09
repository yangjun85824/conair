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
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.Menu;
import org.springblade.system.pojo.entity.RoleMenu;
import org.springblade.system.service.IMenuService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springblade.common.constant.TenantConstant.ACCOUNT_MENU_CODE_KEY;
import static org.springblade.common.constant.TenantConstant.MENU_CODES;

/**
 * 租户角色菜单构建
 *
 * @author Chill
 */
@LiteflowComponent(id = "tenantRoleMenuRule", name = "租户角色菜单构建")
public class TenantRoleMenuRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		// 获取上下文
		TenantContext contextBean = this.getFirstContextBean();
		IMenuService menuService = contextBean.getMenuService();
		// 新建租户对应的角色菜单权限
		LinkedList<Menu> userMenus = new LinkedList<>();
		// 获取参数配置的默认菜单集合，逗号隔开
		List<String> menuCodes = Func.toStrList(ParamCache.getValue(ACCOUNT_MENU_CODE_KEY));
		List<Menu> menus = getMenus(menuService, (!menuCodes.isEmpty() ? menuCodes : MENU_CODES), userMenus);
		List<RoleMenu> roleMenuList = new ArrayList<>();
		menus.forEach(menu -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuId(menu.getId());
			roleMenuList.add(roleMenu);
		});
		// 设置上下文
		contextBean.setRoleMenuList(roleMenuList);
	}

	private List<Menu> getMenus(IMenuService menuService, List<String> codes, LinkedList<Menu> menus) {
		codes.forEach(code -> {
			Menu menu = menuService.getOne(Wrappers.<Menu>query().lambda().eq(Menu::getCode, code).eq(Menu::getIsDeleted, BladeConstant.DB_NOT_DELETED));
			if (menu != null) {
				menus.add(menu);
				recursionMenu(menuService, menu.getId(), menus);
			}
		});
		return menus;
	}

	private void recursionMenu(IMenuService menuService, Long parentId, LinkedList<Menu> menus) {
		List<Menu> menuList = menuService.list(Wrappers.<Menu>query().lambda().eq(Menu::getParentId, parentId).eq(Menu::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		menus.addAll(menuList);
		menuList.forEach(menu -> recursionMenu(menuService, menu.getId(), menus));
	}
}
