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
 * 可视化数据集表 实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_visual_record")
@Schema(description = "可视化数据集表")
@EqualsAndHashCode(callSuper = true)
public class VisualRecord extends BaseEntity {


	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "主键")
	private Long id;
	/**
	 * 名称
	 */
	@Schema(description = "名称")
	private String name;
	/**
	 * 数据地址
	 */
	@Schema(description = "数据地址")
	private String url;
	/**
	 * 数据类型
	 */
	@Schema(description = "数据类型")
	private Integer dataType;
	/**
	 * 数据方法
	 */
	@Schema(description = "数据方法")
	private String dataMethod;
	/**
	 * 数据请求头
	 */
	@Schema(description = "数据请求头")
	private String dataHeader;
	/**
	 * 数据集
	 */
	@Schema(description = "数据集")
	private String data;
	/**
	 * 数据查询
	 */
	@Schema(description = "数据查询")
	private String dataQuery;
	/**
	 * 数据查询类型
	 */
	@Schema(description = "数据查询类型")
	private String dataQueryType;
	/**
	 * 数据格式化
	 */
	@Schema(description = "数据格式化")
	private String dataFormatter;
	/**
	 * ws地址
	 */
	@Schema(description = "ws地址")
	private String wsUrl;
	/**
	 * mqtt地址
	 */
	@Schema(description = "mqtt地址")
	private String mqttUrl;
	/**
	 * mqtt配置
	 */
	@Schema(description = "mqtt配置")
	private String mqttConfig;
	/**
	 * 创建部门
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "创建部门")
	private Long createDept;

}
