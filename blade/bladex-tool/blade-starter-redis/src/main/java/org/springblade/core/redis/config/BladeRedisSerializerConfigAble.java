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

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis 序列化
 *
 * @author L.cm
 */
public interface BladeRedisSerializerConfigAble {

	/**
	 * JSON序列化类型字段
	 */
	String TYPE_NAME = "@class";

	/**
	 * 序列化接口
	 *
	 * @param properties 配置
	 * @return RedisSerializer
	 */
	RedisSerializer<Object> redisSerializer(BladeRedisProperties properties);

	/**
	 * 默认的序列化方式
	 *
	 * @param properties 配置
	 * @return RedisSerializer
	 */
	default RedisSerializer<Object> defaultRedisSerializer(BladeRedisProperties properties) {
		BladeRedisProperties.SerializerType serializerType = properties.getSerializerType();
		if (BladeRedisProperties.SerializerType.JDK == serializerType) {
			/**
			 * SpringBoot扩展了ClassLoader，进行分离打包的时候，使用到JdkSerializationRedisSerializer的地方
			 * 会因为ClassLoader的不同导致加载不到Class
			 * 指定使用项目的ClassLoader
			 *
			 * JdkSerializationRedisSerializer默认使用{@link sun.misc.Launcher.AppClassLoader}
			 * SpringBoot默认使用{@link org.springframework.boot.loader.LaunchedURLClassLoader}
			 */
			ClassLoader classLoader = this.getClass().getClassLoader();
			return new JdkSerializationRedisSerializer(classLoader);
		}
		return new GenericJackson2JsonRedisSerializer(TYPE_NAME);
	}
}
