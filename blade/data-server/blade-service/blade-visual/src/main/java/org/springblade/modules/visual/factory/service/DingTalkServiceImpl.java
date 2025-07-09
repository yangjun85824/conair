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
import lombok.SneakyThrows;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.OkHttpUtil;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 钉钉消息推送
 *
 * @author Chill
 */
@AllArgsConstructor
public class DingTalkServiceImpl implements MessageService {
	/**
	 * 消息渠道
	 */
	private VisualPushChannel channel;
	/**
	 * 消息模版
	 */
	private String message;

	@Override
	@SneakyThrows
	public void sendMessage() {
		// 生成签名
		long timestamp = System.currentTimeMillis();
		String sign = generateSign(timestamp, channel.getRobotSign());

		// 构建最终的请求URL
		String signedUrl = String.format("%s&timestamp=%d&sign=%s", channel.getWebhook(), timestamp, URLEncoder.encode(sign, "UTF-8"));

		// 构建消息体
		String json = String.format("{\"msgtype\": \"text\", \"text\": {\"content\": \"%s\"}}", message);

		// 使用OkHttpUtil发送POST请求
		String response = OkHttpUtil.postJson(signedUrl, json);

		if (response == null || response.isEmpty()) {
			throw new ServiceException(String.format("钉钉消息推送失败，webhook：%s，secret：%s，消息：%s", channel.getWebhook(), channel.getRobotSign(), message));
		}
	}

	/**
	 * 生成签名
	 *
	 * @param timestamp 当前时间戳
	 * @param secret    提供的密钥
	 * @return 生成的签名字符串
	 */
	private String generateSign(long timestamp, String secret) throws Exception {
		String stringToSign = timestamp + "\n" + secret;
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
		byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(signData);
	}
}
