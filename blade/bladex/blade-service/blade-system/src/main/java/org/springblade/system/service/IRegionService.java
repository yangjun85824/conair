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
package org.springblade.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.system.pojo.entity.Region;
import org.springblade.system.excel.RegionExcel;
import org.springblade.system.pojo.vo.RegionVO;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表 服务类
 *
 * @author Chill
 */
public interface IRegionService extends IService<Region> {

	/**
	 * 提交
	 *
	 * @param region
	 * @return
	 */
	boolean submit(Region region);

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	boolean removeRegion(String id);

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<RegionVO> lazyList(String parentCode, Map<String, Object> param);

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<RegionVO> lazyTree(String parentCode, Map<String, Object> param);

	/**
	 * 导入区划数据
	 *
	 * @param data
	 * @param isCovered
	 * @return
	 */
	void importRegion(List<RegionExcel> data, Boolean isCovered);

	/**
	 * 导出区划数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<RegionExcel> exportRegion(Wrapper<Region> queryWrapper);

}
