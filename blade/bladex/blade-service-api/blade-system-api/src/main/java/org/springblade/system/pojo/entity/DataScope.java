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
package org.springblade.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_scope_data")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "DataScope对象")
public class DataScope extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单主键
	 */
	@Schema(description = "菜单主键")
	private Long menuId;
	/**
	 * 资源编号
	 */
	@Schema(description = "资源编号")
	private String resourceCode;
	/**
	 * 数据权限名称
	 */
	@Schema(description = "数据权限名称")
	private String scopeName;
	/**
	 * 数据权限可见字段
	 */
	@Schema(description = "数据权限可见字段")
	private String scopeField;
	/**
	 * 数据权限类名
	 */
	@Schema(description = "数据权限类名")
	private String scopeClass;
	/**
	 * 数据权限字段
	 */
	@Schema(description = "数据权限字段")
	private String scopeColumn;
	/**
	 * 数据权限类型
	 */
	@Schema(description = "数据权限类型")
	private Integer scopeType;
	/**
	 * 数据权限值域
	 */
	@Schema(description = "数据权限值域")
	private String scopeValue;
	/**
	 * 数据权限备注
	 */
	@Schema(description = "数据权限备注")
	private String remark;


}
