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

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.resource.pojo.entity.Attach;
import org.springblade.resource.mapper.AttachMapper;
import org.springblade.resource.service.IAttachService;
import org.springblade.resource.pojo.vo.AttachVO;
import org.springframework.stereotype.Service;

/**
 * 附件表 服务实现类
 *
 * @author Chill
 */
@Service
public class AttachServiceImpl extends BaseServiceImpl<AttachMapper, Attach> implements IAttachService {

	@Override
	public IPage<AttachVO> selectAttachPage(IPage<AttachVO> page, AttachVO attach) {
		return page.setRecords(baseMapper.selectAttachPage(page, attach));
	}

}
