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
 * 附件表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_attach")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "附件表")
public class Attach extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 附件地址
	 */
	@Schema(description = "附件地址")
	private String link;
	/**
	 * 附件域名
	 */
	@Schema(description = "附件域名")
	private String domainUrl;
	/**
	 * 附件名称
	 */
	@Schema(description = "附件名称")
	private String name;
	/**
	 * 附件原名
	 */
	@Schema(description = "附件原名")
	private String originalName;
	/**
	 * 附件拓展名
	 */
	@Schema(description = "附件拓展名")
	private String extension;
	/**
	 * 附件大小
	 */
	@Schema(description = "附件大小")
	private Long attachSize;


}
