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
package org.springblade.core.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import okhttp3.Call;
import okhttp3.Request;
import org.springblade.core.tool.utils.Exceptions;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Exchange
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class Exchange {
	private BiConsumer<Request, IOException> failedBiConsumer = (r, e) -> {};
	private final Call call;

	public Exchange onFailed(BiConsumer<Request, IOException> failConsumer) {
		this.failedBiConsumer = failConsumer;
		return this;
	}

	public <R> R onResponse(Function<ResponseSpec, R> func) {
		try (HttpResponse response = new HttpResponse(call.execute())) {
			return func.apply(response);
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	@Nullable
	public <R> R onSuccess(Function<ResponseSpec, R> func) {
		try (HttpResponse response = new HttpResponse(call.execute())) {
			return func.apply(response);
		} catch (IOException e) {
			failedBiConsumer.accept(call.request(), e);
			return null;
		}
	}

	@Nullable
	public <R> R onSuccessful(Function<ResponseSpec, R> func) {
		try (HttpResponse response = new HttpResponse(call.execute())) {
			if (response.isOk()) {
				return func.apply(response);
			} else {
				failedBiConsumer.accept(call.request(), new IOException(response.toString()));
			}
		} catch (IOException e) {
			failedBiConsumer.accept(call.request(), e);
		}
		return null;
	}

	public <R> Optional<R> onSuccessOpt(Function<ResponseSpec, R> func) {
		return Optional.ofNullable(this.onSuccess(func));
	}

	public <R> Optional<R> onSuccessfulOpt(Function<ResponseSpec, R> func) {
		return Optional.ofNullable(this.onSuccessful(func));
	}

	/**
	 * Returns body String.
	 *
	 * @return body String
	 */
	public String asString() {
		return onResponse(ResponseSpec::asString);
	}

	/**
	 * Returns body to byte arrays.
	 *
	 * @return byte arrays
	 */
	public byte[] asBytes() {
		return onResponse(ResponseSpec::asBytes);
	}

	/**
	 * Returns body to JsonNode.
	 *
	 * @return JsonNode
	 */
	public JsonNode asJsonNode() {
		return onResponse(ResponseSpec::asJsonNode);
	}

	/**
	 * Returns body to Object.
	 *
	 * @param valueType value value type
	 * @return Object
	 */
	public <T> T asValue(Class<T> valueType) {
		return onResponse(responseSpec -> responseSpec.asValue(valueType));
	}

	/**
	 * Returns body to Object.
	 *
	 * @param typeReference value Type Reference
	 * @return Object
	 */
	public <T> T asValue(TypeReference<T> typeReference) {
		return onResponse(responseSpec -> responseSpec.asValue(typeReference));
	}

	/**
	 * Returns body to List.
	 *
	 * @param valueType value type
	 * @return List
	 */
	public <T> List<T> asList(Class<T> valueType) {
		return onResponse(responseSpec -> responseSpec.asList(valueType));
	}

	/**
	 * Returns body to Map.
	 *
	 * @param keyClass  key type
	 * @param valueType value type
	 * @return Map
	 */
	public <K, V> Map<K, V> asMap(Class<?> keyClass, Class<?> valueType) {
		return onResponse(responseSpec -> responseSpec.asMap(keyClass, valueType));
	}

	/**
	 * Returns body to Map.
	 *
	 * @param valueType value 类型
	 * @return Map
	 */
	public <V> Map<String, V> asMap(Class<?> valueType) {
		return onResponse(responseSpec -> responseSpec.asMap(valueType));
	}

	/**
	 * 将 xml、heml 转成对象
	 *
	 * @param valueType 对象类
	 * @param <T>       泛型
	 * @return 对象
	 */
	public <T> T asDomValue(Class<T> valueType) {
		return onResponse(responseSpec -> responseSpec.asDomValue(valueType));
	}

	/**
	 * 将 xml、heml 转成对象
	 *
	 * @param valueType 对象类
	 * @param <T>       泛型
	 * @return 对象集合
	 */
	public <T> List<T> asDomList(Class<T> valueType) {
		return onResponse(responseSpec -> responseSpec.asDomList(valueType));
	}

	/**
	 * toFile.
	 *
	 * @param file File
	 */
	public File toFile(File file) {
		return onResponse(responseSpec -> responseSpec.toFile(file));
	}

	/**
	 * toFile.
	 *
	 * @param path Path
	 */
	public Path toFile(Path path) {
		return onResponse(responseSpec -> responseSpec.toFile(path));
	}

}
