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
package org.springblade.core.oss.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.core.oss.S3Template;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.oss.rule.OssRule;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * S3Configuration
 *
 * @author fanjia2
 * @version 1.0.0
 * @ClassName S3Configuration.java
 * @Description 亚马逊 S3 配置类
 * @createTime 2022年07月04日 11:19:00
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@AutoConfiguration(after = OssConfiguration.class)
@ConditionalOnClass({AmazonS3.class})
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(value = "oss.name", havingValue = "s3")
public class S3Configuration {


	private final OssProperties ossProperties;
	private final OssRule ossRule;


	@Bean
	@SneakyThrows
	@ConditionalOnMissingBean(AmazonS3.class)
	public AmazonS3 amazonS3() {
		AWSCredentials credentials = new BasicAWSCredentials(ossProperties.getAccessKey(), ossProperties.getSecretKey());
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setSignerOverride("AWSS3V4SignerType");
		return AmazonS3ClientBuilder
			.standard()
			.withEndpointConfiguration(new AwsClientBuilder.
				EndpointConfiguration(ossProperties.getEndpoint(),
				StringUtil.isBlank(ossProperties.getRegion()) ? Regions.DEFAULT_REGION.name() : Regions.fromName(ossProperties.getRegion()).getName()))
			.withPathStyleAccessEnabled(true)
			.withClientConfiguration(clientConfiguration)
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.build();
	}

	@Bean
	@ConditionalOnBean({AmazonS3.class})
	@ConditionalOnMissingBean(S3Template.class)
	public S3Template s3Template(AmazonS3 amazonS3) {
		return new S3Template(amazonS3, ossRule, ossProperties);
	}
}
