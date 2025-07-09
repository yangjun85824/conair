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
package org.springblade.resource.rule.oss;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.oss.rule.BladeOssRule;
import org.springblade.resource.pojo.entity.Oss;
import org.springblade.resource.rule.context.OssContext;

/**
 * OSS数据创建
 *
 * @author Chill
 */
@LiteflowComponent(id = "ossDataRule", name = "OSS数据创建")
public class OssDataRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		OssContext contextBean = this.getContextBean(OssContext.class);
		Oss oss = contextBean.getOss();
		OssProperties ossProperties = contextBean.getOssProperties();
		// 若采用默认设置则开启多租户模式, 若是用户自定义oss则不开启
		if (oss.getEndpoint().equals(ossProperties.getEndpoint()) && oss.getAccessKey().equals(ossProperties.getAccessKey()) && ossProperties.getTenantMode()) {
			contextBean.setOssRule(new BladeOssRule(Boolean.TRUE));
		} else {
			contextBean.setOssRule(new BladeOssRule(Boolean.FALSE));
		}
	}
}
