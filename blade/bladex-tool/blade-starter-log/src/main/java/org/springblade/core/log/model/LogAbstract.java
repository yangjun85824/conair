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
package org.springblade.core.log.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * logApi、logError、logUsual父类
 *
 * @author Chill
 */
@Data
public class LogAbstract implements Serializable {

	protected static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	protected Long id;
	/**
	 * 租户ID
	 */
	private String tenantId;
	/**
	 * 服务ID
	 */
	protected String serviceId;
	/**
	 * 服务器 ip
	 */
	protected String serverIp;
	/**
	 * 服务器名
	 */
	protected String serverHost;
	/**
	 * 环境
	 */
	protected String env;
	/**
	 * 操作IP地址
	 */
	protected String remoteIp;
	/**
	 * 用户代理
	 */
	protected String userAgent;
	/**
	 * 请求URI
	 */
	protected String requestUri;
	/**
	 * 操作方式
	 */
	protected String method;
	/**
	 * 方法类
	 */
	protected String methodClass;
	/**
	 * 方法名
	 */
	protected String methodName;
	/**
	 * 操作提交的数据
	 */
	protected String params;
	/**
	 * 创建人
	 */
	protected String createBy;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	protected Date createTime;

}
