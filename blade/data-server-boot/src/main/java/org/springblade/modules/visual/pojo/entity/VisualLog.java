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


import java.io.Serializable;
import java.util.Date;

/**
 * 大屏日志实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_visual_log")
@Schema(description = "VisualLog对象")
public class VisualLog implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "主键")
	private Long id;
	/**
	 * 日志标题
	 */
	@Schema(description = "日志标题")
	private String title;
	/**
	 * 操作方式
	 */
	@Schema(description = "操作方式")
	private String method;
	/**
	 * 请求URI
	 */
	@Schema(description = "请求URI")
	private String requestUri;
	/**
	 * 用户代理
	 */
	@Schema(description = "用户代理")
	private String userAgent;
	/**
	 * 操作IP地址
	 */
	@Schema(description = "操作IP地址")
	private String remoteIp;
	/**
	 * 方法类
	 */
	@Schema(description = "方法类")
	private String methodClass;
	/**
	 * 方法名
	 */
	@Schema(description = "方法名")
	private String methodName;
	/**
	 * 操作提交的数据
	 */
	@Schema(description = "操作提交的数据")
	private String params;
	/**
	 * 执行时间
	 */
	@Schema(description = "执行时间")
	private String time;
	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private Date createTime;


}
