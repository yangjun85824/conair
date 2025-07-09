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
package org.springblade.core.tool.tuple;

import lombok.*;

/**
 * tuple Pair
 *
 * @author L.cm
 **/
@Getter
@ToString
@EqualsAndHashCode
public class Pair<L, R> {
	private static final Pair<Object, Object> EMPTY = new Pair<>(null, null);

	private final L left;
	private final R right;

	/**
	 * Returns an empty pair.
	 */
	@SuppressWarnings("unchecked")
	public static <L, R> Pair<L, R> empty() {
		return (Pair<L, R>) EMPTY;
	}

	/**
	 * Constructs a pair with its left value being {@code left}, or returns an empty pair if
	 * {@code left} is null.
	 *
	 * @return the constructed pair or an empty pair if {@code left} is null.
	 */
	public static <L, R> Pair<L, R> createLeft(L left) {
		if (left == null) {
			return empty();
		} else {
			return new Pair<>(left, null);
		}
	}

	/**
	 * Constructs a pair with its right value being {@code right}, or returns an empty pair if
	 * {@code right} is null.
	 *
	 * @return the constructed pair or an empty pair if {@code right} is null.
	 */
	public static <L, R> Pair<L, R> createRight(R right) {
		if (right == null) {
			return empty();
		} else {
			return new Pair<>(null, right);
		}
	}

	public static <L, R> Pair<L, R> create(L left, R right) {
		if (right == null && left == null) {
			return empty();
		} else {
			return new Pair<>(left, right);
		}
	}

	private Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

}
