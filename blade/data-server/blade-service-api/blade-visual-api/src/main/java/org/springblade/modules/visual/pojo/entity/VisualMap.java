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
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 可视化地图配置表实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_visual_map")
@Schema(description = "可视化地图配置表")
public class VisualMap implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 父主键
	 */
	@Schema(description = "父主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/**
	 * 地图编号
	 */
	@Schema(description = "地图编号")
	private String code;
	/**
	 * 地图父编号
	 */
	@Schema(description = "地图父编号")
	private String parentCode;
	/**
	 * 地图祖编号
	 */
	@Schema(description = "地图祖编号")
	private String ancestors;
	/**
	 * 地图名称
	 */
	@Schema(description = "地图名称")
	private String name;
	/**
	 * 地图层级
	 */
	@Schema(description = "地图层级")
	private Integer level;
	/**
	 * 地图数据
	 */
	@Schema(description = "地图数据")
	private String data;

	/**
	 * 状态[0:未删除,1:删除]
	 */
	@TableLogic
	@Schema(description = "是否已删除", hidden = true)
	private Integer isDeleted;


}
