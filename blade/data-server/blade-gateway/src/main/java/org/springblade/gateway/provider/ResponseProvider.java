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
package org.springblade.gateway.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应返回
 *
 * @author Chill
 */
public class ResponseProvider {

	/**
	 * 成功
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> success(String message) {
		return response(200, message);
	}

	/**
	 * 失败
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> fail(String message) {
		return response(400, message);
	}

	/**
	 * 未授权
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> unAuth(String message) {
		return response(401, message);
	}

	/**
	 * 服务器异常
	 *
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> error(String message) {
		return response(500, message);
	}

	/**
	 * 构建返回的JSON数据格式
	 *
	 * @param status  状态码
	 * @param message 信息
	 * @return
	 */
	public static Map<String, Object> response(int status, String message) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("code", status);
		map.put("msg", message);
		map.put("data", null);
		return map;
	}

}
