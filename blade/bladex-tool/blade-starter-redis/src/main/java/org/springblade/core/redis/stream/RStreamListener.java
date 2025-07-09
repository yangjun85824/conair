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

import java.lang.annotation.*;

/**
 * 基于 redis 的 stream 监听
 *
 * @author L.cm
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RStreamListener {

	/**
	 * Queue name
	 *
	 * @return String
	 */
	String name();

	/**
	 * consumer group，默认为服务名 + 环境
	 *
	 * @return String
	 */
	String group() default "";

	/**
	 * 消息方式，集群模式和广播模式，如果想让所有订阅者收到所有消息，广播是一个不错的选择。
	 *
	 * @return MessageModel
	 */
	MessageModel messageModel() default MessageModel.CLUSTERING;

	/**
	 * offsetModel，默认：LAST_CONSUMED
	 *
	 * <p>
	 * 0-0 : 从开始的地方读。
	 * $ ：表示从尾部开始消费，只接受新消息，当前 Stream 消息会全部忽略。
	 * > : 读取所有新到达的元素，这些元素的id大于消费组使用的最后一个元素。
	 * </p>
	 *
	 * @return ReadOffsetModel
	 */
	ReadOffsetModel offsetModel() default ReadOffsetModel.LAST_CONSUMED;

	/**
	 * 自动 ack
	 *
	 * @return boolean
	 */
	boolean autoAcknowledge() default false;

	/**
	 * 读取原始的 bytes 数据
	 *
	 * @return boolean
	 */
	boolean readRawBytes() default false;

}
