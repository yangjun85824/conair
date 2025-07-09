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

package org.springblade.core.redis.stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ReadOffset;

/**
 * stream read offset model
 *
 * @author L.cm
 */
@Getter
@RequiredArgsConstructor
public enum ReadOffsetModel {

	/**
	 * 从开始的地方读
	 */
	START(ReadOffset.from("0-0")),
	/**
	 * 从最近的偏移量读取。
	 */
	LATEST(ReadOffset.latest()),
	/**
	 * 读取所有新到达的元素，这些元素的id大于最后一个消费组的id。
	 */
	LAST_CONSUMED(ReadOffset.lastConsumed());

	/**
	 * readOffset
	 */
	private final ReadOffset readOffset;

}
