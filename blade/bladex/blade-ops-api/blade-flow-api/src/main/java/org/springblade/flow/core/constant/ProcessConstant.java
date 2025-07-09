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
package org.springblade.flow.core.constant;

/**
 * 流程常量.
 *
 * @author Chill
 */
public interface ProcessConstant {

	/**
	 * 请假流程标识
	 */
	String LEAVE_KEY = "Leave";

	/**
	 * 报销流程标识
	 */
	String EXPENSE_KEY = "Expense";

	/**
	 * 同意标识
	 */
	String PASS_KEY = "pass";

	/**
	 * 同意代号
	 */
	String PASS_ALIAS = "ok";

	/**
	 * 同意默认批复
	 */
	String PASS_COMMENT = "同意";

	/**
	 * 驳回默认批复
	 */
	String NOT_PASS_COMMENT = "驳回";

	/**
	 * 创建人变量名
	 */
	String TASK_VARIABLE_CREATE_USER = "createUser";

}
