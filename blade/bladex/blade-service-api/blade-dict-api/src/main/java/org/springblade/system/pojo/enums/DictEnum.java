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
package org.springblade.system.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统字典枚举类
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum DictEnum {

	/**
	 * 性别
	 */
	SEX("sex"),
	/**
	 * 通知类型
	 */
	NOTICE("notice"),
	/**
	 * 菜单类型
	 */
	MENU_CATEGORY("menu_category"),
	/**
	 * 按钮功能
	 */
	BUTTON_FUNC("button_func"),
	/**
	 * 是否
	 */
	YES_NO("yes_no"),
	/**
	 * 流程类型
	 */
	FLOW("flow"),
	/**
	 * 机构类型
	 */
	ORG_CATEGORY("org_category"),
	/**
	 * 数据权限
	 */
	DATA_SCOPE_TYPE("data_scope_type"),
	/**
	 * 接口权限
	 */
	API_SCOPE_TYPE("api_scope_type"),
	/**
	 * 权限类型
	 */
	SCOPE_CATEGORY("scope_category"),
	/**
	 * 对象存储类型
	 */
	OSS("oss"),
	/**
	 * 短信服务类型
	 */
	SMS("sms"),
	/**
	 * 岗位类型
	 */
	POST_CATEGORY("post_category"),
	/**
	 * 行政区划
	 */
	REGION("region"),
	/**
	 * 用户平台
	 */
	USER_TYPE("user_type"),
	;

	final String name;

}
