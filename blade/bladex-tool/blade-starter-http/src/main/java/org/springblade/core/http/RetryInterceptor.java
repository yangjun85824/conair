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

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * 重试拦截器，应对代理问题
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class RetryInterceptor implements Interceptor {
	private final RetryPolicy retryPolicy;

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		RetryTemplate template = createRetryTemplate(retryPolicy);
		return template.execute(context -> {
			Response response = chain.proceed(request);
			// 结果集校验
			Predicate<ResponseSpec> respPredicate = retryPolicy.getRespPredicate();
			if (respPredicate == null) {
				return response;
			}
			// copy 一份 body
			ResponseBody body = response.peekBody(Long.MAX_VALUE);
			try (HttpResponse httpResponse = new HttpResponse(response)) {
				if (respPredicate.test(httpResponse)) {
					throw new IOException("Http Retry ResponsePredicate test Failure.");
				}
			}
			return response.newBuilder().body(body).build();
		});
	}

	private static RetryTemplate createRetryTemplate(RetryPolicy policy) {
		RetryTemplate template = new RetryTemplate();
		// 重试策略
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(policy.getMaxAttempts());
		// 设置间隔策略
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(policy.getSleepMillis());
		template.setRetryPolicy(retryPolicy);
		template.setBackOffPolicy(backOffPolicy);
		return template;
	}
}
