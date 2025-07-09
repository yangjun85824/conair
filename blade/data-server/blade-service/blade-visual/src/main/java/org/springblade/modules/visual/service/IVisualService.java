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

import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.visual.pojo.dto.VisualDTO;
import org.springblade.modules.visual.pojo.entity.Visual;

/**
 * 可视化表 服务类
 *
 * @author Chill
 */
public interface IVisualService extends BaseService<Visual> {

	/**
	 * 获取 可视化信息
	 *
	 * @param id 主键
	 * @return VisualDTO
	 */
	VisualDTO detail(Long id);

	/**
	 * 保存可视化信息
	 *
	 * @param dto 配置信息
	 * @return boolean
	 */
	boolean saveVisual(VisualDTO dto);

	/**
	 * 修改可视化信息
	 *
	 * @param dto 配置信息
	 * @return boolean
	 */
	boolean updateVisual(VisualDTO dto);

	/**
	 * 复制可视化信息
	 *
	 * @param id 主键
	 * @return 复制后主键
	 */
	String copyVisual(Long id);

}
