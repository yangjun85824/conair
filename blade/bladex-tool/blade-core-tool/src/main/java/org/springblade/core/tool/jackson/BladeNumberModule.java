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
package org.springblade.core.tool.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 大整数序列化为 String 字符串，避免浏览器丢失精度
 *
 * <p>
 * 前端建议采用：
 * bignumber 库： https://github.com/MikeMcl/bignumber.js
 * decimal.js 库： https://github.com/MikeMcl/decimal.js
 * </p>
 *
 * @author L.cm
 */
public class BladeNumberModule extends SimpleModule {
	public static final BladeNumberModule INSTANCE = new BladeNumberModule();

	public BladeNumberModule() {
		super(BladeNumberModule.class.getName());
		// Long 和 BigInteger 采用定制的逻辑序列化，避免超过js的精度
		this.addSerializer(Long.class, BigNumberSerializer.instance);
		this.addSerializer(Long.TYPE, BigNumberSerializer.instance);
		this.addSerializer(BigInteger.class, BigNumberSerializer.instance);
		// BigDecimal 采用 toString 避免精度丢失，前端采用 decimal.js 来计算。
		this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
	}
}
