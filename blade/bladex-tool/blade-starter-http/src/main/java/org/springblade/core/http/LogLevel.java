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
package org.springblade.core.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 日志级别
 *
 * @author L.cm
 */
@Getter
@AllArgsConstructor
public enum LogLevel {
	/**
	 * No logs.
	 */
	NONE(HttpLoggingInterceptor.Level.NONE),
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
	BASIC(HttpLoggingInterceptor.Level.BASIC),
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
	HEADERS(HttpLoggingInterceptor.Level.HEADERS),
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
	BODY(HttpLoggingInterceptor.Level.BODY);

	private final HttpLoggingInterceptor.Level level;
}
