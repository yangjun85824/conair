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
package org.springblade.modules.visual.factory;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.TemplateUtil;
import org.springblade.modules.visual.cache.PushCache;
import org.springblade.modules.visual.factory.enums.MessageType;
import org.springblade.modules.visual.factory.service.*;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;
import org.springblade.modules.visual.pojo.entity.VisualPushTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 消息工厂
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class MessageServiceFactory {

	/**
	 * 构建消息服务
	 *
	 * @param code   模版编号
	 * @param params 模版参数
	 * @return 消息服务
	 */
	public MessageService getService(String code, Kv params) {
		// 根据模版编号查询绑定数据
		VisualPushTemplate template = PushCache.getTemplate(code);
		VisualPushChannel channel = PushCache.getChannel(template.getChannelId());
		// 解析模版参数
		String message = TemplateUtil.process(template.getTemplateParam(), params);
		// 获取枚举类
		MessageType messageType = MessageType.of(channel.getPushType());
		// 根据枚举类实例化具体的消息服务
		switch (Objects.requireNonNull(messageType)) {
			// 钉钉
			case DING_TALK:
				return new DingTalkServiceImpl(channel, message);
			// 企业微信
			case WECHAT_WORK:
				return new WeChatWorkServiceImpl(channel, message);
			// 邮件
			case EMAIL:
				return new EmailServiceImpl(channel, message, params);
			// 阿里云短信
			case ALI_SMS:
				return new AliSmsServiceImpl(channel, template.getTemplateParam(), params);
			// 腾讯云短信
			case TENCENT_SMS:
				return new TencentSmsServiceImpl(channel, template.getTemplateParam(), params);
			// 未知
			default:
				throw new IllegalArgumentException("暂不支持此服务: " + messageType);
		}
	}

}
