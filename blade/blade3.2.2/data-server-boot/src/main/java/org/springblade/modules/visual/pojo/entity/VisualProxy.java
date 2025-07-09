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
package org.springblade.modules.visual.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 接口代理实体类
 *
 * @author Chill
 */
@Data
@Schema(description = "接口代理")
public class VisualProxy implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 请求地址
	 */
	@Schema(description = "请求地址")
	private String url;
	/**
	 * 方法类型
	 */
	@Schema(description = "方法类型")
	private String method;
	/**
	 * 请求头
	 */
	@Schema(description = "请求头")
	private Map<String, Object> headers;
	/**
	 * 参数
	 */
	@Schema(description = "参数")
	private Map<String, Object> params;
	/**
	 * 数据
	 */
	@Schema(description = "数据")
	private Object data;


}
