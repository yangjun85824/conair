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
package org.springblade.core.tool.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * jackson 默认值为 null 时的处理
 * <p>
 * 主要是为了避免 app 端出现null导致闪退
 * <p>
 * 规则：
 * number -1
 * string ""
 * date ""
 * boolean false
 * array []
 * Object {}
 *
 * @author L.cm
 */
public class BladeBeanSerializerModifier extends BeanSerializerModifier {
	@Override
	public List<BeanPropertyWriter> changeProperties(
		SerializationConfig config, BeanDescription beanDesc,
		List<BeanPropertyWriter> beanProperties) {
		// 循环所有的beanPropertyWriter
		beanProperties.forEach(writer -> {
			// 如果已经有 null 序列化处理如注解：@JsonSerialize(nullsUsing = xxx) 跳过
			if (writer.hasNullSerializer()) {
				return;
			}
			JavaType type = writer.getType();
			Class<?> clazz = type.getRawClass();
			if (type.isTypeOrSubTypeOf(Number.class)) {
				writer.assignNullSerializer(NullJsonSerializers.NUMBER_JSON_SERIALIZER);
			} else if (type.isTypeOrSubTypeOf(Boolean.class)) {
				writer.assignNullSerializer(NullJsonSerializers.BOOLEAN_JSON_SERIALIZER);
			} else if (type.isTypeOrSubTypeOf(Character.class)) {
				writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
			} else if (type.isTypeOrSubTypeOf(String.class)) {
				writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
			} else if (type.isArrayType() || clazz.isArray() || type.isTypeOrSubTypeOf(Collection.class)) {
				writer.assignNullSerializer(NullJsonSerializers.ARRAY_JSON_SERIALIZER);
			} else if (type.isTypeOrSubTypeOf(OffsetDateTime.class)) {
				writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
			} else if (type.isTypeOrSubTypeOf(Date.class) || type.isTypeOrSubTypeOf(TemporalAccessor.class)) {
				writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
			} else {
				writer.assignNullSerializer(NullJsonSerializers.OBJECT_JSON_SERIALIZER);
			}
		});
		return super.changeProperties(config, beanDesc, beanProperties);
	}

	public interface NullJsonSerializers {

		JsonSerializer<Object> STRING_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString(StringPool.EMPTY);
			}
		};

		JsonSerializer<Object> NUMBER_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeNumber(StringUtil.INDEX_NOT_FOUND);
			}
		};

		JsonSerializer<Object> BOOLEAN_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeObject(Boolean.FALSE);
			}
		};

		JsonSerializer<Object> ARRAY_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeStartArray();
				gen.writeEndArray();
			}
		};

		JsonSerializer<Object> OBJECT_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeStartObject();
				gen.writeEndObject();
			}
		};

	}

}
