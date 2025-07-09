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
package org.springblade.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.cache.DictBizCache;
import org.springblade.system.pojo.entity.DictBiz;
import org.springblade.system.pojo.vo.DictBizVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class DictBizWrapper extends BaseEntityWrapper<DictBiz, DictBizVO> {

	public static DictBizWrapper build() {
		return new DictBizWrapper();
	}

	@Override
	public DictBizVO entityVO(DictBiz dict) {
		DictBizVO dictVO = Objects.requireNonNull(BeanUtil.copyProperties(dict, DictBizVO.class));
		if (Func.equals(dict.getParentId(), BladeConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(BladeConstant.TOP_PARENT_NAME);
		} else {
			DictBiz parent = DictBizCache.getById(dict.getParentId());
			dictVO.setParentName(parent.getDictValue());
		}
		return dictVO;
	}

	public List<DictBizVO> listNodeVO(List<DictBiz> list) {
		List<DictBizVO> collect = list.stream().map(dict -> BeanUtil.copyProperties(dict, DictBizVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
