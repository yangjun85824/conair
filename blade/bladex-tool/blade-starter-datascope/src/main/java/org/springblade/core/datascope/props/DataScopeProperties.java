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
package org.springblade.core.datascope.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 数据权限参数配置类
 *
 * @author Chill
 */
@Data
@ConfigurationProperties(prefix = "blade.data-scope")
public class DataScopeProperties {

	/**
	 * 开启数据权限
	 */
	private Boolean enabled = true;
	/**
	 * mapper方法匹配关键字
	 */
	private List<String> mapperKey = Arrays.asList("page", "Page", "list", "List");

	/**
	 * mapper过滤
	 */
	private List<String> mapperExclude = Collections.singletonList("FlowMapper");

}
