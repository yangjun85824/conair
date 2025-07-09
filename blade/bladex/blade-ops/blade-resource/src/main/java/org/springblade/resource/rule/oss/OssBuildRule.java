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
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.oss.enums.OssEnum;
import org.springblade.resource.pojo.entity.Oss;
import org.springblade.resource.rule.context.OssContext;

/**
 * Oss构建判断
 *
 * @author Chill
 */
@LiteflowComponent(id = "ossBuildRule", name = "OSS构建条件判断")
public class OssBuildRule extends NodeSwitchComponent {

	@Override
	public String processSwitch() {
		OssContext contextBean = this.getContextBean(OssContext.class);
		Oss oss = contextBean.getOss();
		if (oss.getCategory() == OssEnum.MINIO.getCategory()) {
			return "minioRule";
		} else if (oss.getCategory() == OssEnum.QINIU.getCategory()) {
			return "qiniuOssRule";
		} else if (oss.getCategory() == OssEnum.ALI.getCategory()) {
			return "aliOssRule";
		} else if (oss.getCategory() == OssEnum.TENCENT.getCategory()) {
			return "tencentCosRule";
		} else if (oss.getCategory() == OssEnum.HUAWEI.getCategory()) {
			return "huaweiObsRule";
		} else if (oss.getCategory() == OssEnum.AMAZONS3.getCategory()) {
			return "amazonS3Rule";
		}
		throw new ServiceException("未找到OSS配置");
	}
}
