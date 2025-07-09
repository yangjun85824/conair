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
package org.springblade.core.auto.factories;

import org.springblade.core.auto.common.MultiSetMap;

import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * spring boot 自动化配置工具类
 *
 * @author L.cm
 */
class FactoriesFiles {
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	/**
	 * 读取 spring.factories 文件
	 *
	 * @param fileObject FileObject
	 * @return MultiSetMap
	 * @throws IOException 异常信息
	 */
	protected static MultiSetMap<String, String> readFactoriesFile(FileObject fileObject, Elements elementUtils) throws IOException {
		// 读取 spring.factories 内容
		Properties properties = new Properties();
		try (InputStream input = fileObject.openInputStream()) {
			properties.load(input);
		}
		MultiSetMap<String, String> multiSetMap = new MultiSetMap<>();
		Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
		for (Map.Entry<Object, Object> objectEntry : entrySet) {
			String key = (String) objectEntry.getKey();
			String value = (String) objectEntry.getValue();
			if (value == null || value.trim().isEmpty()) {
				continue;
			}
			// 解析 spring.factories
			String[] values = value.split(",");
			Set<String> valueSet = Arrays.stream(values)
				.filter(v -> !v.isEmpty())
				.map(String::trim)
				// 校验是否删除文件
				.filter((v) -> Objects.nonNull(elementUtils.getTypeElement(v)))
				.collect(Collectors.toSet());
			multiSetMap.putAll(key.trim(), valueSet);
		}
		return multiSetMap;
	}

	/**
	 * 读取已经存在的 AutoConfiguration imports
	 *
	 * @param fileObject FileObject
	 * @return Set
	 * @throws IOException IOException
	 */
	protected static Set<String> readAutoConfigurationImports(FileObject fileObject) throws IOException {
		Set<String> set = new HashSet<>();
		try (
			InputStream input = fileObject.openInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input))
		) {
			reader.lines()
				.map(String::trim)
				.filter(line -> !line.startsWith("#"))
				.forEach(set::add);
		}
		return set;
	}

	/**
	 * 写出 spring.factories 文件
	 *
	 * @param factories factories 信息
	 * @param output    输出流
	 * @throws IOException 异常信息
	 */
	protected static void writeFactoriesFile(MultiSetMap<String, String> factories,
								   OutputStream output) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, UTF_8));
		Set<String> keySet = factories.keySet();
		for (String key : keySet) {
			Set<String> values = factories.get(key);
			if (values == null || values.isEmpty()) {
				continue;
			}
			writer.write(key);
			writer.write("=\\\n  ");
			StringJoiner joiner = new StringJoiner(",\\\n  ");
			for (String value : values) {
				joiner.add(value);
			}
			writer.write(joiner.toString());
			writer.newLine();
		}
		writer.flush();
		output.close();
	}

	/**
	 * 写出 spring-devtools.properties
	 *
	 * @param projectName 项目名
	 * @param output      输出流
	 * @throws IOException 异常信息
	 */
	protected static void writeDevToolsFile(String projectName,
								  OutputStream output) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, UTF_8));
		String format = "restart.include.%s=/%s[\\\\w-]+\\.jar";
		writer.write(String.format(format, projectName, projectName));
		writer.flush();
		output.close();
	}

	/**
	 * 写出 AutoConfiguration imports
	 *
	 * @param allAutoConfigurationImports allAutoConfigurationImports
	 * @param output                      OutputStream
	 * @throws IOException IOException
	 */
	protected static void writeAutoConfigurationImportsFile(Set<String> allAutoConfigurationImports, OutputStream output) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, UTF_8));
		StringJoiner joiner = new StringJoiner("\n");
		for (String configurationImport : allAutoConfigurationImports) {
			joiner.add(configurationImport);
		}
		writer.write(joiner.toString());
		writer.flush();
	}

}
