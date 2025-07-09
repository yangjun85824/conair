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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AuthInfo
 *
 * @author Chill
 */
@Data
@Schema(description = "认证信息")
public class AuthInfo {
	@Schema(description = "令牌")
	private String accessToken;
	@Schema(description = "令牌类型")
	private String tokenType;
	@Schema(description = "头像")
	private String avatar = "https://bladex.cn/images/logo.png";
	@Schema(description = "角色名")
	private String authority;
	@Schema(description = "用户名")
	private String userName;
	@Schema(description = "账号名")
	private String account;
	@Schema(description = "过期时间")
	private long expiresIn;
	@Schema(description = "许可证")
	private String license = "powered by bladex";
}
