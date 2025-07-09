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
package org.springblade.core.tool.utils;


import org.springblade.core.tool.support.Kv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模版解析工具类
 */
public class TemplateUtil {

	/**
	 * 支持 ${} 与 #{} 两种模版占位符
	 */
	private static final Pattern pattern = Pattern.compile("\\$\\{([^{}]+)}|\\#\\{([^{}]+)}");

	/**
	 * 解析模版
	 *
	 * @param template 模版
	 * @param params   参数
	 * @return 解析后的字符串
	 */
	public static String process(String template, Kv params) {
		Matcher matcher = pattern.matcher(template);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String key = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
			String replacement = params.getStr(key);
			if (replacement == null) {
				throw new IllegalArgumentException("参数中缺少必要的键: " + key);
			}
			matcher.appendReplacement(sb, replacement);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 解析模版
	 *
	 * @param template 模版
	 * @param params   参数
	 * @return 解析后的字符串
	 */
	public static String safeProcess(String template, Kv params) {
		Matcher matcher = pattern.matcher(template);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String key = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
			String replacement = params.getStr(key);
			if (replacement != null) {
				matcher.appendReplacement(sb, replacement);
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
