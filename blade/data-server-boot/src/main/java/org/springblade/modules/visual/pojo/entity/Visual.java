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
package org.springblade.modules.visual.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;



/**
 * 可视化表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_visual")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "可视化表")
public class Visual extends BaseEntity {


	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "主键")
	private Long id;
	/**
	 * 大屏标题
	 */
	@Schema(description = "大屏标题")
	private String title;
	/**
	 * 大屏背景
	 */
	@Schema(description = "大屏背景")
	private String backgroundUrl;
	/**
	 * 大屏类型
	 */
	@Schema(description = "大屏类型")
	private Integer category;
	/**
	 * 发布密码
	 */
	@Schema(description = "发布密码")
	private String password;

	/**
	 * 创建部门
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "创建部门")
	private Long createDept;


}
