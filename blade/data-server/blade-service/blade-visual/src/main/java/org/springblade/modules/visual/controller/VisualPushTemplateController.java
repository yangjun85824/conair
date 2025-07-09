/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2024, https://bladex.cn. All rights reserved.
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.factory.MessageTemplate;
import org.springblade.modules.visual.pojo.entity.VisualPushTemplate;
import org.springblade.modules.visual.service.IVisualPushTemplateService;
import org.springframework.web.bind.annotation.*;

/**
 * 可视化消息模版配置模块 控制器
 *
 * @author Blade
 */
@RestController
@AllArgsConstructor
@RequestMapping("/push/template")
@Tag(name = "可视化消息模版配置模块", description = "消息模版配置表接口")
public class VisualPushTemplateController extends BladeController {

	private IVisualPushTemplateService pushTemplateService;
	private MessageTemplate messageTemplate;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入pushTemplate")
	public R<VisualPushTemplate> detail(VisualPushTemplate pushTemplate) {
		VisualPushTemplate detail = pushTemplateService.getOne(Condition.getQueryWrapper(pushTemplate));
		return R.data(detail);
	}

	/**
	 * 分页 消息模版配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入pushTemplate")
	public R<IPage<VisualPushTemplate>> list(VisualPushTemplate pushTemplate, Query query) {
		IPage<VisualPushTemplate> pages = pushTemplateService.page(Condition.getPage(query), Condition.getQueryWrapper(pushTemplate));
		return R.data(pages);
	}

	/**
	 * 新增 消息模版配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入pushTemplate")
	public R save(@Valid @RequestBody VisualPushTemplate pushTemplate) {
		return R.status(pushTemplateService.submit(pushTemplate));
	}

	/**
	 * 修改 消息模版配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入pushTemplate")
	public R update(@Valid @RequestBody VisualPushTemplate pushTemplate) {
		return R.status(pushTemplateService.submit(pushTemplate));
	}

	/**
	 * 新增或修改 消息模版配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入pushTemplate")
	public R submit(@Valid @RequestBody VisualPushTemplate pushTemplate) {
		return R.status(pushTemplateService.submit(pushTemplate));
	}


	/**
	 * 删除 消息模版配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(pushTemplateService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 调试 消息模版
	 */
	@PostMapping("/debug")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "调试消息模版", description = "传入json参数")
	public R debug(@RequestParam String code, String params) {
		messageTemplate.sendMessage(code, JsonUtil.parse(params, Kv.class));
		return R.success("调试完毕");
	}


}
