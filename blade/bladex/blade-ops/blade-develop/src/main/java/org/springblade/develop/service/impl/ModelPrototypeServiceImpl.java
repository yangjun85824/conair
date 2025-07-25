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
package org.springblade.develop.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.develop.pojo.entity.ModelPrototype;
import org.springblade.develop.mapper.ModelPrototypeMapper;
import org.springblade.develop.service.IModelPrototypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据原型表 服务实现类
 *
 * @author Chill
 */
@Service
public class ModelPrototypeServiceImpl extends BaseServiceImpl<ModelPrototypeMapper, ModelPrototype> implements IModelPrototypeService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submitList(List<ModelPrototype> modelPrototypes) {
		modelPrototypes.forEach(modelPrototype -> {
			if (modelPrototype.getId() == null) {
				this.save(modelPrototype);
			} else {
				this.updateById(modelPrototype);
			}
		});
		return true;
	}

	@Override
	public List<ModelPrototype> prototypeList(Long modelId) {
		return this.list(Wrappers.<ModelPrototype>lambdaQuery().eq(ModelPrototype::getModelId, modelId));
	}

}
