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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.system.pojo.entity.TopMenu;
import org.springblade.system.pojo.entity.TopMenuSetting;
import org.springblade.system.mapper.TopMenuMapper;
import org.springblade.system.service.ITopMenuService;
import org.springblade.system.service.ITopMenuSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 顶部菜单表 服务实现类
 *
 * @author BladeX
 */
@Service
@AllArgsConstructor
public class TopMenuServiceImpl extends BaseServiceImpl<TopMenuMapper, TopMenu> implements ITopMenuService {

	private final ITopMenuSettingService topMenuSettingService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds) {
		// 删除顶部菜单配置的菜单集合
		topMenuSettingService.remove(Wrappers.<TopMenuSetting>update().lambda().in(TopMenuSetting::getTopMenuId, topMenuIds));
		// 组装配置
		List<TopMenuSetting> menuSettings = new ArrayList<>();
		topMenuIds.forEach(topMenuId -> menuIds.forEach(menuId -> {
			TopMenuSetting menuSetting = new TopMenuSetting();
			menuSetting.setTopMenuId(topMenuId);
			menuSetting.setMenuId(menuId);
			menuSettings.add(menuSetting);
		}));
		// 新增配置
		topMenuSettingService.saveBatch(menuSettings);
		return true;
	}

}
