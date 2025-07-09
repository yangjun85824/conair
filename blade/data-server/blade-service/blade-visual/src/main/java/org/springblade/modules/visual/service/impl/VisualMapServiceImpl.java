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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.visual.cache.MapCache;
import org.springblade.modules.visual.mapper.VisualMapMapper;
import org.springblade.modules.visual.pojo.entity.VisualMap;
import org.springblade.modules.visual.pojo.vo.VisualMapVO;
import org.springblade.modules.visual.service.IVisualMapService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springblade.modules.visual.cache.MapCache.MAIN_CODE;

/**
 * 可视化地图配置表 服务实现类
 *
 * @author BladeX
 */
@Service
public class VisualMapServiceImpl extends ServiceImpl<VisualMapMapper, VisualMap> implements IVisualMapService {

	@Override
	public List<VisualMapVO> lazyList(String parentId, String name) {
		return ForestNodeMerger.merge(baseMapper.lazyList(parentId, name));
	}

	@Override
	public boolean submit(VisualMap map) {
		// 判断是否重复
		if (isFieldDuplicate(VisualMap::getCode, map.getCode(), map.getId())) {
			throw new ServiceException("[地图编号] 不可重复");
		}
		// 设置市级编号格式
		String regionCode = map.getCode();
		String regionParentCode = map.getParentCode();
		if (regionCode.startsWith(MAIN_CODE)) {
			map.setCode(StringUtil.removePrefix(regionCode, MAIN_CODE));
		}
		if (regionParentCode.startsWith(MAIN_CODE)) {
			map.setParentCode(StringUtil.removePrefix(regionParentCode, MAIN_CODE));
		}
		// 设置祖区划编号
		VisualMap parent = MapCache.getByCode(map.getParentCode());
		if (Func.isNotEmpty(parent) && Func.isNotEmpty(parent.getCode())) {
			String ancestors = parent.getAncestors() + StringPool.COMMA + parent.getCode();
			map.setAncestors(ancestors);
		}
		return this.saveOrUpdate(map);
	}

	public boolean isFieldDuplicate(SFunction<VisualMap, ?> field, Object value, Long excludedId) {
		LambdaQueryWrapper<VisualMap> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(field, value);
		if (excludedId != null) {
			queryWrapper.ne(VisualMap::getId, excludedId);
		}
		return baseMapper.selectCount(queryWrapper) > 0;
	}

}
