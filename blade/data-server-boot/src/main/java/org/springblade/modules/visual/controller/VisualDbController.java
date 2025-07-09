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
import org.springblade.core.api.crypto.annotation.decrypt.ApiDecryptAes;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.pojo.dto.VisualDbDTO;
import org.springblade.modules.visual.dynamic.DynamicModel;
import org.springblade.modules.visual.pojo.entity.VisualDb;
import org.springblade.modules.visual.service.IVisualDbService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 数据源表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/db")
@Tag(name = "可视化数据源配置表", description = "可视化数据源配置接口")
public class VisualDbController {

	private final IVisualDbService visualDbService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入datasource")
	public R<VisualDb> detail(VisualDb db) {
		VisualDb detail = visualDbService.getOne(Condition.getQueryWrapper(db));
		return R.data(detail);
	}

	/**
	 * 分页 数据源配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入datasource")
	public R<IPage<VisualDb>> list(VisualDb db, Query query) {
		IPage<VisualDb> pages = visualDbService.page(Condition.getPage(query), Condition.getQueryWrapper(db));
		return R.data(pages);
	}

	/**
	 * 新增 数据源配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入datasource")
	public R save(@Valid @RequestBody VisualDb db) {
		db.setUrl(db.getUrl().replace("&amp;", "&"));
		if (visualDbService.dbTest(db)) {
			return R.status(visualDbService.save(db));
		}
		throw new ServiceException("系统检测数据库未能连通,请检查配置");
	}

	/**
	 * 修改 数据源配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入datasource")
	public R update(@Valid @RequestBody VisualDb db) {
		db.setUrl(db.getUrl().replace("&amp;", "&"));
		if (visualDbService.dbTest(db)) {
			return R.status(visualDbService.updateById(db));
		}
		throw new ServiceException("系统检测数据库未能连通,请检查配置");
	}

	/**
	 * 新增或修改 数据源配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入datasource")
	public R submit(@Valid @RequestBody VisualDb db) {
		db.setUrl(db.getUrl().replace("&amp;", "&"));
		if (visualDbService.dbTest(db)) {
			return R.status(visualDbService.saveOrUpdate(db));
		}
		throw new ServiceException("系统检测数据库未能连通,请检查配置");
	}


	/**
	 * 删除 数据源配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description =  "主键集合", required = true) @RequestParam String ids) {
		return R.status(visualDbService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 数据源测试连接
	 */
	@ApiDecryptAes
	@PostMapping("/db-test")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "数据源测试连接", description = "数据源测试连接")
	public R<Boolean> dbTest(@RequestBody VisualDb db) {
		return R.status(visualDbService.dbTest(db));
	}

	/**
	 * 数据源列表
	 */
	@GetMapping("/db-list")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "下拉数据源", description = "数据源列表")
	public R<List<VisualDbDTO>> dbList() {
		return R.data(visualDbService.dbList());
	}

	/**
	 * 动态执行SQL
	 */
	@ApiDecryptAes
	@PostMapping("dynamic-query")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "动态执行SQL", description = "动态执行SQL")
	public R dynamicQuery(@RequestBody DynamicModel model) {
		List<LinkedHashMap<String, Object>> linkedHashMaps = visualDbService.dynamicQuery(model.getId(), model.getSql());
		return R.data(linkedHashMaps);
	}

}
