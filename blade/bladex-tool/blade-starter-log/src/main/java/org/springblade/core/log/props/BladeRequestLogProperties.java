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
package org.springblade.core.log.props;

import lombok.Getter;
import lombok.Setter;
import org.springblade.core.launch.log.BladeLogLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志配置
 *
 * @author L.cm
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(BladeLogLevel.REQ_LOG_PROPS_PREFIX)
public class BladeRequestLogProperties {

	/**
	 * 是否开启请求日志
	 */
	private Boolean enabled = true;

	/**
	 * 是否开启异常日志推送
	 */
	private Boolean errorLog = true;

	/**
	 * 日志级别配置，默认：BODY
	 */
	private BladeLogLevel level = BladeLogLevel.BODY;

	/**
	 * 放行url
	 */
	private List<String> skipUrl = new ArrayList<>();
}
