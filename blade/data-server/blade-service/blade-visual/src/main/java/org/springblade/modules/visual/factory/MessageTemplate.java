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
import org.springblade.modules.visual.factory.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * 消息发送类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class MessageTemplate {
	/**
	 * 消息服务工厂
	 */
	private final MessageServiceFactory messageServiceFactory;

	/**
	 * 发送消息
	 *
	 * @param code   消息模版编号
	 * @param params 消息模版参数
	 */
	public void sendMessage(String code, Kv params) {
		MessageService messageService = messageServiceFactory.getService(code, params);
		messageService.sendMessage();
	}
}
