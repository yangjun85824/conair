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
package org.springblade.modules.visual.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息推送渠道表实体类
 *
 * @author Blade
 */
@Data
@TableName("blade_visual_push_channel")
@Schema(description = "消息推送渠道表")
public class VisualPushChannel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 推送名称
	 */
	@Schema(description = "推送名称")
	private String pushName;
	/**
	 * 推送类型
	 */
	@Schema(description = "推送类型")
	private Integer pushType;
	/**
	 * 网络钩子 用于钉钉和企业微信
	 */
	@Schema(description = "网络钩子 用于钉钉和企业微信")
	private String webhook;
	/**
	 * 机器人签名 用于钉钉
	 */
	@Schema(description = "机器人签名 用于钉钉")
	private String robotSign;
	/**
	 * 发件邮箱地址
	 */
	@Schema(description = "发件邮箱地址")
	private String senderEmail;
	/**
	 * 发件邮箱服务器地址
	 */
	@Schema(description = "发件邮箱服务器地址")
	private String emailHost;
	/**
	 * 发件邮箱服务器端口
	 */
	@Schema(description = "发件邮箱服务器端口")
	private String emailPort;
	/**
	 * 发件邮箱用户名
	 */
	@Schema(description = "发件邮箱用户名")
	private String username;
	/**
	 * 发件邮箱密码
	 */
	@Schema(description = "发件邮箱密码")
	private String password;
	/**
	 * 收件邮箱地址
	 */
	@Schema(description = "收件邮箱地址")
	private String recipientEmail;
	/**
	 * 阿里云短信accessKey
	 */
	@Schema(description = "阿里云短信accessKey")
	private String accessKey;
	/**
	 * 阿里云短信secretKey
	 */
	@Schema(description = "阿里云短信secretKey")
	private String secretKey;
	/**
	 * 阿里云短信regionId
	 */
	@Schema(description = "阿里云短信regionId")
	private String regionId;
	/**
	 * 腾讯云短信appId
	 */
	@Schema(description = "阿里云短信appId")
	private String appId;
	/**
	 * 腾讯云短信appKey
	 */
	@Schema(description = "阿里云短信appKey")
	private String appKey;
	/**
	 * 短信平台审核通过的签名
	 */
	@Schema(description = "短信平台审核通过的签名")
	private String smsSign;
	/**
	 * 保存的json配置参数
	 */
	@Schema(description = "保存的json配置参数")
	private String pushParam;

	/**
	 * 状态[0:未删除,1:删除]
	 */
	@TableLogic
	@Schema(description = "是否已删除", hidden = true)
	private Integer isDeleted;


}
