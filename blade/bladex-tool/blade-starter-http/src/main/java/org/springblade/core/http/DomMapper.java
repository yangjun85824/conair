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
package org.springblade.core.http;

import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springblade.core.tool.utils.Exceptions;
import org.springframework.cglib.proxy.Enhancer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫 xml 转 bean 基于 jsoup
 *
 * @author L.cm
 */
public class DomMapper {

	/**
	 * Returns body to jsoup Document.
	 *
	 * @return Document
	 */
	public static Document asDocument(ResponseSpec response) {
		return readDocument(response.asString());
	}

	/**
	 * 将流读取为 jsoup Document
	 *
	 * @param inputStream InputStream
	 * @return Document
	 */
	public static Document readDocument(InputStream inputStream) {
		try {
			return DataUtil.load(inputStream, StandardCharsets.UTF_8.name(), "");
		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 将 html 字符串读取为 jsoup Document
	 *
	 * @param html String
	 * @return Document
	 */
	public static Document readDocument(String html) {
		return Parser.parse(html, "");
	}

	/**
	 * 读取 xml 信息为 java Bean
	 *
	 * @param inputStream InputStream
	 * @param clazz       bean Class
	 * @param <T>         泛型
	 * @return 对象
	 */
	public static <T> T readValue(InputStream inputStream, final Class<T> clazz) {
		return readValue(readDocument(inputStream), clazz);
	}

	/**
	 * 读取 xml 信息为 java Bean
	 *
	 * @param html  html String
	 * @param clazz bean Class
	 * @param <T>   泛型
	 * @return 对象
	 */
	public static <T> T readValue(String html, final Class<T> clazz) {
		return readValue(readDocument(html), clazz);
	}

	/**
	 * 读取 xml 信息为 java Bean
	 *
	 * @param doc   xml element
	 * @param clazz bean Class
	 * @param <T>   泛型
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readValue(final Element doc, final Class<T> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setUseCache(true);
		enhancer.setCallback(new CssQueryMethodInterceptor(clazz, doc));
		return (T) enhancer.create();
	}

	/**
	 * 读取 xml 信息为 java Bean
	 *
	 * @param <T>         泛型
	 * @param inputStream InputStream
	 * @param clazz       bean Class
	 * @return 对象
	 */
	public static <T> List<T> readList(InputStream inputStream, final Class<T> clazz) {
		return readList(readDocument(inputStream), clazz);
	}

	/**
	 * 读取 xml 信息为 java Bean
	 *
	 * @param <T>   泛型
	 * @param html  html String
	 * @param clazz bean Class
	 * @return 对象
	 */
	public static <T> List<T> readList(String html, final Class<T> clazz) {
		return readList(readDocument(html), clazz);
	}

	/**
	 * 读取 xml 信息为 java Bean
	 *
	 * @param doc   xml element
	 * @param clazz bean Class
	 * @param <T>   泛型
	 * @return 对象列表
	 */
	public static <T> List<T> readList(Element doc, Class<T> clazz) {
		CssQuery annotation = clazz.getAnnotation(CssQuery.class);
		if (annotation == null) {
			throw new IllegalArgumentException("DomMapper readList " + clazz + " mast has annotation @CssQuery.");
		}
		String cssQueryValue = annotation.value();
		Elements elements = doc.select(cssQueryValue);
		List<T> valueList = new ArrayList<>();
		for (Element element : elements) {
			valueList.add(readValue(element, clazz));
		}
		return valueList;
	}

}
