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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 自定义业务封装基础类(推荐自行修改拓展并用于特定业务模块)
 *
 * @param <M> mapper
 * @param <T> model
 * @author Chill
 */
@Validated
public class BizServiceImpl<M extends BaseMapper<T>, T extends BizEntity> extends ServiceImpl<M, T> implements BizService<T> {

	@Override
	public boolean save(T entity) {
		this.resolveEntity(entity);
		return super.save(entity);
	}

	@Override
	public boolean saveBatch(Collection<T> entityList, int batchSize) {
		entityList.forEach(this::resolveEntity);
		return super.saveBatch(entityList, batchSize);
	}

	@Override
	public boolean updateById(T entity) {
		this.resolveEntity(entity);
		return super.updateById(entity);
	}

	@Override
	public boolean updateBatchById(Collection<T> entityList, int batchSize) {
		entityList.forEach(this::resolveEntity);
		return super.updateBatchById(entityList, batchSize);
	}

	@Override
	public boolean saveOrUpdate(T entity) {
		if (entity.getId() == null) {
			return this.save(entity);
		} else {
			return this.updateById(entity);
		}
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
		entityList.forEach(this::resolveEntity);
		return super.saveOrUpdateBatch(entityList, batchSize);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteLogic(@NotEmpty List<Long> ids) {
		BladeUser user = AuthUtil.getUser();
		List<T> list = new ArrayList<>();
		ids.forEach(id -> {
			T entity = BeanUtil.newInstance(currentModelClass());
			if (user != null) {
				entity.setUpdateUser(user.getUserId());
			}
			entity.setUpdateTime(DateUtil.now());
			entity.setId(id);
			list.add(entity);
		});
		return super.updateBatchById(list) && super.removeByIds(ids);
	}

	@Override
	public boolean changeStatus(@NotEmpty List<Long> ids, Integer status) {
		BladeUser user = AuthUtil.getUser();
		List<T> list = new ArrayList<>();
		ids.forEach(id -> {
			T entity = BeanUtil.newInstance(currentModelClass());
			if (user != null) {
				entity.setUpdateUser(user.getUserId());
			}
			entity.setUpdateTime(DateUtil.now());
			entity.setId(id);
			entity.setStatus(status);
			list.add(entity);
		});
		return super.updateBatchById(list);
	}

	@Override
	public boolean isFieldDuplicate(SFunction<T, ?> field, Object value) {
		LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(field, value);
		return super.count(queryWrapper) > 0;
	}

	@Override
	public boolean isFieldDuplicate(SFunction<T, ?> field, Object value, Long excludedId) {
		LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(field, value);
		if (excludedId != null) {
			queryWrapper.ne(T::getId, excludedId);
		}
		return super.count(queryWrapper) > 0;
	}

	@SneakyThrows
	private void resolveEntity(T entity) {
		BladeUser user = AuthUtil.getUser();
		Date now = DateUtil.now();
		if (entity.getId() == null) {
			// 处理新增逻辑
			if (user != null) {
				entity.setCreateUser(user.getUserId());
				entity.setCreateDept(Func.firstLong(user.getDeptId()));
				entity.setUpdateUser(user.getUserId());
			}
			if (entity.getStatus() == null) {
				entity.setStatus(BladeConstant.DB_STATUS_NORMAL);
			}
			entity.setCreateTime(now);
		} else if (user != null) {
			// 处理修改逻辑
			entity.setUpdateUser(user.getUserId());
		}
		// 处理通用逻辑
		entity.setUpdateTime(now);
		entity.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		// 处理多租户逻辑，若字段值为空，则不进行操作
		Field field = ReflectUtil.getField(entity.getClass(), BladeConstant.DB_TENANT_KEY);
		if (ObjectUtil.isNotEmpty(field)) {
			Method getTenantId = ClassUtil.getMethod(entity.getClass(), BladeConstant.DB_TENANT_KEY_GET_METHOD);
			Object invoke = getTenantId.invoke(entity);
			String tenantId = String.valueOf(invoke);
			if (ObjectUtil.isEmpty(invoke) || ObjectUtil.isEmpty(tenantId)) {
				Method setTenantId = ClassUtil.getMethod(entity.getClass(), BladeConstant.DB_TENANT_KEY_SET_METHOD, String.class);
				setTenantId.invoke(entity, (Object) null);
			}
		}
	}

}
