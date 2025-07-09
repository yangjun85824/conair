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
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.log.DataLog;
import org.springblade.modules.visual.pojo.entity.VisualMap;
import org.springblade.modules.visual.pojo.vo.VisualMapVO;
import org.springblade.modules.visual.service.IVisualMapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 可视化地图配置模块 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/map")
@Tag(name = "可视化地图配置模块", description = "可视化地图配置接口")
public class VisualMapController extends BladeController {

	private final IVisualMapService visualMapService;

	/**
	 * 详情
	 */
	@DataLog("地图详情")
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
	@DataLog("地图数据")
	@GetMapping("/data")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "数据详情", description = "传入id")
	public Map<String, Object> data(Long id) {
		VisualMap detail = visualMapService.getById(id);
		return JsonUtil.toMap(detail.getData());
	}

	/**
	 * 可视化地图懒加载列表
	 */
	@DataLog("地图列表")
	@GetMapping("/lazy-list")
	@Parameters({
		@Parameter(name = "parentId", description = "地图父主键", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "地图名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "懒加载列表", description = "传入parentId,name")
	public R<List<VisualMapVO>> lazyList(@RequestParam String parentId, String name) {
		List<VisualMapVO> list = visualMapService.lazyList(parentId, name);
		return R.data(list);
	}

	/**
	 * 新增 可视化地图配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入visualMap")
	public R save(@Valid @RequestBody VisualMap visualMap) {
		return R.status(visualMapService.submit(visualMap));
	}

	/**
	 * 修改 可视化地图配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入visualMap")
	public R update(@Valid @RequestBody VisualMap visualMap) {
		return R.status(visualMapService.submit(visualMap));
	}


	/**
	 * 删除 可视化地图配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualMapService.removeByIds(Func.toLongList(ids)));
	}


}
