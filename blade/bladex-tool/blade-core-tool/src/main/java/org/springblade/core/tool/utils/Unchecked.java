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

import org.springblade.core.tool.function.*;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Lambda 受检异常处理
 *
 * <p>
 * https://segmentfault.com/a/1190000007832130
 * https://github.com/jOOQ/jOOL
 * </p>
 *
 * @author L.cm
 */
public class Unchecked {

	public static <T, R> Function<T, R> function(CheckedFunction<T, R> mapper) {
		Objects.requireNonNull(mapper);
		return t -> {
			try {
				return mapper.apply(t);
			} catch (Throwable e) {
				throw Exceptions.unchecked(e);
			}
		};
	}

	public static <T> Consumer<T> consumer(CheckedConsumer<T> mapper) {
		Objects.requireNonNull(mapper);
		return t -> {
			try {
				mapper.accept(t);
			} catch (Throwable e) {
				throw Exceptions.unchecked(e);
			}
		};
	}

	public static <T> Supplier<T> supplier(CheckedSupplier<T> mapper) {
		Objects.requireNonNull(mapper);
		return () -> {
			try {
				return mapper.get();
			} catch (Throwable e) {
				throw Exceptions.unchecked(e);
			}
		};
	}

	public static Runnable runnable(CheckedRunnable runnable) {
		Objects.requireNonNull(runnable);
		return () -> {
			try {
				runnable.run();
			} catch (Throwable e) {
				throw Exceptions.unchecked(e);
			}
		};
	}

	public static <T> Callable<T> callable(CheckedCallable<T> callable) {
		Objects.requireNonNull(callable);
		return () -> {
			try {
				return callable.call();
			} catch (Throwable e) {
				throw Exceptions.unchecked(e);
			}
		};
	}

	public static <T> Comparator<T> comparator(CheckedComparator<T> comparator) {
		Objects.requireNonNull(comparator);
		return (T o1, T o2) -> {
			try {
				return comparator.compare(o1, o2);
			} catch (Throwable e) {
				throw Exceptions.unchecked(e);
			}
		};
	}

}
