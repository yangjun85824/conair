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
package org.springblade.resource.rule.sms;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.sms.SmsTemplate;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.pojo.entity.Sms;
import org.springblade.resource.rule.context.SmsContext;

import java.util.Map;

/**
 * 缓存短信构建类
 *
 * @author Chill
 */
@LiteflowComponent(id = "cacheSmsRule", name = "缓存SMS构建")
public class CacheSmsRule extends NodeComponent {

	@Override
	public void process() throws Exception {
		// 获取上下文
		String tenantId = this.getRequestData();
		SmsContext contextBean = this.getContextBean(SmsContext.class);

		Map<String, Sms> smsPool = contextBean.getSmsPool();
		Map<String, SmsTemplate> templatePool = contextBean.getTemplatePool();
		Sms smsCached = smsPool.get(tenantId);
		SmsTemplate template = templatePool.get(tenantId);
		if (Func.hasEmpty(template, smsCached)) {
			throw new ServiceException("SMS缓存读取失败!");
		}

	}
}
