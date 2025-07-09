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
import com.yomahub.liteflow.core.NodeSwitchComponent;
import org.springblade.resource.rule.context.OssContext;

/**
 * Oss缓存判断
 *
 * @author Chill
 */
@LiteflowComponent(id = "ossCacheRule", name = "OSS缓存判断")
public class OssCacheRule extends NodeSwitchComponent {

	@Override
	public String processSwitch() {
		OssContext contextBean = this.getContextBean(OssContext.class);
		// 若判断配置已缓存则直接读取，否则进入下一步构建新数据
		if (contextBean.getIsCached()) {
			return "ossReadRule";
		} else {
			return "ossNewRule";
		}
	}

}
