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
import okhttp3.*;

import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 相应接口
 *
 * @author L.cm
 */
public interface ResponseSpec {

	/**
	 * Returns the HTTP code.
	 *
	 * @return code
	 */
	int code();

	/**
	 * Returns the HTTP status message.
	 *
	 * @return message
	 */
	String message();

	/**
	 * Returns the HTTP isSuccessful.
	 *
	 * @return boolean
	 */
	default boolean isOk() {
		return false;
	}

	/**
	 * Returns the is Redirect.
	 *
	 * @return is Redirect
	 */
	boolean isRedirect();

	/**
	 * Returns the Headers.
	 *
	 * @return Headers
	 */
	Headers headers();

	/**
	 * Headers Consumer.
	 *
	 * @param consumer Consumer
	 * @return Headers
	 */
	default ResponseSpec headers(Consumer<Headers> consumer) {
		consumer.accept(this.headers());
		return this;
	}

	/**
	 * Returns the Cookies.
	 *
	 * @return Cookie List
	 */
	List<Cookie> cookies();

	/**
	 * 读取消费 cookie
	 *
	 * @param consumer Consumer
	 * @return ResponseSpec
	 */
	default ResponseSpec cookies(Consumer<List<Cookie>> consumer) {
		consumer.accept(this.cookies());
		return this;
	}

	/**
	 * Returns body String.
	 *
	 * @return body String
	 */
	String asString();

	/**
	 * Returns body to byte arrays.
	 *
	 * @return byte arrays
	 */
	byte[] asBytes();

	/**
	 * Returns body to InputStream.
	 *
	 * @return InputStream
	 */
	InputStream asStream();

	/**
	 * Returns body to JsonNode.
	 *
	 * @return JsonNode
	 */
	JsonNode asJsonNode();

	/**
	 * Returns body to Object.
	 *
	 * @param valueType value value type
	 * @return Object
	 */
	@Nullable
	<T> T asValue(Class<T> valueType);

	/**
	 * Returns body to Object.
	 *
	 * @param typeReference value Type Reference
	 * @return Object
	 */
	@Nullable
	<T> T asValue(TypeReference<T> typeReference);

	/**
	 * Returns body to List.
	 *
	 * @param valueType value type
	 * @return List
	 */
	<T> List<T> asList(Class<T> valueType);

	/**
	 * Returns body to Map.
	 *
	 * @param keyClass  key type
	 * @param valueType value type
	 * @return Map
	 */
	<K, V> Map<K, V> asMap(Class<?> keyClass, Class<?> valueType);

	/**
	 * Returns body to Map.
	 *
	 * @param valueType value 类型
	 * @return Map
	 */
	<V> Map<String, V> asMap(Class<?> valueType);

	/**
	 * 将 xml、heml 转成对象
	 *
	 * @param valueType 对象类
	 * @param <T>       泛型
	 * @return 对象
	 */
	<T> T asDomValue(Class<T> valueType);

	/**
	 * 将 xml、heml 转成对象
	 *
	 * @param valueType 对象类
	 * @param <T>       泛型
	 * @return 对象集合
	 */
	<T> List<T> asDomList(Class<T> valueType);

	/**
	 * toFile.
	 *
	 * @param file File
	 */
	File toFile(File file);

	/**
	 * toFile.
	 *
	 * @param path Path
	 */
	Path toFile(Path path);

	/**
	 * Returns contentType.
	 *
	 * @return contentType
	 */
	@Nullable
	MediaType contentType();

	/**
	 * Returns contentLength.
	 *
	 * @return contentLength
	 */
	long contentLength();

	/**
	 * Returns rawRequest.
	 *
	 * @return Request
	 */
	Request rawRequest();

	/**
	 * rawRequest Consumer.
	 *
	 * @param consumer Consumer
	 * @return ResponseSpec
	 */
	@Nullable
	default ResponseSpec rawRequest(Consumer<Request> consumer) {
		consumer.accept(this.rawRequest());
		return this;
	}

	/**
	 * Returns rawResponse.
	 *
	 * @return Response
	 */
	Response rawResponse();

	/**
	 * rawResponse Consumer.
	 *
	 * @param consumer Consumer
	 * @return Response
	 */
	default ResponseSpec rawResponse(Consumer<Response> consumer) {
		consumer.accept(this.rawResponse());
		return this;
	}

	/**
	 * Returns rawBody.
	 *
	 * @return ResponseBody
	 */
	@Nullable
	ResponseBody rawBody();

	/**
	 * rawBody Consumer.
	 *
	 * @param consumer Consumer
	 * @return ResponseBody
	 */
	@Nullable
	default ResponseSpec rawBody(Consumer<ResponseBody> consumer) {
		consumer.accept(this.rawBody());
		return this;
	}

}
