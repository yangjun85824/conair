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
package org.springblade.develop.service;

import org.springblade.core.mp.base.BaseService;
import org.springblade.develop.pojo.entity.ModelPrototype;

import java.util.List;

/**
 * 数据原型表 服务类
 *
 * @author Chill
 */
public interface IModelPrototypeService extends BaseService<ModelPrototype> {

	/**
	 * 批量提交
	 *
	 * @param modelPrototypes 原型集合
	 * @return boolean
	 */
	boolean submitList(List<ModelPrototype> modelPrototypes);

	/**
	 * 原型列表
	 *
	 * @param modelId 模型ID
	 * @return List<ModelPrototype>
	 */
	List<ModelPrototype> prototypeList(Long modelId);

}
