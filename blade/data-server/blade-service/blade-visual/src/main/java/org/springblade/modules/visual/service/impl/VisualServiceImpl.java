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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.modules.visual.pojo.dto.VisualDTO;
import org.springblade.modules.visual.pojo.entity.Visual;
import org.springblade.modules.visual.pojo.entity.VisualConfig;
import org.springblade.modules.visual.mapper.VisualMapper;
import org.springblade.modules.visual.service.IVisualConfigService;
import org.springblade.modules.visual.service.IVisualService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 可视化表 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class VisualServiceImpl extends BaseServiceImpl<VisualMapper, Visual> implements IVisualService {

	private final IVisualConfigService configService;

	@Override
	public VisualDTO detail(Long id) {
		Visual visual = this.getById(id);
		VisualConfig visualConfig = configService.getOne(Wrappers.<VisualConfig>query().lambda().eq(VisualConfig::getVisualId, id));
		VisualDTO dto = new VisualDTO();
		dto.setVisual(visual);
		dto.setConfig(visualConfig);
		return dto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveVisual(VisualDTO dto) {
		Visual visual = dto.getVisual();
		VisualConfig visualConfig = dto.getConfig();
		boolean tempV = this.save(visual);
		visualConfig.setVisualId(visual.getId());
		boolean tempVc = configService.save(visualConfig);
		return tempV && tempVc;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateVisual(VisualDTO dto) {
		Visual visual = dto.getVisual();
		VisualConfig visualConfig = dto.getConfig();
		if (visual != null && visual.getId() != null) {
			this.updateById(visual);
		}
		if (visualConfig != null && visualConfig.getId() != null) {
			configService.updateById(visualConfig);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String copyVisual(Long id) {
		Visual visual = this.getById(id);
		VisualConfig visualConfig = configService.getOne(Wrappers.<VisualConfig>query().lambda().eq(VisualConfig::getVisualId, id));
		if (visual != null && visualConfig != null) {
			visual.setId(null);
			visualConfig.setId(null);
			VisualDTO dto = new VisualDTO();
			dto.setVisual(visual);
			dto.setConfig(visualConfig);
			boolean temp = this.saveVisual(dto);
			if (temp) {
				return String.valueOf(visual.getId());
			}
		}
		return null;
	}
}
