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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * redis 配置
 *
 * @author L.cm
 */
@Getter
@Setter
@ConfigurationProperties(BladeRedisProperties.PREFIX)
public class BladeRedisProperties {
	public static final String PREFIX = "blade.redis";

	/**
	 * 序列化方式
	 */
	private SerializerType serializerType = SerializerType.ProtoStuff;
	/**
	 * stream
	 */
	private Stream stream = new Stream();

	public enum SerializerType {
		/**
		 * 默认:ProtoStuff 序列化
		 */
		ProtoStuff,
		/**
		 * json 序列化
		 */
		JSON,
		/**
		 * jdk 序列化
		 */
		JDK
	}

	@Getter
	@Setter
	public static class Stream {
		public static final String PREFIX = BladeRedisProperties.PREFIX + ".stream";
		/**
		 * 是否开启 stream
		 */
		boolean enable = false;
		/**
		 * consumer group，默认：服务名 + 环境
		 */
		String consumerGroup;
		/**
		 * 消费者名称，默认：ip + 端口
		 */
		String consumerName;
		/**
		 * poll 批量大小
		 */
		Integer pollBatchSize;
		/**
		 * poll 超时时间
		 */
		Duration pollTimeout;
	}
}
