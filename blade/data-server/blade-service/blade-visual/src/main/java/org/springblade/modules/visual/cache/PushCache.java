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
package org.springblade.modules.visual.cache;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;
import org.springblade.modules.visual.pojo.entity.VisualPushTemplate;
import org.springblade.modules.visual.service.IVisualPushChannelService;
import org.springblade.modules.visual.service.IVisualPushTemplateService;

/**
 * 消息推送缓存
 *
 * @author Chill
 */
public class PushCache {

	public static final String PUSH_CACHE = "blade:push";
	public static final String CHANNEL_ID = "channel:key:";
	public static final String TEMPLATE_CODE = "template:key:";

	private static final IVisualPushChannelService CHANNEL_SERVICE;
	private static final IVisualPushTemplateService TEMPLATE_SERVICE;

	static {
		CHANNEL_SERVICE = SpringUtil.getBean(IVisualPushChannelService.class);
		TEMPLATE_SERVICE = SpringUtil.getBean(IVisualPushTemplateService.class);
	}

	/**
	 * 获取消息模版实体
	 *
	 * @param templateCode 模版编号
	 * @return PushTemplateEntity
	 */
	public static VisualPushTemplate getTemplate(String templateCode) {
		return CacheUtil.get(PUSH_CACHE, TEMPLATE_CODE, templateCode, () -> TEMPLATE_SERVICE.getOne(Wrappers.<VisualPushTemplate>lambdaQuery().eq(VisualPushTemplate::getTemplateCode, templateCode)));
	}

	/**
	 * 获取消息渠道实体
	 *
	 * @param channelId 渠道id
	 * @return PushChannelEntity
	 */
	public static VisualPushChannel getChannel(Long channelId) {
		return CacheUtil.get(PUSH_CACHE, CHANNEL_ID, channelId, () -> CHANNEL_SERVICE.getById(channelId));
	}


}
