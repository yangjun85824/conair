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

import okhttp3.Call;
import okhttp3.Request;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 异步执行器
 *
 * @author L.cm
 */
public class AsyncCall {
	private static final Consumer<ResponseSpec> DEFAULT_CONSUMER = (r) -> {};
	private static final BiConsumer<Request, IOException> DEFAULT_FAIL_CONSUMER = (r, e) -> {};
	private final Call call;
	private Consumer<ResponseSpec> successConsumer;
	private Consumer<ResponseSpec> responseConsumer;
	private BiConsumer<Request, IOException> failedBiConsumer;

	AsyncCall(Call call) {
		this.call = call;
		this.successConsumer = DEFAULT_CONSUMER;
		this.responseConsumer = DEFAULT_CONSUMER;
		this.failedBiConsumer = DEFAULT_FAIL_CONSUMER;
	}

	public void onSuccessful(Consumer<ResponseSpec> consumer) {
		this.successConsumer = consumer;
		this.execute();
	}

	public void onResponse(Consumer<ResponseSpec> consumer) {
		this.responseConsumer = consumer;
		this.execute();
	}

	public AsyncCall onFailed(BiConsumer<Request, IOException> biConsumer) {
		this.failedBiConsumer = biConsumer;
		return this;
	}

	private void execute() {
		call.enqueue(new AsyncCallback(this));
	}

	void onResponse(HttpResponse httpResponse) {
		responseConsumer.accept(httpResponse);
	}

	void onSuccessful(HttpResponse httpResponse) {
		successConsumer.accept(httpResponse);
	}

	void onFailure(Request request, IOException e) {
		failedBiConsumer.accept(request, e);
	}
}
