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
import okhttp3.internal.Util;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Exceptions;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * ok http 封装，相应结构体
 *
 * @author L.cm
 */
public class HttpResponse implements ResponseSpec, Closeable {
	private final Request request;
	private final Response response;
	private final ResponseBody body;

	HttpResponse(final Response response) {
		this.request = response.request();
		this.response = response;
		this.body = ifNullBodyToEmpty(response.body());
	}

	@Override
	public int code() {
		return response.code();
	}

	@Override
	public String message() {
		return response.message();
	}

	@Override
	public boolean isOk() {
		return response.isSuccessful();
	}

	@Override
	public boolean isRedirect() {
		return response.isRedirect();
	}

	@Override
	public Headers headers() {
		return response.headers();
	}

	@Override
	public List<Cookie> cookies() {
		return Cookie.parseAll(request.url(), this.headers());
	}

	@Override
	public Request rawRequest() {
		return this.request;
	}

	@Override
	public Response rawResponse() {
		return this.response;
	}

	@Override
	public ResponseBody rawBody() {
		return this.body;
	}

	@Override
	public String asString() {
		try {
			return body.string();
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	@Override
	public byte[] asBytes() {
		try {
			return body.bytes();
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	@Override
	public InputStream asStream() {
		return body.byteStream();
	}

	@Override
	public JsonNode asJsonNode() {
		return JsonUtil.readTree(asBytes());
	}

	@Override
	public <T> T asValue(Class<T> valueType) {
		return JsonUtil.readValue(asBytes(), valueType);
	}

	@Override
	public <T> T asValue(TypeReference<T> typeReference) {
		return JsonUtil.readValue(asBytes(), typeReference);
	}

	@Override
	public <T> List<T> asList(Class<T> valueType) {
		return JsonUtil.readList(asBytes(), valueType);
	}

	@Override
	public <K, V> Map<K, V> asMap(Class<?> keyClass, Class<?> valueType) {
		return JsonUtil.readMap(asBytes(), keyClass, valueType);
	}

	@Override
	public <V> Map<String, V> asMap(Class<?> valueType) {
		return this.asMap(String.class, valueType);
	}

	@Override
	public <T> T asDomValue(Class<T> valueType) {
		return DomMapper.readValue(this.asStream(), valueType);
	}

	@Override
	public <T> List<T> asDomList(Class<T> valueType) {
		return DomMapper.readList(this.asStream(), valueType);
	}

	@Override
	public File toFile(File file) {
		toFile(file.toPath());
		return file;
	}

	@Override
	public Path toFile(Path path) {
		try {
			Files.copy(this.asStream(), path);
			return path;
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	@Override
	public MediaType contentType() {
		return body.contentType();
	}

	@Override
	public long contentLength() {
		return body.contentLength();
	}

	@Override
	public String toString() {
		return response.toString();
	}

	private static ResponseBody ifNullBodyToEmpty(@Nullable ResponseBody body) {
		return body == null ? Util.EMPTY_RESPONSE : body;
	}

	@Override
	public void close() throws IOException {
		Util.closeQuietly(this.body);
	}
}
