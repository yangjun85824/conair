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

import org.springframework.lang.Nullable;

/**
 * 版本号比较工具
 * <p>
 * 思路来源于： https://github.com/hotoo/versioning/blob/master/versioning.js
 * <p>
 * example
 * * ##完整模式
 * Version.of("v0.1.1").eq("v0.1.2"); // false
 * <p>
 * ##不完整模式
 * Version.of("v0.1").incomplete().eq("v0.1.2");   // true
 *
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date 2015年7月9日下午10:48:39
 */
public class Version {
	private static final String DELIMITER = "\\.";

	/**
	 * 版本号
	 */
	@Nullable
	private String version;
	/**
	 * 是否完整模式，默认使用完整模式
	 */
	private boolean complete = true;

	/**
	 * 私有实例化构造方法
	 */
	private Version() {
	}

	private Version(@Nullable String version) {
		this.version = version;
	}

	/**
	 * 不完整模式
	 *
	 * @return {Version}
	 */
	public Version incomplete() {
		this.complete = false;
		return this;
	}

	/**
	 * 构造器
	 *
	 * @param version 版本
	 * @return {Version}
	 */
	public static Version of(@Nullable String version) {
		return new Version(version);
	}

	/**
	 * 比较版本号是否相同
	 * <p>
	 * example:
	 * * Version.of("v0.3").eq("v0.4")
	 *
	 * @param version 字符串版本号
	 * @return {boolean}
	 */
	public boolean eq(@Nullable String version) {
		return compare(version) == 0;
	}

	/**
	 * 不相同
	 * <p>
	 * example:
	 * * Version.of("v0.3").ne("v0.4")
	 *
	 * @param version 字符串版本号
	 * @return {boolean}
	 */
	public boolean ne(@Nullable String version) {
		return compare(version) != 0;
	}

	/**
	 * 大于
	 *
	 * @param version 版本号
	 * @return 是否大于
	 */
	public boolean gt(@Nullable String version) {
		return compare(version) > 0;
	}

	/**
	 * 大于和等于
	 *
	 * @param version 版本号
	 * @return 是否大于和等于
	 */
	public boolean gte(@Nullable String version) {
		return compare(version) >= 0;
	}

	/**
	 * 小于
	 *
	 * @param version 版本号
	 * @return 是否小于
	 */
	public boolean lt(@Nullable String version) {
		return compare(version) < 0;
	}

	/**
	 * 小于和等于
	 *
	 * @param version 版本号
	 * @return 是否小于和等于
	 */
	public boolean lte(@Nullable String version) {
		return compare(version) <= 0;
	}

	/**
	 * 和另外一个版本号比较
	 *
	 * @param version 版本号
	 * @return {int}
	 */
	private int compare(@Nullable String version) {
		return Version.compare(this.version, version, complete);
	}

	/**
	 * 比较2个版本号
	 *
	 * @param v1       v1
	 * @param v2       v2
	 * @param complete 是否完整的比较两个版本
	 * @return (v1 < v2) ? -1 : ((v1 == v2) ? 0 : 1)
	 */
	private static int compare(@Nullable String v1, @Nullable String v2, boolean complete) {
		// v1 null视为最小版本，排在前
		if (v1 == v2) {
			return 0;
		} else if (v1 == null) {
			return -1;
		} else if (v2 == null) {
			return 1;
		}
		// 去除空格
		v1 = v1.trim();
		v2 = v2.trim();
		if (v1.equals(v2)) {
			return 0;
		}
		String[] v1s = v1.split(DELIMITER);
		String[] v2s = v2.split(DELIMITER);
		int v1sLen = v1s.length;
		int v2sLen = v2s.length;
		int len = complete
			? Math.max(v1sLen, v2sLen)
			: Math.min(v1sLen, v2sLen);

		for (int i = 0; i < len; i++) {
			String c1 = len > v1sLen || null == v1s[i] ? "" : v1s[i];
			String c2 = len > v2sLen || null == v2s[i] ? "" : v2s[i];

			int result = c1.compareTo(c2);
			if (result != 0) {
				return result;
			}
		}

		return 0;
	}

}
