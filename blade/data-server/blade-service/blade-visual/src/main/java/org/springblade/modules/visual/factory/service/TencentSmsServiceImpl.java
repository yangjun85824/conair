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

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 腾讯云短信消息推送
 *
 * @author Chill
 */
@AllArgsConstructor
public class TencentSmsServiceImpl implements MessageService {

	private static final int SUCCESS = 0;
	private static final String NATION_CODE = "86";

	/**
	 * 消息渠道
	 */
	private VisualPushChannel channel;
	/**
	 * 模版id
	 */
	private String template;
	/**
	 * 消息参数
	 */
	private Kv params;

	@Override
	public void sendMessage() {
		// 获取短信配置
		String appId = channel.getAppId();
		String appKey = channel.getAppKey();
		String signName = channel.getSmsSign();
		String phones = params.getStr("phones");

		SmsMultiSender smsSender = new SmsMultiSender(Func.toInt(appId), appKey);
		try {

			Collection<String> values = params.values().stream()
				.map(obj -> obj != null ? obj.toString() : null) // 将每个对象转换为字符串，对于 null 值保持为 null
				.collect(Collectors.toList());
			String[] params = StringUtil.toStringArray(values);
			SmsMultiSenderResult senderResult = smsSender.sendWithParam(
				NATION_CODE,
				StringUtil.split(phones, StringPool.COMMA),
				Func.toInt(template),
				params,
				signName,
				StringPool.EMPTY, StringPool.EMPTY
			);
			if (senderResult.result != SUCCESS) {
				throw new ServiceException(
					String.format(
						"腾讯云短信消息推送失败，appId：%s，appKey：%s，templateId：%s，signName：%s，phones：%s"
						, appId, appKey, template, signName, phones
					)
				);
			}
		} catch (HTTPException | IOException e) {
			throw new ServiceException(String.format("腾讯云短信消息推送失败: %s", e.getMessage()));
		}

	}
}
