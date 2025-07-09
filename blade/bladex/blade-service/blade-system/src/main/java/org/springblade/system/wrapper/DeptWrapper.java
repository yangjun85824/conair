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
import org.springblade.system.cache.DictCache;
import org.springblade.system.cache.SysCache;
import org.springblade.system.pojo.entity.Dept;
import org.springblade.system.pojo.enums.DictEnum;
import org.springblade.system.pojo.vo.DeptVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO> {

	public static DeptWrapper build() {
		return new DeptWrapper();
	}

	@Override
	public DeptVO entityVO(Dept dept) {
		DeptVO deptVO = Objects.requireNonNull(BeanUtil.copyProperties(dept, DeptVO.class));
		if (Func.equals(dept.getParentId(), BladeConstant.TOP_PARENT_ID)) {
			deptVO.setParentName(BladeConstant.TOP_PARENT_NAME);
		} else {
			Dept parent = SysCache.getDept(dept.getParentId());
			deptVO.setParentName(parent.getDeptName());
		}
		String category = DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory());
		deptVO.setDeptCategoryName(category);
		return deptVO;
	}


	public List<DeptVO> listNodeVO(List<Dept> list) {
		List<DeptVO> collect = list.stream().map(dept -> {
			DeptVO deptVO = BeanUtil.copyProperties(dept, DeptVO.class);
			String category = DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory());
			Objects.requireNonNull(deptVO).setDeptCategoryName(category);
			return deptVO;
		}).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	public List<DeptVO> listNodeLazyVO(List<DeptVO> list) {
		List<DeptVO> collect = list.stream().peek(dept -> {
			String category = DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory());
			Objects.requireNonNull(dept).setDeptCategoryName(category);
		}).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
