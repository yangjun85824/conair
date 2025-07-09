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
package org.springblade.core.mp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * StatusType枚举类
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum StatusType {

	/**
	 * 禁用
	 */
	DISABLED("disabled", -1),

	/**
	 * 未激活
	 */
	IN_ACTIVE("inactive", 0),

	/**
	 * 已激活
	 */
	ACTIVE("active", 1),
	;

	/**
	 * 名称
	 */
	final String name;
	/**
	 * 类型
	 */
	final int type;

	/**
	 * 匹配枚举值
	 *
	 * @param type 类型
	 * @return StatusType
	 */
	public static StatusType of(Integer type) {
		if (type == null) {
			return null;
		}
		StatusType[] values = StatusType.values();
		for (StatusType statusType : values) {
			if (statusType.type == type) {
				return statusType;
			}
		}
		return null;
	}

}
