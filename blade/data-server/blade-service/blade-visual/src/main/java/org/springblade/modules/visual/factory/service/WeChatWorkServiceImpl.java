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
package org.springblade.modules.visual.factory.service;

import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.OkHttpUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;

/**
 * 企业微信消息推送
 *
 * @author Chill
 */
@AllArgsConstructor
public class WeChatWorkServiceImpl implements MessageService {

	/**
	 * 消息渠道
	 */
	private VisualPushChannel channel;
	/**
	 * 消息模版
	 */
	private String message;

	@Override
	public void sendMessage() {
		String json = String.format("{\"msgtype\": \"text\", \"text\": {\"content\": \"%s\"}}", message);
		String resp = OkHttpUtil.postJson(channel.getWebhook(), json);
		if (StringUtil.isBlank(resp)) {
			throw new ServiceException(String.format("企业微信消息推送失败，webhook：%s，消息：%s", channel.getWebhook(), message));
		}
	}
}
