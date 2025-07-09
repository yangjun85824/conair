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
package org.springblade.core.http.util;

import lombok.extern.slf4j.Slf4j;
import org.springblade.core.http.Exchange;
import org.springblade.core.http.FormBuilder;
import org.springblade.core.http.HttpRequest;

import java.util.Map;

/**
 * Http请求工具类
 *
 * @author Chill
 */
@Slf4j
public class HttpUtil {

	/**
	 * GET
	 *
	 * @param url     请求的url
	 * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return String
	 */
	public static String get(String url, Map<String, Object> queries) {
		return get(url, null, queries);
	}

	/**
	 * GET
	 *
	 * @param url     请求的url
	 * @param header  请求头
	 * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return String
	 */
	public static String get(String url, Map<String, String> header, Map<String, Object> queries) {
		// 添加请求头
		HttpRequest httpRequest = HttpRequest.get(url);
		if (header != null && !header.keySet().isEmpty()) {
			header.forEach(httpRequest::addHeader);
		}
		// 添加参数
		httpRequest.queryMap(queries);
		return httpRequest.execute().asString();
	}

	/**
	 * POST
	 *
	 * @param url    请求的url
	 * @param params post form 提交的参数
	 * @return String
	 */
	public static String post(String url, Map<String, Object> params) {
		return exchange(url, null, params).asString();
	}

	/**
	 * POST
	 *
	 * @param url    请求的url
	 * @param header 请求头
	 * @param params post form 提交的参数
	 * @return String
	 */
	public static String post(String url, Map<String, String> header, Map<String, Object> params) {
		return exchange(url, header, params).asString();
	}

	/**
	 * POST请求发送JSON数据
	 *
	 * @param url  请求的url
	 * @param json 请求的json串
	 * @return String
	 */
	public static String postJson(String url, String json) {
		return exchange(url, null, json).asString();
	}

	/**
	 * POST请求发送JSON数据
	 *
	 * @param url    请求的url
	 * @param header 请求头
	 * @param json   请求的json串
	 * @return String
	 */
	public static String postJson(String url, Map<String, String> header, String json) {
		return exchange(url, header, json).asString();
	}

	public static Exchange exchange(String url, Map<String, String> header, Map<String, Object> params) {
		HttpRequest httpRequest = HttpRequest.post(ensureHttpUrl(url));
		//添加请求头
		if (header != null && !header.keySet().isEmpty()) {
			header.forEach(httpRequest::addHeader);
		}
		FormBuilder formBuilder = httpRequest.formBuilder();
		//添加参数
		if (params != null && !params.keySet().isEmpty()) {
			params.forEach(formBuilder::add);
		}
		return formBuilder.execute();
	}

	public static Exchange exchange(String url, Map<String, String> header, String content) {
		HttpRequest httpRequest = HttpRequest.post(ensureHttpUrl(url));
		//添加请求头
		if (header != null && !header.keySet().isEmpty()) {
			header.forEach(httpRequest::addHeader);
		}
		return httpRequest.bodyString(content).execute();
	}

	/**
	 * 确保URL具有http或https协议头
	 *
	 * @param url 要处理的URL字符串
	 * @return 处理后的URL字符串
	 */
	public static String ensureHttpUrl(String url) {
		if (url == null || url.isEmpty()) {
			return url;
		}
		if (!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
			return "http://" + url;
		}
		return url;
	}

}
