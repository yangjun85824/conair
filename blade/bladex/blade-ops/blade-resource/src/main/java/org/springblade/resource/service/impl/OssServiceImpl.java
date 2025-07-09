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
package org.springblade.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.pojo.entity.Oss;
import org.springblade.resource.pojo.vo.OssVO;
import org.springblade.resource.mapper.OssMapper;
import org.springblade.resource.service.IOssService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现类
 *
 * @author BladeX
 */
@Service
public class OssServiceImpl extends BaseServiceImpl<OssMapper, Oss> implements IOssService {

	@Override
	public IPage<OssVO> selectOssPage(IPage<OssVO> page, OssVO oss) {
		return page.setRecords(baseMapper.selectOssPage(page, oss));
	}

	@Override
	public boolean submit(Oss oss) {
		LambdaQueryWrapper<Oss> lqw = Wrappers.<Oss>query().lambda()
			.eq(Oss::getOssCode, oss.getOssCode()).eq(Oss::getTenantId, AuthUtil.getTenantId());
		Long cnt = baseMapper.selectCount(Func.isEmpty(oss.getId()) ? lqw : lqw.notIn(Oss::getId, oss.getId()));
		if (cnt > 0L) {
			throw new ServiceException("当前资源编号已存在!");
		}
		return this.saveOrUpdate(oss);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean enable(Long id) {
		// 先禁用
		boolean temp1 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getStatus, 1));
		// 在启用
		boolean temp2 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getStatus, 2).eq(Oss::getId, id));
		return temp1 && temp2;
	}

}
