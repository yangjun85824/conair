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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 行政区划表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_region")
@Schema(description = "行政区划表")
public class Region implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 区划编号
	 */
	@TableId(value = "code", type = IdType.INPUT)
	@Schema(description = "区划编号")
	private String code;
	/**
	 * 父区划编号
	 */
	@Schema(description = "父区划编号")
	private String parentCode;
	/**
	 * 祖区划编号
	 */
	@Schema(description = "祖区划编号")
	private String ancestors;
	/**
	 * 区划名称
	 */
	@Schema(description = "区划名称")
	private String name;
	/**
	 * 省级区划编号
	 */
	@Schema(description = "省级区划编号")
	private String provinceCode;
	/**
	 * 省级名称
	 */
	@Schema(description = "省级名称")
	private String provinceName;
	/**
	 * 市级区划编号
	 */
	@Schema(description = "市级区划编号")
	private String cityCode;
	/**
	 * 市级名称
	 */
	@Schema(description = "市级名称")
	private String cityName;
	/**
	 * 区级区划编号
	 */
	@Schema(description = "区级区划编号")
	private String districtCode;
	/**
	 * 区级名称
	 */
	@Schema(description = "区级名称")
	private String districtName;
	/**
	 * 镇级区划编号
	 */
	@Schema(description = "镇级区划编号")
	private String townCode;
	/**
	 * 镇级名称
	 */
	@Schema(description = "镇级名称")
	private String townName;
	/**
	 * 村级区划编号
	 */
	@Schema(description = "村级区划编号")
	private String villageCode;
	/**
	 * 村级名称
	 */
	@Schema(description = "村级名称")
	private String villageName;
	/**
	 * 层级
	 */
	@Schema(description = "层级")
	private Integer regionLevel;
	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;
	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;


}
