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
package org.springblade.core.log.utils;

import org.slf4j.MDC;
import org.springblade.core.tool.utils.StringUtil;

/**
 * 日志追踪工具类
 *
 * @author Chill
 */
public class LogTraceUtil {
	private static final String UNIQUE_ID = "traceId";

	/**
	 * 获取日志追踪id格式
	 */
	public static String getTraceId() {
		return StringUtil.randomUUID();
	}

	/**
	 * 插入traceId
	 */
	public static boolean insert() {
		MDC.put(UNIQUE_ID, getTraceId());
		return true;
	}

	/**
	 * 移除traceId
	 */
	public static boolean remove() {
		MDC.remove(UNIQUE_ID);
		return true;
	}

}
