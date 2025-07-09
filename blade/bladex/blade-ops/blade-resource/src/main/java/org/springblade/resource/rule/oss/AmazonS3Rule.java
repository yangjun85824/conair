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

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springblade.core.oss.OssTemplate;
import org.springblade.core.oss.S3Template;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.oss.rule.OssRule;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.resource.pojo.entity.Oss;
import org.springblade.resource.rule.context.OssContext;

/**
 * Amazon S3 云存储构建类
 *
 * @author Chill
 */
@LiteflowComponent(id = "amazonS3Rule", name = "amazonS3OSS构建")
public class AmazonS3Rule extends NodeComponent {

	@Override
	public void process() throws Exception {
		// 获取上下文
		OssContext contextBean = this.getContextBean(OssContext.class);
		Oss oss = contextBean.getOss();
		OssRule ossRule = contextBean.getOssRule();

		// 创建配置类
		OssProperties ossProperties = new OssProperties();
		ossProperties.setEndpoint(oss.getEndpoint());
		ossProperties.setTransformEndpoint(oss.getTransformEndpoint());
		ossProperties.setAccessKey(oss.getAccessKey());
		ossProperties.setSecretKey(oss.getSecretKey());
		ossProperties.setBucketName(oss.getBucketName());
		ossProperties.setRegion(oss.getRegion());
		// 创建客户端
		AWSCredentials credentials = new BasicAWSCredentials(ossProperties.getAccessKey(), ossProperties.getSecretKey());
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setSignerOverride("AWSS3V4SignerType");
		AmazonS3 amazonS3 = AmazonS3ClientBuilder
			.standard()
			.withEndpointConfiguration(new AwsClientBuilder.
				EndpointConfiguration(ossProperties.getEndpoint(),
				StringUtil.isBlank(ossProperties.getRegion()) ? Regions.DEFAULT_REGION.name() : Regions.fromName(ossProperties.getRegion()).getName()))
			.withPathStyleAccessEnabled(true)
			.withClientConfiguration(clientConfiguration)
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.build();
		OssTemplate ossTemplate = new S3Template(amazonS3, ossRule, ossProperties);

		// 设置上下文
		contextBean.setOssTemplate(ossTemplate);

	}
}
