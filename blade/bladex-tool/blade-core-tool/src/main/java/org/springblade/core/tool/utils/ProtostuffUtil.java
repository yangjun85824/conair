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
package org.springblade.core.tool.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Protostuff 工具类
 *
 * @author L.cm
 */
public class ProtostuffUtil {

	/**
	 * 避免每次序列化都重新申请Buffer空间
	 */
	private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
	/**
	 * 缓存Schema
	 */
	private static final Map<Class<?>, Schema<?>> SCHEMA_CACHE = new ConcurrentHashMap<>();

	/**
	 * 序列化方法，把指定对象序列化成字节数组
	 *
	 * @param obj obj
	 * @param <T> T
	 * @return byte[]
	 */
	@SuppressWarnings("unchecked")
	public static <T> byte[] serialize(T obj) {
		Class<T> clazz = (Class<T>) obj.getClass();
		Schema<T> schema = getSchema(clazz);
		byte[] data;
		try {
			data = ProtobufIOUtil.toByteArray(obj, schema, BUFFER);
		} finally {
			BUFFER.clear();
		}
		return data;
	}

	/**
	 * 反序列化方法，将字节数组反序列化成指定Class类型
	 *
	 * @param data data
	 * @param clazz clazz
	 * @param <T> T
	 * @return T
	 */
	public static <T> T deserialize(byte[] data, Class<T> clazz) {
		Schema<T> schema = getSchema(clazz);
		T obj = schema.newMessage();
		ProtobufIOUtil.mergeFrom(data, obj, schema);
		return obj;
	}

	/**
	 * 获取Schema
	 * @param clazz clazz
	 * @param <T> T
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	private static <T> Schema<T> getSchema(Class<T> clazz) {
		Schema<T> schema = (Schema<T>) SCHEMA_CACHE.get(clazz);
		if (Objects.isNull(schema)) {
			//这个schema通过RuntimeSchema进行懒创建并缓存
			//所以可以一直调用RuntimeSchema.getSchema(),这个方法是线程安全的
			schema = RuntimeSchema.getSchema(clazz);
			if (Objects.nonNull(schema)) {
				SCHEMA_CACHE.put(clazz, schema);
			}
		}
		return schema;
	}

}
