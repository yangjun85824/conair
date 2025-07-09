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
package org.springblade.core.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Sms枚举类
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum SmsEnum {

	/**
	 * yunpian
	 */
	YUNPIAN("yunpian", 1),

	/**
	 * qiniu
	 */
	QINIU("qiniu", 2),

	/**
	 * ali
	 */
	ALI("ali", 3),

	/**
	 * tencent
	 */
	TENCENT("tencent", 4),
	;

	/**
	 * 名称
	 */
	final String name;
	/**
	 * 类型
	 */
	final int category;

	/**
	 * 匹配枚举值
	 *
	 * @param name 名称
	 * @return OssEnum
	 */
	public static SmsEnum of(String name) {
		if (name == null) {
			return null;
		}
		SmsEnum[] values = SmsEnum.values();
		for (SmsEnum smsEnum : values) {
			if (smsEnum.name.equals(name)) {
				return smsEnum;
			}
		}
		return null;
	}

}
