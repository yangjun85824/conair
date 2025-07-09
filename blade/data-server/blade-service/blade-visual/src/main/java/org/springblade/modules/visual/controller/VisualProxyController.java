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
package org.springblade.modules.visual.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.visual.pojo.entity.VisualProxy;
import org.springblade.modules.visual.tool.RequestUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 接口代理 控制器
 *
 * @author Chill
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/visual")
@Hidden
@Tag(name = "接口代理", description = "接口代理")
public class VisualProxyController extends BladeController {

	/**
	 * 接口代理
	 */
	@RequestMapping("/proxy")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "接口代理", description = "接口代理")
	public Object proxy(@RequestBody VisualProxy proxy) {
		// 获取参数
		String url = proxy.getUrl();
		String method = proxy.getMethod();
		Map<String, Object> headers = proxy.getHeaders();
		Map<String, Object> params = proxy.getParams();
		Object data = proxy.getData();
		String request;
		// 获取参数提交格式
		Boolean form = Func.toBoolean(proxy.getHeaders().get("form"), true);
		// 处理GET与DELETE方法请求
		if (StringUtil.equalsIgnoreCase(method, RequestUtil.GET) || StringUtil.equalsIgnoreCase(method, RequestUtil.DELETE)) {
			request = RequestUtil.request(url, headers, params, method);
		} else {
			// 处理POST与PUT方法请求
			if (form) {
				request = RequestUtil.requestForm(url + StringPool.QUESTION_MARK + StringUtil.trimAllWhitespace(String.valueOf(data)), headers, params, method);
			} else {
				request = RequestUtil.requestJson(url, headers, JsonUtil.toJson(data), method);
			}
		}
		// 判断返回结果集
		if (StringUtil.isBlank(request)) {
			return Kv.create().set("code", 400).set("msg", "获取数据失败,请检查参数配置!");
		} else {
			Map<String, Object> result = JsonUtil.toMap(request);
			return CollectionUtil.isEmpty(result) ? request : result;
		}
	}

}
