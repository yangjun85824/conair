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

package org.springblade.core.redis.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.springblade.core.tool.utils.ObjectUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * ProtoStuff 序列化
 *
 * @author L.cm
 */
public class ProtoStuffSerializer implements RedisSerializer<Object> {
	private final Schema<BytesWrapper> schema;

	public ProtoStuffSerializer() {
		this.schema = RuntimeSchema.getSchema(BytesWrapper.class);
	}

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		if (object == null) {
			return null;
		}
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			return ProtobufIOUtil.toByteArray(new BytesWrapper<>(object), schema, buffer);
		} finally {
			buffer.clear();
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (ObjectUtil.isEmpty(bytes)) {
			return null;
		}
		BytesWrapper<Object> wrapper = new BytesWrapper<>();
		ProtobufIOUtil.mergeFrom(bytes, wrapper, schema);
		return wrapper.getValue();
	}
}
