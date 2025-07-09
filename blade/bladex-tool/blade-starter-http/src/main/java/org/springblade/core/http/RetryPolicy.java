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

import lombok.Getter;
import lombok.ToString;
import org.springframework.retry.policy.SimpleRetryPolicy;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * 重试策略
 *
 * @author dream.lu
 */
@Getter
@ToString
public class RetryPolicy {
	public static final RetryPolicy INSTANCE = new RetryPolicy();

	private final int maxAttempts;
	private final long sleepMillis;
	@Nullable
	private final Predicate<ResponseSpec> respPredicate;

	public RetryPolicy() {
		this(null);
	}

	public RetryPolicy(int maxAttempts, long sleepMillis) {
		this(maxAttempts, sleepMillis, null);
	}

	public RetryPolicy(@Nullable Predicate<ResponseSpec> respPredicate) {
		this(SimpleRetryPolicy.DEFAULT_MAX_ATTEMPTS, 0L, respPredicate);
	}

	public RetryPolicy(int maxAttempts, long sleepMillis, @Nullable Predicate<ResponseSpec> respPredicate) {
		this.maxAttempts = maxAttempts;
		this.sleepMillis = sleepMillis;
		this.respPredicate = respPredicate;
	}
}
