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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springblade.core.tool.support.Kv;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author Chill
 */
@Data
@Schema(description = "用户信息")
public class UserInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 第三方授权id
	 */
	@Schema(description = "第三方授权id")
	private String oauthId;

	/**
	 * 用户基础信息
	 */
	@Schema(description = "用户")
	private User user;

	/**
	 * 拓展信息
	 */
	@Schema(description = "拓展信息")
	private Kv detail;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限集合")
	private List<String> permissions;

	/**
	 * 角色集合
	 */
	@Schema(description = "角色集合")
	private List<String> roles;

}
