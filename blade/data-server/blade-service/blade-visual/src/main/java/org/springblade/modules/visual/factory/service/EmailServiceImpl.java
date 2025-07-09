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

import lombok.AllArgsConstructor;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 邮件消息推送
 *
 * @author Chill
 */
@AllArgsConstructor
public class EmailServiceImpl implements MessageService {

	/**
	 * 邮件主题
	 */
	private static final String SUBJECT_KEY = "subject";

	/**
	 * 消息渠道
	 */
	private VisualPushChannel channel;
	/**
	 * 消息模版
	 */
	private String message;
	/**
	 * 消息参数
	 */
	private Kv params;

	@Override
	public void sendMessage() {
		// 获取邮件参数
		String senderEmail = channel.getSenderEmail();
		String emailHost = channel.getEmailHost();
		String emailPort = channel.getEmailPort();
		String emailUsername = channel.getUsername();
		String emailPassword = channel.getPassword();
		String recipientEmail = channel.getRecipientEmail();

		// 创建邮件发送器实例并设置SMTP服务器属性
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailHost);
		mailSender.setPort(Integer.parseInt(emailPort));
		mailSender.setUsername(emailUsername);
		mailSender.setPassword(emailPassword);

		// 设置JavaMail特有的属性
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.required", "false");
		props.put("mail.debug", "false"); // 如果你想查看发送邮件的详细过程，可以设置为true

		// 创建邮件消息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(senderEmail);// 设置发件人
		mailMessage.setTo(recipientEmail);// 设置收件人
		mailMessage.setSubject(params.get(SUBJECT_KEY, StringUtil.sub(message, 0, 15))); // 设置邮件主题
		mailMessage.setText(message); // 设置邮件内容

		// 发送邮件
		mailSender.send(mailMessage);

	}
}
