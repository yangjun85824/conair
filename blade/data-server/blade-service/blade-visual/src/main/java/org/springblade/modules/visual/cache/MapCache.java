/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2024, https://bladex.cn. All rights reserved.
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
package org.springblade.modules.visual.cache;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.visual.pojo.entity.VisualMap;
import org.springblade.modules.visual.service.IVisualMapService;

/**
 * 地图缓存
 *
 * @author Chill
 */
public class MapCache {
	public static final String MAIN_CODE = "00";
	public static final int PROVINCE_LEVEL = 1;
	public static final int CITY_LEVEL = 2;
	public static final int DISTRICT_LEVEL = 3;
	public static final int TOWN_LEVEL = 4;
	public static final int VILLAGE_LEVEL = 5;

	public static final String MAP_CACHE = "blade:map";
	public static final String MAP_ID = "map:id:";
	public static final String MAP_CODE = "map:code:";

	private static final IVisualMapService MAP_SERVICE;

	static {
		MAP_SERVICE = SpringUtil.getBean(IVisualMapService.class);
	}


	/**
	 * 获取地图实体
	 *
	 * @param id 地图id
	 * @return VisualMap
	 */
	public static VisualMap getById(Long id) {
		return CacheUtil.get(MAP_CACHE, MAP_ID, id, () -> MAP_SERVICE.getById(id));
	}

	/**
	 * 获取地图实体
	 *
	 * @param code 地图code
	 * @return VisualMap
	 */
	public static VisualMap getByCode(String code) {
		return CacheUtil.get(MAP_CACHE, MAP_CODE, code, () -> MAP_SERVICE.getOne(Wrappers.<VisualMap>lambdaQuery().eq(VisualMap::getCode, code)));
	}


}
