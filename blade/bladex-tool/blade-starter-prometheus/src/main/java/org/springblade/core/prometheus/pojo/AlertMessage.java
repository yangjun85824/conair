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

package org.springblade.core.prometheus.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * alert hook
 *
 * @author L.cm
 */
@Data
public class AlertMessage implements Serializable {

	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 由于 “max_alerts” 而截断了多少警报
	 */
	private Integer truncatedAlerts;
	/**
	 * 分组 key
	 */
	private String groupKey;
	/**
	 * 状态 resolved|firing
	 */
	private String status;
	/**
	 * 接收者
	 */
	private String receiver;
	/**
	 * 分组 labels
	 */
	private Map<String, String> groupLabels;
	/**
	 * 通用 label
	 */
	private Map<String, String> commonLabels;
	/**
	 * 通用注解
	 */
	private Map<String, String> commonAnnotations;
	/**
	 * 扩展 url 地址
	 */
	private String externalURL;
	/**
	 * alerts
	 */
	private List<AlertInfo> alerts;

}
