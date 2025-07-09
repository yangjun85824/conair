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


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 字符集工具类
 *
 * @author L.cm
 */
public class Charsets {

	/**
	 * 字符集ISO-8859-1
	 */
	public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
	public static final String ISO_8859_1_NAME = ISO_8859_1.name();

	/**
	 * 字符集GBK
	 */
	public static final Charset GBK = Charset.forName(StringPool.GBK);
	public static final String GBK_NAME = GBK.name();

	/**
	 * 字符集utf-8
	 */
	public static final Charset UTF_8 = StandardCharsets.UTF_8;
	public static final String UTF_8_NAME = UTF_8.name();

	/**
	 * 转换为Charset对象
	 *
	 * @param charsetName 字符集，为空则返回默认字符集
	 * @return Charsets
	 * @throws UnsupportedCharsetException 编码不支持
	 */
	public static Charset charset(String charsetName) throws UnsupportedCharsetException {
		return StringUtil.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
	}

}
