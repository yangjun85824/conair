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
package org.springblade.flow.core.utils;

import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;

import static org.springblade.core.launch.constant.FlowConstant.TASK_USR_PREFIX;

/**
 * 工作流任务工具类
 *
 * @author Chill
 */
public class TaskUtil {

	/**
	 * 获取任务用户格式
	 *
	 * @return taskUser
	 */
	public static String getTaskUser() {
		return StringUtil.format("{}{}", TASK_USR_PREFIX, AuthUtil.getUserId());
	}

	/**
	 * 获取任务用户格式
	 *
	 * @param userId 用户id
	 * @return taskUser
	 */
	public static String getTaskUser(String userId) {
		return StringUtil.format("{}{}", TASK_USR_PREFIX, userId);
	}


	/**
	 * 获取用户主键
	 *
	 * @param taskUser 任务用户
	 * @return userId
	 */
	public static Long getUserId(String taskUser) {
		return Func.toLong(StringUtil.removePrefix(taskUser, TASK_USR_PREFIX));
	}

	/**
	 * 获取用户组格式
	 *
	 * @return candidateGroup
	 */
	public static String getCandidateGroup() {
		return AuthUtil.getUserRole();
	}

}
