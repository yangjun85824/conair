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

import lombok.AllArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;

/**
 * Spring AntPath 规则文件过滤
 *
 * @author L.cm
 */
@AllArgsConstructor
public class AntPathFilter implements FileFilter, Serializable {
	private static final long serialVersionUID = 812598009067554612L;
	private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

	private final String pattern;

	/**
	 * 过滤规则
	 *
	 * @param pathname 路径
	 * @return boolean
	 */
	@Override
	public boolean accept(File pathname) {
		String filePath = pathname.getAbsolutePath();
		return PATH_MATCHER.match(pattern, filePath);
	}
}
