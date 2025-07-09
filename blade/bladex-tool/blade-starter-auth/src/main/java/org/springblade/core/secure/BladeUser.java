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
package org.springblade.core.secure;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springblade.core.tool.support.Kv;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户实体
 *
 * @author Chill
 */
@Data
@Hidden
public class BladeUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 客户端id
	 */
	@Schema(hidden = true)
	private String clientId;

	/**
	 * 用户id
	 */
	@Schema(hidden = true)
	private Long userId;
	/**
	 * 账号
	 */
	@Schema(hidden = true)
	private String account;
	/**
	 * 用户名
	 */
	@Schema(hidden = true)
	private String userName;
	/**
	 * 昵称
	 */
	@Schema(hidden = true)
	private String nickName;
	/**
	 * 租户ID
	 */
	@Schema(hidden = true)
	private String tenantId;
	/**
	 * 第三方认证ID
	 */
	@Schema(hidden = true)
	private String oauthId;
	/**
	 * 部门id
	 */
	@Schema(hidden = true)
	private String deptId;
	/**
	 * 岗位id
	 */
	@Schema(hidden = true)
	private String postId;
	/**
	 * 角色id
	 */
	@Schema(hidden = true)
	private String roleId;
	/**
	 * 角色名
	 */
	@Schema(hidden = true)
	private String roleName;
	/**
	 * 用户详情
	 */
	@Schema(hidden = true)
	private Kv detail;

}
