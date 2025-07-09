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
package org.springblade.core.launch.constant;

/**
 * Token配置常量.
 *
 * @author Chill
 */
public interface TokenConstant {

	String AVATAR = "avatar";
	String BEARER = "bearer";
	String AUTH_HEADER = "Blade-Auth";
	String SECURE_HEADER = "Blade-Requested-With";
	String SECURE_HEADER_VALUE = "BladeHttpRequest";
	String ACCESS_TOKEN = "access_token";
	String REFRESH_TOKEN = "refresh_token";
	String CLIENT_ACCESS_TOKEN = "client_access_token";
	String IMPLICIT_ACCESS_TOKEN = "implicit_access_token";
	String TOKEN_TYPE = "token_type";
	String EXPIRES_IN = "expires_in";
	String ACCOUNT = "account";
	String USER_NAME = "user_name";
	String NICK_NAME = "nick_name";
	String REAL_NAME = "real_name";
	String USER_ID = "user_id";
	String DEPT_ID = "dept_id";
	String POST_ID = "post_id";
	String ROLE_ID = "role_id";
	String ROLE_NAME = "role_name";
	String TENANT_ID = "tenant_id";
	String OAUTH_ID = "oauth_id";
	String CLIENT_ID = "client_id";
	String DETAIL = "detail";
	String LICENSE = "license";
	String LICENSE_NAME = "powered by bladex";
	String DEFAULT_AVATAR = "https://bladex.cn/images/logo.png";
	Integer AUTH_LENGTH = 7;

}
