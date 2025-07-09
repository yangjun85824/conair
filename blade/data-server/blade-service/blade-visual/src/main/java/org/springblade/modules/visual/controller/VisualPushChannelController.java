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
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.visual.factory.enums.MessageType;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;
import org.springblade.modules.visual.pojo.vo.VisualPushChannelVO;
import org.springblade.modules.visual.service.IVisualPushChannelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 可视化消息推送渠道模块 控制器
 *
 * @author Blade
 */
@RestController
@AllArgsConstructor
@RequestMapping("/push/channel")
@Tag(name = "可视化消息推送渠道模块", description = "消息推送渠道表接口")
public class VisualPushChannelController extends BladeController {

	private IVisualPushChannelService pushChannelService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入pushChannel")
	public R<VisualPushChannel> detail(VisualPushChannel pushChannel) {
		VisualPushChannel detail = pushChannelService.getOne(Condition.getQueryWrapper(pushChannel));
		return R.data(detail);
	}

	/**
	 * 分页 消息推送渠道表
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "pushName", description = "名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "pushType", description = "类型", in = ParameterIn.QUERY, schema = @Schema(type = "integer"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入pushChannel")
	public R<IPage<VisualPushChannel>> list(String pushName, Integer pushType, Query query) {
		IPage<VisualPushChannel> pages = pushChannelService.page(Condition.getPage(query),
			Condition.getQueryWrapper(Kv.create().set("pushName", pushName).set("pushType", pushType), VisualPushChannel.class));
		return R.data(pages);
	}

	/**
	 * 新增 消息推送渠道表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入pushChannel")
	public R save(@Valid @RequestBody VisualPushChannel pushChannel) {
		return R.status(pushChannelService.submit(pushChannel));
	}

	/**
	 * 修改 消息推送渠道表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入pushChannel")
	public R update(@Valid @RequestBody VisualPushChannel pushChannel) {
		return R.status(pushChannelService.submit(pushChannel));
	}

	/**
	 * 新增或修改 消息推送渠道表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入pushChannel")
	public R submit(@Valid @RequestBody VisualPushChannel pushChannel) {
		return R.status(pushChannelService.submit(pushChannel));
	}


	/**
	 * 删除 消息推送渠道表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(pushChannelService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 渠道列表
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "渠道列表", description = "渠道")
	public R<List<VisualPushChannelVO>> select() {
		List<VisualPushChannelVO> list = pushChannelService.list().stream().map(pushChannel -> {
			VisualPushChannelVO channelVO = BeanUtil.copyProperties(pushChannel, VisualPushChannelVO.class);
			channelVO.setPushTypeName(MessageType.ofName(pushChannel.getPushType()));
			return channelVO;
		}).collect(Collectors.toList());
		return R.data(list);
	}


}
