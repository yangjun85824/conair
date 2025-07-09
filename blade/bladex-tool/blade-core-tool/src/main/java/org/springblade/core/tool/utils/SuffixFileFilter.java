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

import org.springframework.util.Assert;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;

/**
 * 文件后缀过滤器
 *
 * @author L.cm
 */
public class SuffixFileFilter implements FileFilter, Serializable {

	private static final long serialVersionUID = -3389157631240246157L;

	private final String[] suffixes;

	public SuffixFileFilter(final String suffix) {
		Assert.notNull(suffix, "The suffix must not be null");
		this.suffixes = new String[]{suffix};
	}

	public SuffixFileFilter(final String[] suffixes) {
		Assert.notNull(suffixes, "The suffix must not be null");
		this.suffixes = new String[suffixes.length];
		System.arraycopy(suffixes, 0, this.suffixes, 0, suffixes.length);
	}

	@Override
	public boolean accept(File pathname) {
		final String name = pathname.getName();
		for (final String suffix : this.suffixes) {
			if (checkEndsWith(name, suffix)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkEndsWith(final String str, final String end) {
		final int endLen = end.length();
		return str.regionMatches(true, str.length() - endLen, end, 0, endLen);
	}
}
