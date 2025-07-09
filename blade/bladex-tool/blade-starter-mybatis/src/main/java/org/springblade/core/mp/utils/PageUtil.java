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
package org.springblade.core.mp.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.core.tool.utils.BeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 分页工具类
 *
 * @author L.cm
 */
public class PageUtil {

	/**
	 * 2个 IPage 转 Page
	 *
	 * @param page   IPage
	 * @param target 需要copy转换的类型
	 * @param <T>    泛型
	 * @return PageResult
	 */
	public static <T> Page<T> toPage(IPage<?> page, Class<T> target) {
		List<T> records = BeanUtil.copy(page.getRecords(), target);
		return toPage(page, records);
	}

	/**
	 * 2个 IPage 转 Page
	 *
	 * @param page    IPage
	 * @param records 转换过的list模型
	 * @param <T>     泛型
	 * @return PageResult
	 */
	public static <T> Page<T> toPage(IPage<?> page, List<T> records) {
		Page<T> pageResult = new Page<>();
		pageResult.setCurrent(page.getCurrent());
		pageResult.setSize(page.getSize());
		pageResult.setPages(page.getPages());
		pageResult.setTotal(page.getTotal());
		pageResult.setRecords(records);
		return pageResult;
	}

	/**
	 * Page 转换
	 *
	 * @param page     IPage
	 * @param function 转换过的函数
	 * @param <T>      泛型
	 * @return PageResult
	 */
	public static <T, R> Page<R> toPage(IPage<T> page, Function<T, R> function) {
		List<R> records = new ArrayList<>();
		for (T record : page.getRecords()) {
			records.add(function.apply(record));
		}
		return toPage(page, records);
	}

}
