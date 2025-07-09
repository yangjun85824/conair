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
import org.springblade.resource.pojo.entity.Sms;
import org.springblade.resource.mapper.SmsMapper;
import org.springblade.resource.service.ISmsService;
import org.springblade.resource.pojo.vo.SmsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 短信配置表 服务实现类
 *
 * @author BladeX
 */
@Service
public class SmsServiceImpl extends BaseServiceImpl<SmsMapper, Sms> implements ISmsService {

	@Override
	public IPage<SmsVO> selectSmsPage(IPage<SmsVO> page, SmsVO sms) {
		return page.setRecords(baseMapper.selectSmsPage(page, sms));
	}

	@Override
	public boolean submit(Sms sms) {
		LambdaQueryWrapper<Sms> lqw = Wrappers.<Sms>query().lambda()
			.eq(Sms::getSmsCode, sms.getSmsCode()).eq(Sms::getTenantId, AuthUtil.getTenantId());
		Long cnt = baseMapper.selectCount(Func.isEmpty(sms.getId()) ? lqw : lqw.notIn(Sms::getId, sms.getId()));
		if (cnt > 0L) {
			throw new ServiceException("当前资源编号已存在!");
		}
		return this.saveOrUpdate(sms);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean enable(Long id) {
		// 先禁用
		boolean temp1 = this.update(Wrappers.<Sms>update().lambda().set(Sms::getStatus, 1));
		// 在启用
		boolean temp2 = this.update(Wrappers.<Sms>update().lambda().set(Sms::getStatus, 2).eq(Sms::getId, id));
		return temp1 && temp2;
	}

}
