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
package org.springblade.flow.engine.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.flow.engine.constant.FlowEngineConstant;
import org.springblade.flow.engine.entity.FlowProcess;
import org.springblade.flow.engine.service.FlowEngineService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 流程管理接口
 *
 * @author Chill
 */
@NonDS
@RestController
@RequestMapping("/manager")
@AllArgsConstructor
@Tag(name = "流程管理接口", description = "流程管理接口")
@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
@Hidden
public class FlowManagerController {

	private final FlowEngineService flowEngineService;

	/**
	 * 分页
	 */
	@GetMapping("list")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "分页", description = "传入流程类型")
	public R<IPage<FlowProcess>> list(@Parameter(description = "流程类型") String category, Query query, @RequestParam(required = false, defaultValue = "1") Integer mode) {
		IPage<FlowProcess> pages = flowEngineService.selectProcessPage(Condition.getPage(query), category, mode);
		return R.data(pages);
	}

	/**
	 * 变更流程状态
	 *
	 * @param state     状态
	 * @param processId 流程id
	 */
	@PostMapping("change-state")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "变更流程状态", description = "传入state,processId")
	public R changeState(@RequestParam String state, @RequestParam String processId) {
		String msg = flowEngineService.changeState(state, processId);
		return R.success(msg);
	}

	/**
	 * 删除部署流程
	 *
	 * @param deploymentIds 部署流程id集合
	 */
	@PostMapping("delete-deployment")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "删除部署流程", description = "部署流程id集合")
	public R deleteDeployment(String deploymentIds) {
		return R.status(flowEngineService.deleteDeployment(deploymentIds));
	}

	/**
	 * 检查流程文件格式
	 *
	 * @param file 流程文件
	 */
	@PostMapping("check-upload")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "上传部署流程文件", description = "传入文件")
	public R checkUpload(@RequestParam MultipartFile file) {
		boolean temp = Objects.requireNonNull(file.getOriginalFilename()).endsWith(FlowEngineConstant.SUFFIX);
		return R.data(Kv.create().set("name", file.getOriginalFilename()).set("success", temp));
	}

	/**
	 * 上传部署流程文件
	 *
	 * @param files    流程文件
	 * @param category 类型
	 */
	@PostMapping("deploy-upload")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "上传部署流程文件", description = "传入文件")
	public R deployUpload(@RequestParam List<MultipartFile> files,
						  @RequestParam String category,
						  @RequestParam(required = false, defaultValue = "") String tenantIds) {
		return R.status(flowEngineService.deployUpload(files, category, Func.toStrList(tenantIds)));
	}

}
