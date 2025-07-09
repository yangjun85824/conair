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
import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.pojo.dto.VisualMapDTO;
import org.springblade.modules.visual.pojo.entity.VisualMap;
import org.springblade.modules.visual.service.IVisualMapService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

/**
 * 可视化地图配置表 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/map")
@Tag(name = "可视化地图配置表", description = "可视化地图配置接口")
public class VisualMapController extends BladeController {

	private final IVisualMapService visualMapService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualMap")
	public R<VisualMap> detail(VisualMap visualMap) {
		VisualMap detail = visualMapService.getOne(Condition.getQueryWrapper(visualMap));
		return R.data(detail);
	}

	/**
	 * 数据详情
	 */
	@GetMapping("/data")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "数据详情", description = "传入id")
	public Map<String, Object> data(Long id) {
		VisualMap detail = visualMapService.getById(id);
		return JsonUtil.toMap(detail.getData());
	}

	/**
	 * 自定义分页 可视化地图配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入visualMap")
	public R<IPage<VisualMapDTO>> list(VisualMapDTO visualMap, Query query) {
		IPage<VisualMapDTO> pages = visualMapService.selectVisualMapPage(Condition.getPage(query), visualMap);
		return R.data(pages);
	}

	/**
	 * 新增 可视化地图配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入visualMap")
	public R save(@Valid @RequestBody VisualMap visualMap) {
		return R.status(visualMapService.save(visualMap));
	}

	/**
	 * 修改 可视化地图配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入visualMap")
	public R update(@Valid @RequestBody VisualMap visualMap) {
		return R.status(visualMapService.updateById(visualMap));
	}


	/**
	 * 删除 可视化地图配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description =  "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualMapService.removeByIds(Func.toLongList(ids)));
	}


}
