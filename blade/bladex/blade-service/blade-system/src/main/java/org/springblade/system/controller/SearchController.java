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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.pojo.entity.Post;
import org.springblade.system.service.IDeptService;
import org.springblade.system.service.IPostService;
import org.springblade.system.service.IRoleService;
import org.springblade.system.service.IUserService;
import org.springblade.system.pojo.vo.DeptVO;
import org.springblade.system.pojo.vo.PostVO;
import org.springblade.system.pojo.vo.RoleVO;
import org.springblade.system.pojo.vo.UserVO;
import org.springblade.system.wrapper.PostWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 查询控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/search")
@Tag(name = "信息查询", description = "信息查询")
public class SearchController {

	private final IRoleService roleService;

	private final IDeptService deptService;

	private final IPostService postService;

	private final IUserService userService;

	/**
	 * 角色信息查询
	 */
	@GetMapping("/role")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "角色信息查询", description = "传入roleName或者parentId")
	public R<List<RoleVO>> roleSearch(String roleName, Long parentId) {
		return R.data(roleService.search(roleName, parentId));
	}

	/**
	 * 部门信息查询
	 */
	@GetMapping("/dept")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "部门信息查询", description = "传入deptName或者parentId")
	public R<List<DeptVO>> deptSearch(String deptName, Long parentId) {
		return R.data(deptService.search(deptName, parentId));
	}

	/**
	 * 岗位信息查询
	 */
	@GetMapping("/post")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "岗位信息查询", description = "传入postName")
	public R<IPage<PostVO>> postSearch(String postName, Query query) {
		LambdaQueryWrapper<Post> queryWrapper = Wrappers.<Post>query().lambda();
		if (Func.isNotBlank(postName)) {
			queryWrapper.like(Post::getPostName, postName);
		}
		IPage<Post> pages = postService.page(Condition.getPage(query), queryWrapper);
		return R.data(PostWrapper.build().pageVO(pages));
	}

	/**
	 * 用户列表查询
	 */
	@Parameters({
		@Parameter(name = "name", description = "人员姓名", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "deptName", description = "部门名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "postName", description = "职位名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "current", description = "当前页数", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
		@Parameter(name = "size", description = "每页数量", in = ParameterIn.QUERY, schema = @Schema(type = "integer"))
	})
	@ApiOperationSupport(order = 4)
	@Operation(summary = "用户列表查询", description = "用户列表查询")
	@GetMapping("/user")
	public R<IPage<UserVO>> userSearch(@Parameter(hidden = true) UserVO user, @Parameter(hidden = true) Query query) {
		return R.data(userService.selectUserSearch(user, query));
	}

}
