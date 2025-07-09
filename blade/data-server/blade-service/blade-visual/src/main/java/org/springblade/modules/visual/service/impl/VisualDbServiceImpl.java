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
package org.springblade.modules.visual.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.PlaceholderUtil;
import org.springblade.modules.visual.pojo.dto.VisualDbDTO;
import org.springblade.modules.visual.dynamic.DynamicDataSourceHelper;
import org.springblade.modules.visual.dynamic.DynamicDataSourceHolder;
import org.springblade.modules.visual.pojo.entity.VisualDb;
import org.springblade.modules.visual.mapper.VisualDbMapper;
import org.springblade.modules.visual.service.IVisualDbService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 可视化数据源配置表 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class VisualDbServiceImpl extends BaseServiceImpl<VisualDbMapper, VisualDb> implements IVisualDbService {

	private final DynamicDataSourceHolder holder;
	private final DynamicDataSourceHelper helper;

	@Override
	public Boolean dbTest(VisualDb db) {
		return helper.dbTest(db.getDriverClass(), db.getUrl(), db.getUsername(), db.getPassword());
	}

	@Override
	public List<VisualDbDTO> dbList() {
		return baseMapper.dbList();
	}

	@Override
	public List<LinkedHashMap<String, Object>> dynamicQuery(String id, String sql) {
		try {
			//处理自定义数据源
			holder.handleDataSource(id);
			//切换数据源
			DynamicDataSourceContextHolder.push(id);
			//获取user信息
			BladeUser user = AuthUtil.getUser();
			//获取user参数
			Map<String, Object> map = (Func.isEmpty(user)) ? Kv.newMap() : BeanUtil.toMap(user);
			//替换user占位符
			String dynamicSql = PlaceholderUtil.getDefaultResolver().resolveByMap(sql, map);
			//执行自定义sql
			return baseMapper.dynamicQuery(dynamicSql);
		} catch (Exception exception) {
			throw new ServiceException(exception.getMessage());
		} finally {
			//切回主数据源
			DynamicDataSourceContextHolder.poll();
		}

	}

}
