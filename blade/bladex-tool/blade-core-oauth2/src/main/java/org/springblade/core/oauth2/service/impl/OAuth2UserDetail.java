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
package org.springblade.core.oauth2.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.tool.support.Kv;

import java.io.Serial;
import java.util.List;

/**
 * 用户详情
 *
 * @author BladeX
 */
@Data
@Schema(description = "oauth2用户实体类")
public class OAuth2UserDetail implements OAuth2User {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private String userId;
	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private String tenantId;
	/**
	 * 第三方认证ID
	 */
	@Schema(description = "第三方认证ID")
	private String oauthId;
	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String name;
	/**
	 * 真名
	 */
	@Schema(description = "真名")
	private String realName;
	/**
	 * 账号
	 */
	@Schema(description = "账号")
	private String account;
	/**
	 * 密码
	 */
	@JsonIgnore
	@Schema(description = "密码")
	private String password;
	/**
	 * 手机
	 */
	@Schema(description = "手机")
	private String phone;
	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	private String email;
	/**
	 * 部门id
	 */
	@Schema(description = "部门id")
	private String deptId;
	/**
	 * 岗位id
	 */
	@Schema(description = "岗位id")
	private String postId;
	/**
	 * 角色id
	 */
	@Schema(description = "角色id")
	private String roleId;
	/**
	 * 角色名
	 */
	@Schema(description = "角色名")
	private String roleName;
	/**
	 * 头像
	 */
	@Schema(description = "头像")
	private String avatar;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限集合")
	private List<String> permissions;

	/**
	 * 角色集合
	 */
	@Schema(description = "角色集合")
	private List<String> authorities;

	/**
	 * 客户端
	 */
	@Schema(description = "客户端")
	private OAuth2Client client;

	/**
	 * 用户详情
	 */
	@Schema(description = "用户详情")
	private Kv detail;
}
