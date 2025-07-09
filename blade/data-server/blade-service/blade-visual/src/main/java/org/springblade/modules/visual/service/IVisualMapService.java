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
package org.springblade.modules.visual.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.visual.pojo.entity.VisualMap;
import org.springblade.modules.visual.pojo.vo.VisualMapVO;

import java.util.List;

/**
 * 可视化地图配置表 服务类
 *
 * @author BladeX
 */
public interface IVisualMapService extends IService<VisualMap> {
	/**
	 * 获取地图列表
	 *
	 * @param parentId
	 * @param name
	 * @return
	 */
	List<VisualMapVO> lazyList(String parentId, String name);

	/**
	 * 提交
	 *
	 * @param map
	 * @return
	 */
	boolean submit(VisualMap map);
}
