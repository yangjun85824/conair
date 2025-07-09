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
 * Author: DreamLu (596392912@qq.com)
 */

package org.springblade.core.redis.config;

import org.springblade.core.launch.utils.INetUtil;
import org.springblade.core.redis.stream.DefaultRStreamTemplate;
import org.springblade.core.redis.stream.RStreamListenerDetector;
import org.springblade.core.redis.stream.RStreamTemplate;
import org.springblade.core.tool.utils.CharPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.util.ErrorHandler;

import java.time.Duration;

/**
 * redis Stream 配置
 *
 * @author L.cm
 */
@AutoConfiguration
@ConditionalOnProperty(
	prefix = BladeRedisProperties.Stream.PREFIX,
	name = "enable",
	havingValue = "true"
)
public class RedisStreamConfiguration {

	/**
	 * Spring 应用名 prop key
	 */
	private static final String SPRING_APP_NAME_KEY = "spring.application.name";
	/**
	 * The "active profiles" property name.
	 */
	private static final String ACTIVE_PROFILES_PROPERTY = "spring.profiles.active";

	@Bean
	@ConditionalOnMissingBean
	public StreamMessageListenerContainerOptions<String, MapRecord<String, String, byte[]>> streamMessageListenerContainerOptions(BladeRedisProperties properties,
																																  ObjectProvider<ErrorHandler> errorHandlerObjectProvider) {
		StreamMessageListenerContainer.StreamMessageListenerContainerOptionsBuilder<String, MapRecord<String, String, byte[]>> builder = StreamMessageListenerContainerOptions
			.builder()
			.keySerializer(RedisSerializer.string())
			.hashKeySerializer(RedisSerializer.string())
			.hashValueSerializer(RedisSerializer.byteArray());
		BladeRedisProperties.Stream streamProperties = properties.getStream();
		// 批量大小
		Integer pollBatchSize = streamProperties.getPollBatchSize();
		if (pollBatchSize != null && pollBatchSize > 0) {
			builder.batchSize(pollBatchSize);
		}
		// poll 超时时间
		Duration pollTimeout = streamProperties.getPollTimeout();
		if (pollTimeout != null && !pollTimeout.isNegative()) {
			builder.pollTimeout(pollTimeout);
		}
		// errorHandler
		errorHandlerObjectProvider.ifAvailable((builder::errorHandler));
		// TODO L.cm executor
		return builder.build();
	}

	@Bean
	@ConditionalOnMissingBean
	public StreamMessageListenerContainer<String, MapRecord<String, String, byte[]>> streamMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
																													StreamMessageListenerContainerOptions<String, MapRecord<String, String, byte[]>> streamMessageListenerContainerOptions) {
		// 根据配置对象创建监听容器
		return StreamMessageListenerContainer.create(redisConnectionFactory, streamMessageListenerContainerOptions);
	}

	@Bean
	@ConditionalOnMissingBean
	public RStreamListenerDetector streamListenerDetector(StreamMessageListenerContainer<String, MapRecord<String, String, byte[]>> streamMessageListenerContainer,
														  RedisTemplate<String, Object> redisTemplate,
														  ObjectProvider<ServerProperties> serverPropertiesObjectProvider,
														  BladeRedisProperties properties,
														  Environment environment) {
		BladeRedisProperties.Stream streamProperties = properties.getStream();
		// 消费组名称
		String consumerGroup = streamProperties.getConsumerGroup();
		if (StringUtil.isBlank(consumerGroup)) {
			String appName = environment.getRequiredProperty(SPRING_APP_NAME_KEY);
			String profile = environment.getProperty(ACTIVE_PROFILES_PROPERTY);
			consumerGroup = StringUtil.isBlank(profile) ? appName : appName + CharPool.COLON + profile;
		}
		// 消费者名称
		String consumerName = streamProperties.getConsumerName();
		if (StringUtil.isBlank(consumerName)) {
			final StringBuilder consumerNameBuilder = new StringBuilder(INetUtil.getHostIp());
			serverPropertiesObjectProvider.ifAvailable(serverProperties -> {
				consumerNameBuilder.append(CharPool.COLON).append(serverProperties.getPort());
			});
			consumerName = consumerNameBuilder.toString();
		}
		return new RStreamListenerDetector(streamMessageListenerContainer, redisTemplate, consumerGroup, consumerName);
	}

	@Bean
	public RStreamTemplate streamTemplate(RedisTemplate<String, Object> redisTemplate) {
		return new DefaultRStreamTemplate(redisTemplate);
	}

}
