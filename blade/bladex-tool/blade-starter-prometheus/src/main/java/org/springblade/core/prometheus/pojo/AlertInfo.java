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
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 告警模型
 *
 * @author L.cm
 */
@Data
public class AlertInfo implements Serializable {

	/**
	 * 状态 resolved|firing
	 */
	private String status;
	/**
	 * 标签集合
	 */
	private Map<String, String> labels;
	/**
	 * 注释集合
	 */
	private Map<String, String> annotations;
	/**
	 * 开始时间
	 */
	private OffsetDateTime startsAt;
	/**
	 * 结束时间
	 */
	private OffsetDateTime endsAt;
	/**
	 * identifies the entity that caused the alert
	 */
	private String generatorURL;
	/**
	 * fingerprint to identify the alert
	 */
	private String fingerprint;

}
