/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2024, https://bladex.cn. All rights reserved.
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
package org.springblade.modules.visual.factory.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.support.Kv;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;

import java.util.Map;

/**
 * 阿里云短信消息推送
 *
 * @author Chill
 */
@AllArgsConstructor
public class AliSmsServiceImpl implements MessageService {

	private static final int SUCCESS = 200;
	private static final String FAIL = "fail";
	private static final String OK = "ok";
	private static final String DOMAIN = "dysmsapi.aliyuncs.com";
	private static final String VERSION = "2017-05-25";
	private static final String ACTION = "SendSms";

	/**
	 * 消息渠道
	 */
	private VisualPushChannel channel;
	/**
	 * 模版id
	 */
	private String template;
	/**
	 * 消息参数
	 */
	private Kv params;

	@Override
	public void sendMessage() {
		// 获取短信配置
		String accessKey = channel.getAccessKey();
		String secretKey = channel.getSecretKey();
		String regionId = channel.getRegionId();
		String signName = channel.getSmsSign();
		String phones = params.getStr("phones");

		// 初始化客户端
		DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, secretKey);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysDomain(DOMAIN);
		request.setSysVersion(VERSION);
		request.setSysAction(ACTION);
		request.putQueryParameter("PhoneNumbers", phones);
		request.putQueryParameter("TemplateCode", template);
		request.putQueryParameter("TemplateParam", JsonUtil.toJson(params));
		request.putQueryParameter("SignName", signName);
		try {
			CommonResponse response = acsClient.getCommonResponse(request);
			Map<String, Object> data = JsonUtil.toMap(response.getData());
			String code = FAIL;
			if (data != null) {
				code = String.valueOf(data.get("Code"));
			}
			if (response.getHttpStatus() != SUCCESS || !code.equalsIgnoreCase(OK)) {
				throw new ServiceException(
					String.format(
						"阿里云短信消息推送失败，accessKey：%s，secretKey：%s，regionId：%s，templateId：%s，signName：%s，phones：%s"
						, accessKey, secretKey, regionId, template, signName, phones
					)
				);
			}
		} catch (ClientException e) {
			throw new ServiceException(String.format("阿里云短信消息推送失败: %s", e.getMessage()));
		}
	}
}
