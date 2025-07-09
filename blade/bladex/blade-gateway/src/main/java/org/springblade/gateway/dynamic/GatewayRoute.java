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
package org.springblade.gateway.dynamic;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway的路由定义模型
 *
 * @author Chill
 */
@Data
public class GatewayRoute {

	/**
	 * 路由的id
	 */
	private String id;

	/**
	 * 路由断言集合配置
	 */
	private List<GatewayPredicate> predicates = new ArrayList<>();

	/**
	 * 路由过滤器集合配置
	 */
	private List<GatewayFilter> filters = new ArrayList<>();

	/**
	 * 路由规则转发的目标uri
	 */
	private String uri;

	/**
	 * 路由执行的顺序
	 */
	private int order = 0;
}
