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
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_client")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Client对象")
public class AuthClient extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 客户端id
	 */
	@Schema(description = "客户端id")
	private String clientId;
	/**
	 * 客户端密钥
	 */
	@Schema(description = "客户端密钥")
	private String clientSecret;
	/**
	 * 资源集合
	 */
	@Schema(description = "资源集合")
	private String resourceIds;
	/**
	 * 授权范围
	 */
	@Schema(description = "授权范围")
	private String scope;
	/**
	 * 授权类型
	 */
	@Schema(description = "授权类型")
	private String authorizedGrantTypes;
	/**
	 * 回调地址
	 */
	@Schema(description = "回调地址")
	private String webServerRedirectUri;
	/**
	 * 权限
	 */
	@Schema(description = "权限")
	private String authorities;
	/**
	 * 令牌过期秒数
	 */
	@Schema(description = "令牌过期秒数")
	private Integer accessTokenValidity;
	/**
	 * 刷新令牌过期秒数
	 */
	@Schema(description = "刷新令牌过期秒数")
	private Integer refreshTokenValidity;
	/**
	 * 附加说明
	 */
	@JsonIgnore
	@Schema(description = "附加说明")
	private String additionalInformation;
	/**
	 * 自动授权
	 */
	@Schema(description = "自动授权")
	private String autoapprove;


}
