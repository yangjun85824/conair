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
package org.springblade.modules.visual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.modules.visual.mapper.VisualPushTemplateMapper;
import org.springblade.modules.visual.pojo.entity.VisualPushTemplate;
import org.springblade.modules.visual.service.IVisualPushTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springblade.modules.visual.cache.PushCache.PUSH_CACHE;
import static org.springblade.modules.visual.cache.PushCache.TEMPLATE_CODE;


/**
 * 消息模版配置表 服务实现类
 *
 * @author Blade
 */
@Service
public class VisualPushTemplateServiceImpl extends ServiceImpl<VisualPushTemplateMapper, VisualPushTemplate> implements IVisualPushTemplateService {


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submit(VisualPushTemplate pushTemplate) {
		if (isFieldDuplicate(VisualPushTemplate::getTemplateName, pushTemplate.getTemplateName(), pushTemplate.getId())) {
			throw new ServiceException("[模板名称] 不可重复");
		}
		if (isFieldDuplicate(VisualPushTemplate::getTemplateCode, pushTemplate.getTemplateCode(), pushTemplate.getId())) {
			throw new ServiceException("[模板编号] 不可重复");
		}
		pushTemplate.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		CacheUtil.evict(PUSH_CACHE, TEMPLATE_CODE, pushTemplate.getTemplateCode());
		return this.saveOrUpdate(pushTemplate);
	}

	public boolean isFieldDuplicate(SFunction<VisualPushTemplate, ?> field, Object value, Long excludedId) {
		LambdaQueryWrapper<VisualPushTemplate> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(field, value);
		if (excludedId != null) {
			queryWrapper.ne(VisualPushTemplate::getId, excludedId);
		}
		return baseMapper.selectCount(queryWrapper) > 0;
	}

}
