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
package org.springblade.core.datascope.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springblade.core.datascope.constant.DataScopeConstant;
import org.springblade.core.datascope.enums.DataScopeEnum;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据权限实体类
 *
 * @author Chill
 */
@Data
@NoArgsConstructor
public class DataScopeModel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 构造器创建
	 */
	public DataScopeModel(Boolean searched) {
		this.searched = searched;
	}

	/**
	 * 是否已查询
	 */
	private Boolean searched = Boolean.FALSE;
	/**
	 * 资源编号
	 */
	private String resourceCode;
	/**
	 * 数据权限字段
	 */
	private String scopeColumn = DataScopeConstant.DEFAULT_COLUMN;
	/**
	 * 数据权限规则
	 */
	private Integer scopeType = DataScopeEnum.ALL.getType();
	/**
	 * 可见字段
	 */
	private String scopeField;
	/**
	 * 数据权限规则值
	 */
	private String scopeValue;
}
