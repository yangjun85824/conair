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
package org.springblade.resource.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * 实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_oss")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Oss对象")
public class Oss extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 所属分类
	 */
	@Schema(description = "所属分类")
	private Integer category;

	/**
	 * 资源编号
	 */
	@Schema(description = "资源编号")
	private String ossCode;

	/**
	 * 资源地址
	 */
	@Schema(description = "资源地址")
	private String endpoint;

	/**
	 * 外网资源地址
	 */
	@Schema(description = "外网资源地址")
	private String transformEndpoint;
	/**
	 * accessKey
	 */
	@Schema(description = "accessKey")
	private String accessKey;
	/**
	 * secretKey
	 */
	@Schema(description = "secretKey")
	private String secretKey;
	/**
	 * 空间名
	 */
	@Schema(description = "空间名")
	private String bucketName;
	/**
	 * 应用ID TencentCOS需要
	 */
	@Schema(description = "应用ID")
	private String appId;
	/**
	 * 地域简称 TencentCOS需要
	 */
	@Schema(description = "地域简称")
	private String region;
	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;


}
