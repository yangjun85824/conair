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

import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Holder of a value that is computed lazy.
 *
 * @author L.cm
 */
public class Lazy<T> implements Supplier<T>, Serializable {

	@Nullable
	private transient volatile Supplier<? extends T> supplier;
	@Nullable
	private T value;

	/**
	 * Creates new instance of Lazy.
	 *
	 * @param supplier Supplier
	 * @param <T>      泛型标记
	 * @return Lazy
	 */
	public static <T> Lazy<T> of(final Supplier<T> supplier) {
		return new Lazy<>(supplier);
	}

	private Lazy(final Supplier<T> supplier) {
		this.supplier = supplier;
	}

	/**
	 * Returns the value. Value will be computed on first call.
	 *
	 * @return lazy value
	 */
	@Nullable
	@Override
	public T get() {
		return (supplier == null) ? value : computeValue();
	}

	@Nullable
	private synchronized T computeValue() {
		final Supplier<? extends T> s = supplier;
		if (s != null) {
			value = s.get();
			supplier = null;
		}
		return value;
	}

}
