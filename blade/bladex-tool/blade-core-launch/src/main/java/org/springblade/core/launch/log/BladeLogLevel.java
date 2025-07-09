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
 * Author: DreamLu (596392912@qq.com)
 */

package org.springblade.core.launch.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 请求日志级别，来源 okHttp
 *
 * @author L.cm
 */
@Getter
@RequiredArgsConstructor
public enum BladeLogLevel {
	/**
	 * No logs.
	 */
	NONE(0),

	/**
	 * Logs request and response lines.
	 *
	 * <p>Example:
	 * <pre>{@code
	 * --> POST /greeting http/1.1 (3-byte body)
	 *
	 * <-- 200 OK (22ms, 6-byte body)
	 * }</pre>
	 */
	BASIC(1),

	/**
	 * Logs request and response lines and their respective headers.
	 *
	 * <p>Example:
	 * <pre>{@code
	 * --> POST /greeting http/1.1
	 * Host: example.com
	 * Content-Type: plain/text
	 * Content-Length: 3
	 * --> END POST
	 *
	 * <-- 200 OK (22ms)
	 * Content-Type: plain/text
	 * Content-Length: 6
	 * <-- END HTTP
	 * }</pre>
	 */
	HEADERS(2),

	/**
	 * Logs request and response lines and their respective headers and bodies (if present).
	 *
	 * <p>Example:
	 * <pre>{@code
	 * --> POST /greeting http/1.1
	 * Host: example.com
	 * Content-Type: plain/text
	 * Content-Length: 3
	 *
	 * Hi?
	 * --> END POST
	 *
	 * <-- 200 OK (22ms)
	 * Content-Type: plain/text
	 * Content-Length: 6
	 *
	 * Hello!
	 * <-- END HTTP
	 * }</pre>
	 */
	BODY(3);

	/**
	 * 请求日志配置前缀
	 */
	public static final String REQ_LOG_PROPS_PREFIX = "blade.log.request";
	/**
	 * 控制台日志是否启用
	 */
	public static final String CONSOLE_LOG_ENABLED_PROP = "blade.log.console.enabled";

	/**
	 * 级别
	 */
	private final int level;

	/**
	 * 当前版本 小于和等于 比较的版本
	 *
	 * @param level LogLevel
	 * @return 是否小于和等于
	 */
	public boolean lte(BladeLogLevel level) {
		return this.level <= level.level;
	}

}
