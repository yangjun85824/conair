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
 * 短信配置表实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_sms")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "短信配置表")
public class Sms extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 资源编号
	 */
	@Schema(description = "资源编号")
	private String smsCode;

	/**
	 * 模板ID
	 */
	@Schema(description = "模板ID")
	private String templateId;
	/**
	 * 分类
	 */
	@Schema(description = "分类")
	private Integer category;
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
	 * regionId
	 */
	@Schema(description = "regionId")
	private String regionId;
	/**
	 * 短信签名
	 */
	@Schema(description = "短信签名")
	private String signName;
	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;


}
