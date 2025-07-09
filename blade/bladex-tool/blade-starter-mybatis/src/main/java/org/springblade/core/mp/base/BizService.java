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
package org.springblade.core.mp.base;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 自定义业务接口(推荐自行修改拓展并用于特定业务模块)
 *
 * @param <T>
 * @author Chill
 */
public interface BizService<T> extends IService<T> {

	/**
	 * 逻辑删除
	 *
	 * @param ids id集合
	 * @return
	 */
	boolean deleteLogic(@NotEmpty List<Long> ids);

	/**
	 * 变更状态
	 *
	 * @param ids    id集合
	 * @param status 状态值
	 * @return
	 */
	boolean changeStatus(@NotEmpty List<Long> ids, Integer status);

	/**
	 * 判断是否有字段重复
	 *
	 * @param field 字段
	 * @param value 值
	 * @return boolean
	 */
	boolean isFieldDuplicate(SFunction<T, ?> field, Object value);

	/**
	 * 判断是否有字段重复
	 *
	 * @param field      字段
	 * @param value      值
	 * @param excludedId 排除的id
	 * @return boolean
	 */
	boolean isFieldDuplicate(SFunction<T, ?> field, Object value, Long excludedId);

}
