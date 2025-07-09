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
package org.springblade.admin.dingtalk;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 监控配置
 *
 * @author L.cm
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("monitor")
public class MonitorProperties {
	private DingTalk dingTalk = new DingTalk();

	@Getter
	@Setter
	public static class DingTalk {
		/**
		 * 启用钉钉告警，默认为 true
		 */
		private boolean enabled = false;
		/**
		 * 钉钉机器人 token
		 */
		private String accessToken;
		/**
		 * 签名：如果有 secret 则进行签名，兼容老接口
		 */
		private String secret;
		/**
		 * 地址配置
		 */
		private String link;
		private Service service = new Service();
	}

	@Getter
	@Setter
	public static class Service {
		/**
		 * 服务 状态 title
		 */
		private String title = "服务状态通知";
	}
}
