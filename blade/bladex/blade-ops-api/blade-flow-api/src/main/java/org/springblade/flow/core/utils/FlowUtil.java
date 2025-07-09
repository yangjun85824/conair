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

import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.flow.core.constant.ProcessConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作流工具类
 *
 * @author Chill
 */
public class FlowUtil {

	/**
	 * 定义流程key对应的表名
	 */
	private final static Map<String, String> BUSINESS_TABLE = new HashMap<>();

	static {
		BUSINESS_TABLE.put(ProcessConstant.LEAVE_KEY, "blade_process_leave");
	}

	/**
	 * 通过流程key获取业务表名
	 *
	 * @param key 流程key
	 */
	public static String getBusinessTable(String key) {
		String businessTable = BUSINESS_TABLE.get(key);
		if (Func.isEmpty(businessTable)) {
			return StringPool.EMPTY;
		}
		return businessTable;
	}

	/**
	 * 获取业务标识
	 *
	 * @param businessTable 业务表
	 * @param businessId    业务表主键
	 * @return businessKey
	 */
	public static String getBusinessKey(String businessTable, String businessId) {
		return StringUtil.format("{}:{}", businessTable, businessId);
	}

}
