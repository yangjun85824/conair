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

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.sms.AliSmsTemplate;
import org.springblade.core.sms.SmsTemplate;
import org.springblade.core.sms.props.SmsProperties;
import org.springblade.resource.pojo.entity.Sms;
import org.springblade.resource.rule.context.SmsContext;

/**
 * 阿里云短信构建类
 *
 * @author Chill
 */
@LiteflowComponent(id = "aliSmsRule", name = "阿里SMS构建")
public class AliSmsRule extends NodeComponent {

	@Override
	public void process() throws Exception {
		// 获取上下文
		SmsContext contextBean = this.getContextBean(SmsContext.class);
		Sms sms = contextBean.getSms();
		BladeRedis bladeRedis = contextBean.getBladeRedis();

		SmsProperties smsProperties = new SmsProperties();
		smsProperties.setTemplateId(sms.getTemplateId());
		smsProperties.setAccessKey(sms.getAccessKey());
		smsProperties.setSecretKey(sms.getSecretKey());
		smsProperties.setRegionId(sms.getRegionId());
		smsProperties.setSignName(sms.getSignName());
		IClientProfile profile = DefaultProfile.getProfile(smsProperties.getRegionId(), smsProperties.getAccessKey(), smsProperties.getSecretKey());
		IAcsClient acsClient = new DefaultAcsClient(profile);
		SmsTemplate smsTemplate = new AliSmsTemplate(smsProperties, acsClient, bladeRedis);


		// 设置上下文
		contextBean.setSmsTemplate(smsTemplate);

	}
}
