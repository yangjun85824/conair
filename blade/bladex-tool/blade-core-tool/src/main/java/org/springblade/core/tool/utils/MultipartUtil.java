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

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MultipartUtils
 *
 * @author Chill
 */
public class MultipartUtil {
	/**
	 * 从HttpServletRequest中解析并返回所有的MultipartFile
	 *
	 * @param request HttpServletRequest对象，应为MultipartHttpServletRequest类型
	 * @return 包含所有MultipartFile的列表，如果没有文件或请求不是多部分请求，则返回空列表
	 */
	public static List<MultipartFile> extractMultipartFiles(HttpServletRequest request) {
		List<MultipartFile> files = new ArrayList<>();

		if (request instanceof MultipartHttpServletRequest multipartRequest) {

			// 获取所有文件的映射
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

			// 遍历映射，并将所有MultipartFile添加到列表中
			for (MultipartFile file : fileMap.values()) {
				if (file != null && !file.isEmpty()) {
					files.add(file);
				}
			}
		}

		return files;
	}
}
