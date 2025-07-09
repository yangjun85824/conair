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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.visual.pojo.dto.VisualComponentDTO;
import org.springblade.modules.visual.pojo.entity.VisualComponent;
import org.springblade.modules.visual.mapper.VisualComponentMapper;
import org.springblade.modules.visual.service.IVisualComponentService;
import org.springframework.stereotype.Service;

/**
 * 可视化地图配置表 服务实现类
 *
 * @author BladeX
 */
@Service
public class VisualComponentServiceImpl extends ServiceImpl<VisualComponentMapper, VisualComponent> implements IVisualComponentService {

	@Override
	public IPage<VisualComponentDTO> selectVisualComponentPage(IPage<VisualComponentDTO> page, VisualComponentDTO visualComponent) {
		return page.setRecords(baseMapper.selectVisualComponentPage(page, visualComponent));
	}

}
