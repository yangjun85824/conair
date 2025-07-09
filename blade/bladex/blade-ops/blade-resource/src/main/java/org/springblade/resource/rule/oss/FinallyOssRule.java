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
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.oss.OssTemplate;
import org.springblade.core.tool.utils.Func;
import org.springblade.resource.pojo.entity.Oss;
import org.springblade.resource.rule.context.OssContext;

import java.util.Map;

/**
 * Oss后置处理
 *
 * @author Chill
 */
@LiteflowComponent(id = "finallyOssRule", name = "OSS构建后置处理")
public class FinallyOssRule extends NodeComponent {
	@Override
	public void process() throws Exception {
		String tenantId = this.getRequestData();
		OssContext contextBean = this.getContextBean(OssContext.class);
		Map<String, Oss> ossPool = contextBean.getOssPool();
		Map<String, OssTemplate> templatePool = contextBean.getTemplatePool();

		if (contextBean.getIsCached()) {
			OssTemplate template = templatePool.get(tenantId);
			contextBean.setOssTemplate(template);
		} else {
			Oss oss = contextBean.getOss();
			OssTemplate template = contextBean.getOssTemplate();
			if (Func.hasEmpty(template, oss)) {
				throw new ServiceException("OSS接口读取失败!");
			} else {
				templatePool.put(tenantId, template);
				ossPool.put(tenantId, oss);
			}
		}

	}
}
