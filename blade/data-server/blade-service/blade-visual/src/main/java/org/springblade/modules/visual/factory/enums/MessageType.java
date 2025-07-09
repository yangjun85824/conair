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
package org.springblade.modules.visual.factory.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springblade.core.tool.utils.StringPool;

/**
 * 消息服务枚举
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum MessageType {
	/**
	 * 企业微信
	 */
	WECHAT_WORK("企业微信", 1),
	/**
	 * 钉钉
	 */
	DING_TALK("钉钉", 2),
	/**
	 * 邮件
	 */
	EMAIL("邮件", 3),

	/**
	 * 阿里云短信
	 */
	ALI_SMS("阿里云短信", 4),

	/**
	 * 腾讯云短信
	 */
	TENCENT_SMS("腾讯云短信", 5),
	;

	final String name;
	final int type;

	/**
	 * 匹配枚举值
	 *
	 * @param type 类型
	 * @return MessageType
	 */
	public static MessageType of(Integer type) {
		MessageType[] values = MessageType.values();
		for (MessageType messageType : values) {
			if (messageType.type == type) {
				return messageType;
			}
		}
		return null;
	}

	/**
	 * 匹配枚举值
	 *
	 * @param type 类型
	 * @return String
	 */
	public static String ofName(Integer type) {
		MessageType[] values = MessageType.values();
		for (MessageType messageType : values) {
			if (messageType.type == type) {
				return messageType.name;
			}
		}
		return StringPool.EMPTY;
	}
}
