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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.BladePage;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tenant.annotation.TenantDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.desk.pojo.entity.Notice;
import org.springblade.desk.feign.INoticeClient;
import org.springblade.desk.service.INoticeService;
import org.springblade.desk.pojo.vo.NoticeVO;
import org.springblade.desk.wrapper.NoticeWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@TenantDS
@RestController
@RequestMapping("notice")
@AllArgsConstructor
@Tag(name = "用户博客", description = "博客接口")
public class NoticeController extends BladeController {

	private final INoticeService noticeService;

	private final INoticeClient noticeClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入notice")
	public R<NoticeVO> detail(Notice notice) {
		Notice detail = noticeService.getOne(Condition.getQueryWrapper(notice));
		return R.data(NoticeWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "category", description = "公告类型", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
		@Parameter(name = "title", description = "公告标题", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入notice")
	public R<IPage<NoticeVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> notice, Query query) {
		NoticeWrapper.build().noticeQuery(notice);
		IPage<Notice> pages = noticeService.page(Condition.getPage(query), Condition.getQueryWrapper(notice, Notice.class));
		return R.data(NoticeWrapper.build().pageVO(pages));
	}

	/**
	 * 多表联合查询自定义分页
	 */
	@GetMapping("/page")
	@Parameters({
		@Parameter(name = "category", description = "公告类型", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
		@Parameter(name = "title", description = "公告标题", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入notice")
	public R<IPage<NoticeVO>> page(@Parameter(hidden = true) NoticeVO notice, Query query) {
		IPage<NoticeVO> pages = noticeService.selectNoticePage(Condition.getPage(query), notice);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入notice")
	public R save(@RequestBody Notice notice) {
		return R.status(noticeService.save(notice));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入notice")
	public R update(@RequestBody Notice notice) {
		return R.status(noticeService.updateById(notice));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入notice")
	public R submit(@RequestBody Notice notice) {
		return R.status(noticeService.saveOrUpdate(notice));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入notice")
	public R remove(@Parameter(description = "主键集合") @RequestParam String ids) {
		boolean temp = noticeService.deleteLogic(Func.toLongList(ids));
		return R.status(temp);
	}

	/**
	 * 远程调用分页接口
	 */
	@GetMapping("/top")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "分页远程调用", description = "传入current,size")
	public R<BladePage<Notice>> top(@Parameter(description = "当前页") @RequestParam Integer current, @Parameter(description = "每页显示条数") @RequestParam Integer size) {
		BladePage<Notice> page = noticeClient.top(current, size);
		return R.data(page);
	}

}
