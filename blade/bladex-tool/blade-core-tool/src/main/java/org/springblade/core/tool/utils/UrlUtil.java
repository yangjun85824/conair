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
package org.springblade.core.tool.utils;

import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

/**
 * url处理工具类
 *
 * @author L.cm
 */
public class UrlUtil extends org.springframework.web.util.UriUtils {

	/**
	 * url 编码
	 *
	 * @param source source
	 * @return sourced String
	 */
	public static String encode(String source) {
		return encode(source, Charsets.UTF_8);
	}

	/**
	 * url 解码
	 *
	 * @param source source
	 * @return decoded String
	 */
	public static String decode(String source) {
		return StringUtils.uriDecode(source, Charsets.UTF_8);
	}

	/**
	 * url 编码
	 *
	 * @param source  url
	 * @param charset 字符集
	 * @return 编码后的url
	 */
	@Deprecated
	public static String encodeURL(String source, Charset charset) {
		return encode(source, charset.name());
	}

	/**
	 * url 解码
	 *
	 * @param source  url
	 * @param charset 字符集
	 * @return 解码url
	 */
	@Deprecated
	public static String decodeURL(String source, Charset charset) {
		return StringUtils.uriDecode(source, charset);
	}

	/**
	 * 获取url路径
	 *
	 * @param uriStr 路径
	 * @return url路径
	 */
	public static String getPath(String uriStr) {
		URI uri;

		try {
			uri = new URI(uriStr);
		} catch (URISyntaxException var3) {
			throw new RuntimeException(var3);
		}

		return uri.getPath();
	}

}
