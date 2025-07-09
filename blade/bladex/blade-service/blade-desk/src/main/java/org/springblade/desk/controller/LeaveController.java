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
package org.springblade.desk.controller;

import lombok.AllArgsConstructor;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.desk.entity.ProcessLeave;
import org.springblade.desk.service.ILeaveService;
import org.springblade.system.cache.UserCache;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;

/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@Hidden
@RestController
@RequestMapping("/process/leave")
@AllArgsConstructor
public class LeaveController extends BladeController implements CacheNames {

	private final ILeaveService leaveService;

	/**
	 * 详情
	 *
	 * @param businessId 主键
	 */
	@GetMapping("detail")
	public R<ProcessLeave> detail(Long businessId) {
		ProcessLeave detail = leaveService.getById(businessId);
		detail.getFlow().setAssigneeName(UserCache.getUser(detail.getCreateUser()).getName());
		return R.data(detail);
	}

	/**
	 * 新增或修改
	 *
	 * @param leave 请假信息
	 */
	@PostMapping("start-process")
	public R startProcess(@RequestBody ProcessLeave leave) {
		return R.status(leaveService.startProcess(leave));
	}

}
