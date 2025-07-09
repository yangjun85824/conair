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

package org.springblade.core.mp.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springblade.core.mp.base.BaseEntity;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.injector.BladeSqlMethod;
import org.springblade.core.mp.mapper.BladeMapper;
import org.springblade.core.mp.service.BladeService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

/**
 * BladeService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 *
 * @author L.cm, chill
 */
@Validated
public class BladeServiceImpl<M extends BladeMapper<T>, T extends BaseEntity> extends BaseServiceImpl<M, T> implements BladeService<T> {

	@Override
	public boolean saveIgnore(T entity) {
		return SqlHelper.retBool(baseMapper.insertIgnore(entity));
	}

	@Override
	public boolean saveReplace(T entity) {
		return SqlHelper.retBool(baseMapper.replace(entity));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveIgnoreBatch(Collection<T> entityList, int batchSize) {
		return saveBatch(entityList, batchSize, BladeSqlMethod.INSERT_IGNORE_ONE);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveReplaceBatch(Collection<T> entityList, int batchSize) {
		return saveBatch(entityList, batchSize, BladeSqlMethod.REPLACE_ONE);
	}

	private boolean saveBatch(Collection<T> entityList, int batchSize, BladeSqlMethod sqlMethod) {
		String sqlStatement = bladeSqlStatement(sqlMethod);
		executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
		return true;
	}

	/**
	 * 获取 bladeSqlStatement
	 *
	 * @param sqlMethod ignore
	 * @return sql
	 */
	protected String bladeSqlStatement(BladeSqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
	}
}
