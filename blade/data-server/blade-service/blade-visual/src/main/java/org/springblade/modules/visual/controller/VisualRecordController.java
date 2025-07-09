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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.pojo.entity.VisualRecord;
import org.springblade.modules.visual.pojo.vo.VisualRecordVO;
import org.springblade.modules.visual.service.IVisualRecordService;
import org.springframework.web.bind.annotation.*;

/**
 * 可视化数据集模块 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/record")
@Tag(name = "可视化数据集模块", description = "可视化数据集接口")
public class VisualRecordController extends BladeController {

	private final IVisualRecordService visualRecordService;

	/**
	 * 可视化数据集表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入visualRecord")
	public R<VisualRecord> detail(VisualRecord visualRecord) {
		VisualRecord detail = visualRecordService.getOne(Condition.getQueryWrapper(visualRecord));
		return R.data(detail);
	}

	/**
	 * 可视化数据集表 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "name", description = "名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "dataType", description = "类型", in = ParameterIn.QUERY, schema = @Schema(type = "integer"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入visualRecord")
	public R<IPage<VisualRecordVO>> list(VisualRecordVO record, Query query) {
		IPage<VisualRecordVO> pages = visualRecordService.selectVisualRecordPage(Condition.getPage(query), record);
		return R.data(pages);
	}


	/**
	 * 可视化数据集表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description = "传入visualRecord")
	public R save(@Valid @RequestBody VisualRecord visualRecord) {
		return R.status(visualRecordService.save(visualRecord));
	}

	/**
	 * 可视化数据集表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description = "传入visualRecord")
	public R update(@Valid @RequestBody VisualRecord visualRecord) {
		return R.status(visualRecordService.updateById(visualRecord));
	}

	/**
	 * 可视化数据集表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入visualRecord")
	public R submit(@Valid @RequestBody VisualRecord visualRecord) {
		return R.status(visualRecordService.saveOrUpdate(visualRecord));
	}

	/**
	 * 可视化数据集表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualRecordService.deleteLogic(Func.toLongList(ids)));
	}


}
