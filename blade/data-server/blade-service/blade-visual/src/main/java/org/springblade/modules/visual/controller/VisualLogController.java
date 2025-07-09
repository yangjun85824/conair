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
package org.springblade.modules.visual.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.modules.visual.pojo.dto.VisualLogDTO;
import org.springblade.modules.visual.pojo.entity.VisualLog;
import org.springblade.modules.visual.service.IVisualLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可视化日志模块 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log")
@Tag(name = "可视化日志模块", description = "可视化日志接口")
public class VisualLogController {

	private final IVisualLogService visualLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入id")
	public R<VisualLogDTO> detail(Long id) {
		VisualLogDTO detail = visualLogService.detail(id);
		return R.data(detail);
	}

	/**
	 * 分页 日志表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入log")
	public R<IPage<VisualLogDTO>> list(VisualLogDTO log, Query query) {
		IPage<VisualLogDTO> pages = visualLogService.selectVisualLogPage(Condition.getPage(query), log);
		return R.data(pages);
	}

	/**
	 * 详情-全字段
	 */
	@GetMapping("/detail-all")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情-全字段", description = "传入id")
	public R<VisualLog> detailAll(Long id) {
		VisualLog detail = visualLogService.getById(id);
		return R.data(detail);
	}

	/**
	 * 分页-全字段 日志表
	 */
	@GetMapping("/list-all")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页-全字段", description = "传入log")
	public R<IPage<VisualLog>> listAll(VisualLog log, Query query) {
		IPage<VisualLog> pages = visualLogService.page(Condition.getPage(query.setDescs("create_time")), Condition.getQueryWrapper(log));
		return R.data(pages);
	}


}
