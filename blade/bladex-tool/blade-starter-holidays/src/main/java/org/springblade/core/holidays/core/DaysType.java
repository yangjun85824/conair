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

package org.springblade.core.holidays.core;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 日期类型，工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2；
 *
 * @author L.cm
 */
@Getter
@RequiredArgsConstructor
public enum DaysType {

	/**
	 * 工作日
	 */
	WEEKDAYS((byte) 0),
	/**
	 * 休息日
	 */
	REST_DAYS((byte) 1),
	/**
	 * 节假日
	 */
	HOLIDAYS((byte) 2);

	@JsonValue
	private final byte type;

	/**
	 * 将 type 转换成枚举
	 *
	 * @param type type
	 * @return DaysType
	 */
	public static DaysType from(byte type) {
		switch (type) {
			case 0:
				return WEEKDAYS;
			case 1:
				return REST_DAYS;
			case 2:
				return HOLIDAYS;
			default:
				throw new IllegalArgumentException("未知的 DaysType:" + type);
		}
	}

}
