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
package org.springblade.resource.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springblade.core.tool.utils.StringPool;

/**
 * Sms资源编码枚举类
 *
 * @author Chill
 * @apiNote 该枚举类对应短信配置模块的资源编码，可根据业务需求自行拓展
 */
@Getter
@AllArgsConstructor
public enum SmsCodeEnum {

	/**
	 * 默认编号
	 */
	DEFAULT(StringPool.EMPTY, 1),

	/**
	 * 验证码编号
	 */
	VALIDATE("validate", 2),

	/**
	 * 通知公告编号
	 */
	NOTICE("notice", 3),

	/**
	 * 下单通知编号
	 */
	ORDER("order", 4),

	/**
	 * 会议通知编号
	 */
	MEETING("meeting", 5),
	;

	final String name;
	final int category;

}
