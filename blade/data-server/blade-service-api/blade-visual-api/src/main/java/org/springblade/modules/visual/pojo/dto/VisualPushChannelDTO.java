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
package org.springblade.modules.visual.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息推送渠道表数据传输对象实体类
 *
 * @author Blade
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VisualPushChannelDTO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 推送名称
	 */
	private String pushName;
	/**
	 * 推送类型
	 */
	private Integer pushType;
	/**
	 * 推送类型名称
	 */
	private String typeName;
	/**
	 * 网络钩子 用于钉钉和企业微信
	 */
	private String webhook;
	/**
	 * 机器人签名 用于钉钉
	 */
	private String robotSign;
	/**
	 * 发件邮箱地址
	 */
	private String senderEmail;
	/**
	 * 发件邮箱服务器地址
	 */
	private String emailHost;
	/**
	 * 发件邮箱服务器端口
	 */
	private String emailPort;
	/**
	 * 发件邮箱用户名
	 */
	private String username;
	/**
	 * 发件邮箱密码
	 */
	private String password;
	/**
	 * 收件邮箱地址
	 */
	private String recipientEmail;
	/**
	 * 阿里云短信accessKey
	 */
	private String accessKey;
	/**
	 * 阿里云短信secretKey
	 */
	private String secretKey;
	/**
	 * 阿里云短信regionId
	 */
	private String regionId;
	/**
	 * 腾讯云短信appId
	 */
	private String appId;
	/**
	 * 腾讯云短信appKey
	 */
	private String appKey;
	/**
	 * 短信平台审核通过的签名
	 */
	private String smsSign;

}
