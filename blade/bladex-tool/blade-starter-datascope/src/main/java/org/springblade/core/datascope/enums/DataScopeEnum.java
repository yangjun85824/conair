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
package org.springblade.core.datascope.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 数据权限类型
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {
	/**
	 * 全部数据
	 */
	ALL(1, "全部"),

	/**
	 * 本人可见
	 */
	OWN(2, "本人可见"),

	/**
	 * 所在机构可见
	 */
	OWN_DEPT(3, "所在机构可见"),

	/**
	 * 所在机构及子级可见
	 */
	OWN_DEPT_CHILD(4, "所在机构及子级可见"),

	/**
	 * 自定义
	 */
	CUSTOM(5, "自定义");

	/**
	 * 类型
	 */
	private final int type;
	/**
	 * 描述
	 */
	private final String description;

	public static DataScopeEnum of(Integer dataScopeType) {
		return Optional.ofNullable(dataScopeType)
			.flatMap(type -> Arrays.stream(DataScopeEnum.values())
				.filter(scopeTypeEnum -> scopeTypeEnum.type == dataScopeType)
				.findFirst())
			.orElse(null);
	}
}
