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
package org.springblade.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.node.TreeNode;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.Menu;
import org.springblade.system.pojo.entity.TopMenu;
import org.springblade.system.service.IMenuService;
import org.springblade.system.service.ITopMenuService;
import org.springblade.system.pojo.vo.CheckedTreeVO;
import org.springblade.system.pojo.vo.GrantTreeVO;
import org.springblade.system.pojo.vo.MenuVO;
import org.springblade.system.wrapper.MenuWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.MENU_CACHE;


/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Tag(name = "菜单", description = "菜单")
public class MenuController extends BladeController {

	private final IMenuService menuService;
	private final ITopMenuService topMenuService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入menu")
	public R<MenuVO> detail(Menu menu) {
		Menu detail = menuService.getOne(Condition.getQueryWrapper(menu));
		return R.data(MenuWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "code", description = "菜单编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "菜单名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 2)
	@Operation(summary = "列表", description = "传入menu")
	public R<List<MenuVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().orderByAsc(Menu::getSort));
		return R.data(MenuWrapper.build().listNodeVO(list));
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-list")
	@Parameters({
		@Parameter(name = "code", description = "菜单编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "菜单名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 3)
	@Operation(summary = "懒加载列表", description = "传入menu")
	public R<List<MenuVO>> lazyList(Long parentId, @Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<MenuVO> list = menuService.lazyList(parentId, menu);
		return R.data(MenuWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 菜单列表
	 */
	@GetMapping("/menu-list")
	@Parameters({
		@Parameter(name = "code", description = "菜单编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "菜单名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 4)
	@Operation(summary = "菜单列表", description = "传入menu")
	public R<List<MenuVO>> menuList(@Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<Menu> list = menuService.list(Condition.getQueryWrapper(menu, Menu.class).lambda().eq(Menu::getCategory, 1).orderByAsc(Menu::getSort));
		return R.data(MenuWrapper.build().listNodeVO(list));
	}

	/**
	 * 懒加载菜单列表
	 */
	@GetMapping("/lazy-menu-list")
	@Parameters({
		@Parameter(name = "code", description = "菜单编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "菜单名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 5)
	@Operation(summary = "懒加载菜单列表", description = "传入menu")
	public R<List<MenuVO>> lazyMenuList(Long parentId, @Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<MenuVO> list = menuService.lazyMenuList(parentId, menu);
		return R.data(MenuWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入menu")
	public R submit(@Valid @RequestBody Menu menu) {
		if (menuService.submit(menu)) {
			CacheUtil.clear(MENU_CACHE);
			CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
			// 返回懒加载树更新节点所需字段
			Kv kv = Kv.create().set("id", String.valueOf(menu.getId()));
			return R.data(kv);
		}
		return R.fail("操作失败");
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
	@ApiOperationSupport(order = 7)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(MENU_CACHE);
		CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
		return R.status(menuService.removeMenu(ids));
	}

	/**
	 * 前端菜单数据
	 */
	@GetMapping("/routes")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "前端菜单数据", description = "前端菜单数据")
	public R<List<MenuVO>> routes(BladeUser user, Long topMenuId) {
		List<MenuVO> list = menuService.routes((user == null) ? null : user.getRoleId(), topMenuId);
		return R.data(list);
	}

	/**
	 * 前端按钮数据
	 */
	@GetMapping("/buttons")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "前端按钮数据", description = "前端按钮数据")
	public R<List<MenuVO>> buttons(BladeUser user) {
		List<MenuVO> list = menuService.buttons(user.getRoleId());
		return R.data(list);
	}

	/**
	 * 获取菜单树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "树形结构", description = "树形结构")
	public R<List<TreeNode>> tree() {
		List<TreeNode> tree = menuService.tree();
		return R.data(tree);
	}

	/**
	 * 获取权限分配树形结构
	 */
	@GetMapping("/grant-tree")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "权限分配树形结构", description = "权限分配树形结构")
	public R<GrantTreeVO> grantTree(BladeUser user) {
		GrantTreeVO vo = new GrantTreeVO();
		vo.setMenu(menuService.grantTree(user));
		vo.setDataScope(menuService.grantDataScopeTree(user));
		vo.setApiScope(menuService.grantApiScopeTree(user));
		return R.data(vo);
	}

	/**
	 * 获取权限分配树形结构
	 */
	@GetMapping("/role-tree-keys")
	@ApiOperationSupport(order = 13)
	@Operation(summary = "角色所分配的树", description = "角色所分配的树")
	public R<CheckedTreeVO> roleTreeKeys(String roleIds) {
		CheckedTreeVO vo = new CheckedTreeVO();
		vo.setMenu(menuService.roleTreeKeys(roleIds));
		vo.setDataScope(menuService.dataScopeTreeKeys(roleIds));
		vo.setApiScope(menuService.apiScopeTreeKeys(roleIds));
		return R.data(vo);
	}

	/**
	 * 获取顶部菜单树形结构
	 */
	@GetMapping("/grant-top-tree")
	@ApiOperationSupport(order = 14)
	@Operation(summary = "顶部菜单树形结构", description = "顶部菜单树形结构")
	public R<GrantTreeVO> grantTopTree(BladeUser user) {
		GrantTreeVO vo = new GrantTreeVO();
		vo.setMenu(menuService.grantTopTree(user));
		return R.data(vo);
	}

	/**
	 * 获取顶部菜单树形结构
	 */
	@GetMapping("/top-tree-keys")
	@ApiOperationSupport(order = 15)
	@Operation(summary = "顶部菜单所分配的树", description = "顶部菜单所分配的树")
	public R<CheckedTreeVO> topTreeKeys(String topMenuIds) {
		CheckedTreeVO vo = new CheckedTreeVO();
		vo.setMenu(menuService.topTreeKeys(topMenuIds));
		return R.data(vo);
	}

	/**
	 * 顶部菜单数据
	 */
	@GetMapping("/top-menu")
	@ApiOperationSupport(order = 16)
	@Operation(summary = "顶部菜单数据", description = "顶部菜单数据")
	public R<List<TopMenu>> topMenu(BladeUser user) {
		if (Func.isEmpty(user)) {
			return null;
		}
		List<TopMenu> list = topMenuService.list(Wrappers.<TopMenu>query().lambda().orderByAsc(TopMenu::getSort));
		return R.data(list);
	}

	/**
	 * 获取配置的角色权限
	 */
	@GetMapping("auth-routes")
	@ApiOperationSupport(order = 17)
	@Operation(summary = "菜单的角色权限")
	public R<List<Kv>> authRoutes(BladeUser user) {
		if (Func.isEmpty(user)) {
			return null;
		}
		return R.data(menuService.authRoutes(user));
	}
}
