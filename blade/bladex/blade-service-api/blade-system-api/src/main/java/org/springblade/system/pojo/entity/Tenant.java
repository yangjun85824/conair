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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_tenant")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Tenant对象")
public class Tenant extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private String tenantId;
	/**
	 * 租户名称
	 */
	@Schema(description = "租户名称")
	private String tenantName;
	/**
	 * 域名地址
	 */
	@Schema(description = "域名地址")
	private String domainUrl;
	/**
	 * 系统背景
	 */
	@Schema(description = "系统背景")
	private String backgroundUrl;
	/**
	 * 联系人
	 */
	@Schema(description = "联系人")
	private String linkman;
	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	private String contactNumber;
	/**
	 * 联系地址
	 */
	@Schema(description = "联系地址")
	private String address;
	/**
	 * 账号额度
	 */
	@Schema(description = "账号额度")
	private Integer accountNumber;
	/**
	 * 过期时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@Schema(description = "过期时间")
	private Date expireTime;
	/**
	 * 产品包ID
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@Schema(description = "产品包ID")
	private Long packageId;
	/**
	 * 数据源ID
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@Schema(description = "数据源ID")
	private Long datasourceId;
	/**
	 * 授权码
	 */
	@Schema(description = "授权码")
	private String licenseKey;


}
