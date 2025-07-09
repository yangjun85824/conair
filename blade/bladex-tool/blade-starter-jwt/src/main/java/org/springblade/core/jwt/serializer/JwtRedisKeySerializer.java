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

package org.springblade.core.jwt.serializer;

import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 将redis key序列化为字符串
 *
 * <p>
 * spring cache中的简单基本类型直接使用 StringRedisSerializer 会有问题
 * </p>
 *
 * @author L.cm
 */
public class JwtRedisKeySerializer implements RedisSerializer<Object> {
	private final Charset charset;
	private final ConversionService converter;

	public JwtRedisKeySerializer() {
		this(StandardCharsets.UTF_8);
	}

	public JwtRedisKeySerializer(Charset charset) {
		Objects.requireNonNull(charset, "Charset must not be null");
		this.charset = charset;
		this.converter = DefaultConversionService.getSharedInstance();
	}

	@Override
	public Object deserialize(byte[] bytes) {
		// redis keys 会用到反序列化
		if (bytes == null) {
			return null;
		}
		return new String(bytes, charset);
	}

	@Override
	public byte[] serialize(Object object) {
		String key;
		if (object instanceof SimpleKey) {
			key = "";
		} else if (object instanceof String) {
			key = (String) object;
		} else {
			key = converter.convert(object, String.class);
		}
		return Objects.requireNonNull(key).getBytes(this.charset);
	}

}
