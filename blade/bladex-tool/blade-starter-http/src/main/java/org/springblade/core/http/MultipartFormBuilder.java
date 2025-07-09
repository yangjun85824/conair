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

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;

/**
 * 表单构造器
 *
 * @author L.cm
 */
public class MultipartFormBuilder {
	private final HttpRequest request;
	private final MultipartBody.Builder formBuilder;

	MultipartFormBuilder(HttpRequest request) {
		this.request = request;
		this.formBuilder = new MultipartBody.Builder();
	}

	public MultipartFormBuilder add(String name, @Nullable Object value) {
		this.formBuilder.addFormDataPart(name, HttpRequest.handleValue(value));
		return this;
	}

	public MultipartFormBuilder addMap(@Nullable Map<String, Object> formMap) {
		if (formMap != null && !formMap.isEmpty()) {
			formMap.forEach(this::add);
		}
		return this;
	}

	public MultipartFormBuilder add(String name, File file) {
		String fileName = file.getName();
		return add(name, fileName, file);
	}

	public MultipartFormBuilder add(String name, @Nullable String filename, File file) {
		RequestBody fileBody = RequestBody.create(null, file);
		return add(name, filename, fileBody);
	}

	public MultipartFormBuilder add(String name, @Nullable String filename, RequestBody fileBody) {
		this.formBuilder.addFormDataPart(name, filename, fileBody);
		return this;
	}

	public MultipartFormBuilder add(RequestBody body) {
		this.formBuilder.addPart(body);
		return this;
	}

	public MultipartFormBuilder add(@Nullable Headers headers, RequestBody body) {
		this.formBuilder.addPart(headers, body);
		return this;
	}

	public MultipartFormBuilder add(MultipartBody.Part part) {
		this.formBuilder.addPart(part);
		return this;
	}

	public HttpRequest build() {
		formBuilder.setType(MultipartBody.FORM);
		MultipartBody formBody = formBuilder.build();
		this.request.multipartForm(formBody);
		return this.request;
	}

	public Exchange execute() {
		return this.build().execute();
	}

	public AsyncCall async() {
		return this.build().async();
	}
}
