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
package org.springblade.core.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 自定义的 Mapper
 *
 * @author L.cm
 */
public interface BladeMapper<T> extends BaseMapper<T> {

	/**
	 * 插入如果中已经存在相同的记录，则忽略当前新数据
	 *
	 * @param entity 实体对象
	 * @return 更改的条数
	 */
	int insertIgnore(T entity);

	/**
	 * 表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
	 *
	 * @param entity 实体对象
	 * @return 更改的条数
	 */
	int replace(T entity);

	/**
	 * 插入（批量）
	 *
	 * @param entityList 实体对象集合
	 * @return 成功行数
	 */
	int insertBatchSomeColumn(List<T> entityList);
}
