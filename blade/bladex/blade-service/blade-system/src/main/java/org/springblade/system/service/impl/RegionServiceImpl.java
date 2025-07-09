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
package org.springblade.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.pojo.entity.Region;
import org.springblade.system.excel.RegionExcel;
import org.springblade.system.mapper.RegionMapper;
import org.springblade.system.service.IRegionService;
import org.springblade.system.pojo.vo.RegionVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springblade.system.cache.RegionCache.*;


/**
 * 行政区划表 服务实现类
 *
 * @author Chill
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

	@Override
	public boolean submit(Region region) {
		// 设置市级编号格式
		String regionCode = region.getCode();
		String regionParentCode = region.getParentCode();
		if (regionCode.startsWith(MAIN_CODE)) {
			region.setCode(StringUtil.removePrefix(regionCode, MAIN_CODE));
		}
		if (regionParentCode.startsWith(MAIN_CODE)) {
			region.setParentCode(StringUtil.removePrefix(regionParentCode, MAIN_CODE));
		}
		// 查询是否已存在
		Long cnt = baseMapper.selectCount(Wrappers.<Region>query().lambda().eq(Region::getCode, region.getCode()));
		if (cnt > 0L) {
			return this.updateById(region);
		}
		// 设置祖区划编号
		Region parent = getByCode(region.getParentCode());
		if (Func.isNotEmpty(parent) && Func.isNotEmpty(parent.getCode())) {
			String ancestors = parent.getAncestors() + StringPool.COMMA + parent.getCode();
			region.setAncestors(ancestors);
		}
		// 设置省、市、区、镇、村
		Integer level = region.getRegionLevel();
		String code = region.getCode();
		String name = region.getName();
		if (level == PROVINCE_LEVEL) {
			region.setProvinceCode(code);
			region.setProvinceName(name);
		} else if (level == CITY_LEVEL) {
			region.setCityCode(code);
			region.setCityName(name);
		} else if (level == DISTRICT_LEVEL) {
			region.setDistrictCode(code);
			region.setDistrictName(name);
		} else if (level == TOWN_LEVEL) {
			region.setTownCode(code);
			region.setTownName(name);
		} else if (level == VILLAGE_LEVEL) {
			region.setVillageCode(code);
			region.setVillageName(name);
		}
		return this.save(region);
	}

	@Override
	public boolean removeRegion(String id) {
		Long cnt = baseMapper.selectCount(Wrappers.<Region>query().lambda().eq(Region::getParentCode, id));
		if (cnt > 0L) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeById(id);
	}

	@Override
	public List<RegionVO> lazyList(String parentCode, Map<String, Object> param) {
		return baseMapper.lazyList(parentCode, param);
	}

	@Override
	public List<RegionVO> lazyTree(String parentCode, Map<String, Object> param) {
		return baseMapper.lazyTree(parentCode, param);
	}

	@Override
	public void importRegion(List<RegionExcel> data, Boolean isCovered) {
		List<Region> list = new ArrayList<>();
		data.forEach(regionExcel -> {
			Region region = BeanUtil.copyProperties(regionExcel, Region.class);
			list.add(region);
		});
		if (isCovered) {
			this.saveOrUpdateBatch(list);
		} else {
			this.saveBatch(list);
		}
	}

	@Override
	public List<RegionExcel> exportRegion(Wrapper<Region> queryWrapper) {
		return baseMapper.exportRegion(queryWrapper);
	}
}
