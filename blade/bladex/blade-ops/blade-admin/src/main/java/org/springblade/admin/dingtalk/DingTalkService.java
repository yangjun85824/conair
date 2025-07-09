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
 * Author: DreamLu (596392912@qq.com)
 */
package org.springblade.admin.dingtalk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉 服务
 *
 * @author L.cm
 */
@Slf4j
@RequiredArgsConstructor
public class DingTalkService {
	private static final String DING_TALK_ROBOT_URL = "https://oapi.dingtalk.com/robot/send?access_token=";
	private final MonitorProperties properties;
	private final WebClient webClient;

	/**
	 * 发送消息
	 *
	 * @param title title
	 * @param text  消息
	 */
	public Mono<Void> pushMsg(String title, String text) {
		log.info("钉钉消息：[创建消息体]title:{}, text:{}", title, text);

		HashMap<String, String> params = new HashMap<>(2);
		params.put("title", title);
		params.put("text", text);

		Map<String, Object> body = new HashMap<>(2);
		body.put("msgtype", "markdown");
		body.put("markdown", params);
		log.info("创建消息体 json：{}", body);

		MonitorProperties.DingTalk dingTalk = properties.getDingTalk();
		String accessToken = dingTalk.getAccessToken();
		if (!StringUtils.hasText(accessToken)) {
			log.error("DingTalk alert config accessToken ${monitor.ding-talk.access-token} is blank.");
			return Mono.empty();
		}

		String urlString = DING_TALK_ROBOT_URL + dingTalk.getAccessToken();
		// 有私钥要签名
		String secret = dingTalk.getSecret();
		if (StringUtils.hasText(secret)) {
			long timestamp = System.currentTimeMillis();
			urlString += String.format("&timestamp=%s&sign=%s", timestamp, getSign(secret, timestamp));
		}
		return webClient.post()
			.uri(URI.create(urlString))
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(body))
			.retrieve()
			.bodyToMono(String.class)
			.doOnSuccess((result) -> log.info("钉钉消息：[消息返回]result:{}", result))
			.then();
	}

	private static String getSign(String secret, long timestamp) {
		String stringToSign = timestamp + "\n" + secret;
		byte[] hmacSha256Bytes = digestHmac(stringToSign, secret);
		return UriUtils.encode(Base64.getEncoder().encodeToString(hmacSha256Bytes), StandardCharsets.UTF_8);
	}

	public static byte[] digestHmac(String data, String key) {
		SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		try {
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
