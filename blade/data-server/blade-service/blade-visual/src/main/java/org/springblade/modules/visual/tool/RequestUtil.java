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
package org.springblade.modules.visual.tool;


import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;

import java.util.Map;

/**
 * Http请求工具类
 *
 * @author Chill
 */
@Slf4j
public class RequestUtil {

	public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static String GET = "GET";
	public static String POST = "POST";
	public static String PUT = "PUT";
	public static String DELETE = "DELETE";


	/**
	 * request
	 *
	 * @param url     请求url
	 * @param header  请求头
	 * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
	 * @param method  请求方法(get delete)
	 * @return String
	 */
	public static String request(String url, Map<String, Object> header, Map<String, Object> queries, String method) {

		Request.Builder builder = new Request.Builder().url(requestUrl(url, queries));

		if (header != null && header.keySet().size() > 0) {
			header.forEach((k, v) -> builder.addHeader(k, String.valueOf(v)));
		}

		if (StringUtil.equalsIgnoreCase(method, GET)) {
			builder.get();
		} else if (StringUtil.equalsIgnoreCase(method, DELETE)) {
			builder.delete();
		}

		return getBody(builder.build());
	}

	/**
	 * requestUrl
	 *
	 * @param url     请求url
	 * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return String
	 */
	public static String requestUrl(String url, Map<String, Object> queries) {
		StringBuffer sb = new StringBuffer(url).append(StringPool.QUESTION_MARK);
		if (queries != null && queries.keySet().size() > 0) {
			queries.forEach((k, v) -> sb.append("&").append(k).append("=").append(v));
		}
		return sb.toString();
	}

	/**
	 * requestForm
	 *
	 * @param url    请求url
	 * @param header 请求头
	 * @param params post put form 提交的参数
	 * @param method 请求方法(post put)
	 * @return String
	 */
	public static String requestForm(String url, Map<String, Object> header, Map<String, Object> params, String method) {
		FormBody.Builder formBuilder = new FormBody.Builder();
		//添加参数
		if (params != null && params.keySet().size() > 0) {
			params.forEach((k, v) -> formBuilder.add(k, String.valueOf(v)));
		}

		Request.Builder builder = new Request.Builder().url(url);

		if (header != null && header.keySet().size() > 0) {
			header.forEach((k, v) -> builder.addHeader(k, String.valueOf(v)));
		}

		if (StringUtil.equalsIgnoreCase(method, POST)) {
			builder.post(formBuilder.build());
		} else if (StringUtil.equalsIgnoreCase(method, PUT)) {
			builder.put(formBuilder.build());
		}

		return getBody(builder.build());
	}

	/**
	 * 请求发送JSON数据
	 *
	 * @param url    请求url
	 * @param header 请求头
	 * @param json   请求json串
	 * @param method 请求方法(post put)
	 * @return String
	 */
	public static String requestJson(String url, Map<String, Object> header, String json, String method) {
		return requestContent(url, header, json, JSON, method);
	}

	/**
	 * 发送请求
	 *
	 * @param url       请求的url
	 * @param header    请求头
	 * @param content   请求内容
	 * @param mediaType 请求类型
	 * @param method    请求方法(post put)
	 * @return String
	 */
	public static String requestContent(String url, Map<String, Object> header, String content, MediaType mediaType, String method) {
		RequestBody requestBody = RequestBody.create(content, mediaType);
		Request.Builder builder = new Request.Builder().url(url);
		if (header != null && header.keySet().size() > 0) {
			header.forEach((k, v) -> builder.addHeader(k, String.valueOf(v)));
		}
		if (StringUtil.equalsIgnoreCase(method, POST)) {
			builder.post(requestBody);
		} else if (StringUtil.equalsIgnoreCase(method, PUT)) {
			builder.put(requestBody);
		}
		return getBody(builder.build());
	}

	/**
	 * 获取body
	 *
	 * @param request request
	 * @return String
	 */
	private static String getBody(Request request) {
		String responseBody = "";
		Response response = null;
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			log.error("okhttp3 post error >> ex = {}", e.getMessage());
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return responseBody;
	}

}
