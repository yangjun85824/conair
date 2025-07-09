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

package org.springblade.core.redis.stream;

import org.springframework.data.redis.connection.RedisStreamCommands;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 默认的 RStreamTemplate
 *
 * @author L.cm
 */
public class DefaultRStreamTemplate implements RStreamTemplate {
	private static final RedisCustomConversions CUSTOM_CONVERSIONS = new RedisCustomConversions();
	private final RedisTemplate<String, Object> redisTemplate;
	private final StreamOperations<String, String, Object> streamOperations;

	public DefaultRStreamTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.streamOperations = redisTemplate.opsForStream();
	}

	@Override
	public RecordId send(Record<String, ?> record) {
		// 1. MapRecord
		if (record instanceof MapRecord) {
			return streamOperations.add(record);
		}
		String stream = Objects.requireNonNull(record.getStream(), "RStreamTemplate send stream name is null.");
		Object recordValue = Objects.requireNonNull(record.getValue(), "RStreamTemplate send stream: " + stream + " value is null.");
		Class<?> valueClass = recordValue.getClass();
		// 2. 普通类型的 ObjectRecord
		if (CUSTOM_CONVERSIONS.isSimpleType(valueClass)) {
			return streamOperations.add(record);
		}
		// 3. 自定义类型处理
		Map<String, Object> payload = new HashMap<>();
		payload.put(RStreamTemplate.OBJECT_PAYLOAD_KEY, recordValue);
		MapRecord<String, String, Object> mapRecord = MapRecord.create(stream, payload);
		return streamOperations.add(mapRecord);
	}

	@Override
	public RecordId send(String name, String key, byte[] data, RedisStreamCommands.XAddOptions options) {
		RedisSerializer<String> stringSerializer = StringRedisSerializer.UTF_8;
		byte[] nameBytes = Objects.requireNonNull(stringSerializer.serialize(name), "redis stream name is null.");
		byte[] keyBytes = Objects.requireNonNull(stringSerializer.serialize(key), "redis stream key is null.");
		Map<byte[], byte[]> mapDate = Collections.singletonMap(keyBytes, data);
		return redisTemplate.execute((RedisCallback<RecordId>) redis -> {
			RedisStreamCommands streamCommands = redis.streamCommands();
			return streamCommands.xAdd(MapRecord.create(nameBytes, mapDate), options);
		});
	}

	@Override
	public Long delete(String name, String... recordIds) {
		return streamOperations.delete(name, recordIds);
	}

	@Override
	public Long delete(String name, RecordId... recordIds) {
		return streamOperations.delete(name, recordIds);
	}

	@Override
	public Long trim(String name, long count, boolean approximateTrimming) {
		return streamOperations.trim(name, count, approximateTrimming);
	}

	@Override
	public Long acknowledge(String name, String group, String... recordIds) {
		return streamOperations.acknowledge(name, group, recordIds);
	}

	@Override
	public Long acknowledge(String name, String group, RecordId... recordIds) {
		return streamOperations.acknowledge(name, group, recordIds);
	}

	@Override
	public Long acknowledge(String group, Record<String, ?> record) {
		return streamOperations.acknowledge(group, record);
	}

}
