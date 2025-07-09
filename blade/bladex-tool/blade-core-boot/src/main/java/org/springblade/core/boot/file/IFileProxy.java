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
package org.springblade.core.boot.file;

import java.io.File;

/**
 * 文件代理接口
 *
 * @author Chill
 */
public interface IFileProxy {

	/**
	 * 返回路径[物理路径][虚拟路径]
	 *
	 * @param file 文件
	 * @param dir  目录
	 * @return
	 */
	String[] path(File file, String dir);

	/**
	 * 文件重命名策略
	 *
	 * @param file 文件
	 * @param path 路径
	 * @return
	 */
	File rename(File file, String path);

	/**
	 * 图片压缩
	 *
	 * @param path 路径
	 */
	void compress(String path);

}
