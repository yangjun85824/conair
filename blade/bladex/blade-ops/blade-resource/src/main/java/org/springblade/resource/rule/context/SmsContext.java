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
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.sms.SmsTemplate;
import org.springblade.core.sms.props.SmsProperties;
import org.springblade.resource.pojo.entity.Sms;

import java.util.Map;

/**
 * Sms上下文
 *
 * @author Chill
 */
@Data
public class SmsContext {
	/**
	 * 是否有缓存
	 */
	private Boolean isCached;
	/**
	 * sms数据
	 */
	private Sms sms;
	/**
	 * sms接口
	 */
	private SmsTemplate smsTemplate;
	/**
	 * sms配置
	 */
	private SmsProperties smsProperties;
	/**
	 * redis工具
	 */
	private BladeRedis bladeRedis;
	/**
	 * SmsTemplate配置缓存池
	 */
	private Map<String, SmsTemplate> templatePool;
	/**
	 * sms配置缓存池
	 */
	private Map<String, Sms> smsPool;


}
