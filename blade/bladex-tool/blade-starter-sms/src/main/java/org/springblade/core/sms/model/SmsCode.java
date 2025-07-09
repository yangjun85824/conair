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
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.core.sms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 校验信息
 *
 * @author Chill
 */
@Data
@Accessors(chain = true)
public class SmsCode implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 是否成功
	 */
	private boolean success = Boolean.TRUE;

	/**
	 * 变量phone,用于redis进行比对
	 */
	private String phone;

	/**
	 * 变量id,用于redis进行比对
	 */
	private String id;

	/**
	 * 变量值,用于redis进行比对
	 */
	@JsonIgnore
	private String value;

}
