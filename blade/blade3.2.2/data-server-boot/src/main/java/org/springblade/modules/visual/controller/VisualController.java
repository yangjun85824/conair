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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.oss.OssTemplate;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.pojo.dto.VisualDTO;
import org.springblade.modules.visual.pojo.entity.Visual;
import org.springblade.modules.visual.pojo.entity.VisualCategory;
import org.springblade.modules.visual.log.DataLog;
import org.springblade.modules.visual.service.IVisualCategoryService;
import org.springblade.modules.visual.service.IVisualService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 可视化表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/visual")
@Tag(name = "可视化表", description = "可视化数据接口")
public class VisualController extends BladeController {

	private final IVisualService visualService;
	private final IVisualCategoryService categoryService;
	private final OssTemplate ossTemplate;

	/**
	 * 详情
	 */
	@DataLog("大屏详情")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visual")
	public R<VisualDTO> detail(@RequestParam Long id) {
		VisualDTO detail = visualService.detail(id);
		return R.data(detail);
	}

	/**
	 * 分页 可视化表
	 */
	@DataLog("大屏列表")
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入visual")
	public R<IPage<Visual>> list(Visual visual, Query query) {
		IPage<Visual> pages = visualService.page(Condition.getPage(query), Condition.getQueryWrapper(visual));
		return R.data(pages);
	}

	/**
	 * 新增 可视化表
	 */
	@DataLog("大屏新增")
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description = "传入visual")
	public R save(@Valid @RequestBody VisualDTO visual) {
		boolean temp = visualService.saveVisual(visual);
		if (temp) {
			Long id = visual.getVisual().getId();
			return R.data(Kv.create().set("id", String.valueOf(id)));
		} else {
			return R.status(false);
		}
	}

	/**
	 * 修改 可视化表
	 */
	@DataLog("大屏修改")
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description = "传入visual")
	public R update(@Valid @RequestBody VisualDTO visual) {
		return R.status(visualService.updateVisual(visual));
	}


	/**
	 * 删除 可视化表
	 */
	@DataLog("大屏删除")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description =  "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 复制 可视化表
	 */
	@DataLog("大屏复制")
	@PostMapping("/copy")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "复制", description = "传入id")
	public R<String> copy(@Parameter(description =  "主键集合", required = true) @RequestParam Long id) {
		return R.data(visualService.copyVisual(id));
	}

	/**
	 * 获取分类
	 */
	@GetMapping("category")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "获取类型")
	public R category() {
		List<VisualCategory> list = categoryService.list();
		return R.data(list);
	}

	/**
	 * 上传文件
	 */
	@DataLog("资源上传")
	@SneakyThrows
	@PostMapping("/put-file")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "上传", description = "传入文件")
	public R<BladeFile> putFile(@Parameter(description =  "上传文件", required = true) @RequestParam MultipartFile file) {
		BladeFile bladeFile = ossTemplate.putFile(file);
		return R.data(bladeFile);
	}

}
