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
package org.springblade.resource.rule.context;

import lombok.Data;
import org.springblade.core.oss.OssTemplate;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.oss.rule.OssRule;
import org.springblade.resource.pojo.entity.Oss;

import java.util.Map;

/**
 * Oss上下文
 *
 * @author Chill
 */
@Data
public class OssContext {
	/**
	 * 是否有缓存
	 */
	private Boolean isCached;
	/**
	 * oss数据
	 */
	private Oss oss;
	/**
	 * oss规则
	 */
	private OssRule ossRule;
	/**
	 * oss接口
	 */
	private OssTemplate ossTemplate;
	/**
	 * oss配置
	 */
	private OssProperties ossProperties;
	/**
	 * OssTemplate配置缓存池
	 */
	private Map<String, OssTemplate> templatePool;
	/**
	 * oss配置缓存池
	 */
	private Map<String, Oss> ossPool;


}
