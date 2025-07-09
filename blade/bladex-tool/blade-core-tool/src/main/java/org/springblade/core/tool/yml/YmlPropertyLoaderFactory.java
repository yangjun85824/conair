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
package org.springblade.core.tool.yml;

import org.springblade.core.tool.utils.StringUtil;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * yml配置加载
 *
 * @author lcm
 */
public class YmlPropertyLoaderFactory extends DefaultPropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource encodedResource) throws IOException {
		if (encodedResource == null) {
			return emptyPropertySource(name);
		}
		Resource resource = encodedResource.getResource();
		String fileName = resource.getFilename();
		List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(fileName, resource);
		if (sources.isEmpty()) {
			return emptyPropertySource(fileName);
		}
		// yml 数据存储，合成一个 PropertySource
		Map<String, Object> ymlDataMap = new HashMap<>(32);
		for (PropertySource<?> source : sources) {
			ymlDataMap.putAll(((MapPropertySource) source).getSource());
		}
		return new OriginTrackedMapPropertySource(getSourceName(fileName, name), ymlDataMap);
	}

	private static PropertySource<?> emptyPropertySource(@Nullable String name) {
		return new MapPropertySource(getSourceName(name), Collections.emptyMap());
	}

	private static String getSourceName(String... names) {
		return Stream.of(names)
			.filter(StringUtil::isNotBlank)
			.findFirst()
			.orElse("BladeYmlPropertySource");
	}

}
