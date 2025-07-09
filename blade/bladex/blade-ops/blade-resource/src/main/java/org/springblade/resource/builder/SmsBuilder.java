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
package org.springblade.resource.builder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.AllArgsConstructor;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.sms.SmsTemplate;
import org.springblade.core.sms.enums.SmsEnum;
import org.springblade.core.sms.enums.SmsStatusEnum;
import org.springblade.core.sms.props.SmsProperties;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.resource.pojo.entity.Sms;
import org.springblade.resource.rule.context.SmsContext;
import org.springblade.resource.service.ISmsService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springblade.core.cache.constant.CacheConstant.RESOURCE_CACHE;

/**
 * Sms短信服务统一构建类
 *
 * @author Chill
 */
@AllArgsConstructor
public class SmsBuilder {

	public static final String SMS_CODE = "sms:code:";
	public static final String SMS_PARAM_KEY = "code";

	private final SmsProperties smsProperties;
	private final ISmsService smsService;
	private final BladeRedis bladeRedis;
	private final FlowExecutor flowExecutor;

	/**
	 * SmsTemplate配置缓存池
	 */
	private final Map<String, SmsTemplate> templatePool = new ConcurrentHashMap<>();

	/**
	 * Sms配置缓存池
	 */
	private final Map<String, Sms> smsPool = new ConcurrentHashMap<>();


	/**
	 * 获取template
	 *
	 * @return SmsTemplate
	 */
	public SmsTemplate template() {
		return template(StringPool.EMPTY);
	}

	/**
	 * 获取template
	 *
	 * @param code 资源编号
	 * @return SmsTemplate
	 */
	public SmsTemplate template(String code) {
		String tenantId = AuthUtil.getTenantId();
		return template(tenantId, code);
	}

	/**
	 * 获取template
	 *
	 * @param tenantId 租户ID
	 * @param code     资源编号
	 * @return SmsTemplate
	 */
	public SmsTemplate template(String tenantId, String code) {
		Sms sms = getSms(tenantId, code);

		SmsContext smsContext = new SmsContext();
		smsContext.setSms(sms);
		smsContext.setSmsProperties(smsProperties);
		smsContext.setSmsPool(smsPool);
		smsContext.setTemplatePool(templatePool);
		smsContext.setBladeRedis(bladeRedis);

		LiteflowResponse resp = flowExecutor.execute2Resp("smsChain", tenantId, smsContext);
		if (resp.isSuccess()) {
			SmsContext contextBean = resp.getFirstContextBean();
			return contextBean.getSmsTemplate();
		} else {
			throw new ServiceException("未获取到对应的短信配置");
		}
	}

	/**
	 * 获取短信实体
	 *
	 * @param tenantId 租户ID
	 * @return Sms
	 */
	public Sms getSms(String tenantId, String code) {
		String key = tenantId;
		LambdaQueryWrapper<Sms> lqw = Wrappers.<Sms>query().lambda().eq(Sms::getTenantId, tenantId);
		// 获取传参的资源编号并查询，若有则返回，若没有则调启用的配置
		String smsCode = StringUtil.isBlank(code) ? WebUtil.getParameter(SMS_PARAM_KEY) : code;
		if (StringUtil.isNotBlank(smsCode)) {
			key = key.concat(StringPool.DASH).concat(smsCode);
			lqw.eq(Sms::getSmsCode, smsCode);
		} else {
			lqw.eq(Sms::getStatus, SmsStatusEnum.ENABLE.getNum());
		}
		Sms sms = CacheUtil.get(RESOURCE_CACHE, SMS_CODE, key, () -> {
			Sms s = smsService.getOne(lqw);
			// 若为空则调用默认配置
			if (s == null || s.getId() == null) {
				Sms defaultSms = new Sms();
				defaultSms.setId(0L);
				defaultSms.setTemplateId(smsProperties.getTemplateId());
				defaultSms.setRegionId(smsProperties.getRegionId());
				defaultSms.setCategory(SmsEnum.of(smsProperties.getName()).getCategory());
				defaultSms.setAccessKey(smsProperties.getAccessKey());
				defaultSms.setSecretKey(smsProperties.getSecretKey());
				defaultSms.setSignName(smsProperties.getSignName());
				return defaultSms;
			} else {
				return s;
			}
		});
		if (sms == null || sms.getId() == null) {
			throw new ServiceException("未获取到对应的短信配置");
		} else {
			return sms;
		}
	}

}
