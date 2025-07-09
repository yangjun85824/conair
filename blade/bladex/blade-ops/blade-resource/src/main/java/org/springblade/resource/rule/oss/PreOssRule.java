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
import org.springblade.core.oss.OssTemplate;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.pojo.entity.Oss;
import org.springblade.resource.rule.context.OssContext;

import java.util.Map;

/**
 * Oss前置处理
 *
 * @author Chill
 */
@LiteflowComponent(id = "preOssRule", name = "OSS构建前置处理")
public class PreOssRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		String tenantId = this.getRequestData();
		OssContext contextBean = this.getContextBean(OssContext.class);
		Map<String, Oss> ossPool = contextBean.getOssPool();
		Map<String, OssTemplate> templatePool = contextBean.getTemplatePool();
		Oss oss = contextBean.getOss();
		Oss ossCached = ossPool.get(tenantId);
		OssTemplate template = templatePool.get(tenantId);
		// 若为空或者不一致，则重新加载
		if (Func.hasEmpty(template, ossCached)
			|| !Func.equalsSafe(oss.getEndpoint(), ossCached.getEndpoint())
			|| !Func.equalsSafe(oss.getTransformEndpoint(), ossCached.getTransformEndpoint())
			|| !Func.equalsSafe(oss.getAccessKey(), ossCached.getAccessKey())) {
			contextBean.setIsCached(Boolean.FALSE);
		} else {
			contextBean.setIsCached(Boolean.TRUE);
		}
	}

}
