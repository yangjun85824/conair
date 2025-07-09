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
import com.yomahub.liteflow.core.NodeSwitchComponent;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.sms.enums.SmsEnum;
import org.springblade.resource.pojo.entity.Sms;
import org.springblade.resource.rule.context.SmsContext;

/**
 * Sms构建判断
 *
 * @author Chill
 */
@LiteflowComponent(id = "smsBuildRule", name = "SMS构建条件判断")
public class SmsBuildRule extends NodeSwitchComponent {

	@Override
	public String processSwitch() {
		SmsContext contextBean = this.getContextBean(SmsContext.class);
		Sms sms = contextBean.getSms();

		if (contextBean.getIsCached()) {
			return "cacheSmsRule";
		} else if (sms.getCategory() == SmsEnum.YUNPIAN.getCategory()) {
			return "yunpianSmsRule";
		} else if (sms.getCategory() == SmsEnum.QINIU.getCategory()) {
			return "qiniuSmsRule";
		} else if (sms.getCategory() == SmsEnum.ALI.getCategory()) {
			return "aliSmsRule";
		} else if (sms.getCategory() == SmsEnum.TENCENT.getCategory()) {
			return "tencentSmsRule";
		}

		throw new ServiceException("未找到SMS配置");
	}
}
