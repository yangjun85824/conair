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
import org.springblade.modules.visual.mapper.VisualFunMapper;
import org.springblade.modules.visual.pojo.entity.VisualFun;
import org.springblade.modules.visual.pojo.vo.VisualFunVO;
import org.springblade.modules.visual.service.IVisualFunService;
import org.springframework.stereotype.Service;

/**
 * 可视化代码库表 服务实现类
 *
 * @author Blade
 */
@Service
public class VisualFunServiceImpl extends ServiceImpl<VisualFunMapper, VisualFun> implements IVisualFunService {

	@Override
	public IPage<VisualFunVO> selectVisualFunPage(IPage<VisualFunVO> page, VisualFunVO visualFun) {
		return page.setRecords(baseMapper.selectVisualFunPage(page, visualFun));
	}

}
