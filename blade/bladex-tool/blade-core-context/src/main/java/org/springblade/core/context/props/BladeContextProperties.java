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
package org.springblade.core.context.props;

import lombok.Getter;
import lombok.Setter;
import org.springblade.core.launch.constant.TokenConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Headers 配置
 *
 * @author L.cm
 */
@Getter
@Setter
@ConfigurationProperties(BladeContextProperties.PREFIX)
public class BladeContextProperties {
	/**
	 * 配置前缀
	 */
	public static final String PREFIX = "blade.context";
	/**
	 * 上下文传递的 headers 信息
	 */
	private Headers headers = new Headers();

	@Getter
	@Setter
	public static class Headers {
		/**
		 * 请求id，默认：Blade-RequestId
		 */
		private String requestId = "Blade-RequestId";
		/**
		 * 用于 聚合层 向调用层传递用户信息 的请求头，默认：Blade-AccountId
		 */
		private String accountId = "Blade-AccountId";
		/**
		 * 用于 聚合层 向调用层传递租户id 的请求头，默认：Blade-TenantId
		 */
		private String tenantId = "Blade-TenantId";
		/**
		 * 自定义 RestTemplate 和 Feign 透传到下层的 Headers 名称列表
		 */
		private List<String> allowed = Arrays.asList("X-Real-IP", "x-forwarded-for", "version", "VERSION", "authorization", "Authorization", TokenConstant.AUTH_HEADER.toLowerCase(), TokenConstant.AUTH_HEADER, TokenConstant.SECURE_HEADER.toLowerCase(), TokenConstant.SECURE_HEADER);
	}

	/**
	 * 获取跨服务的请求头
	 *
	 * @return 请求头列表
	 */
	public List<String> getCrossHeaders() {
		List<String> headerList = new ArrayList<>();
		headerList.add(headers.getRequestId());
		headerList.add(headers.getAccountId());
		headerList.add(headers.getTenantId());
		headerList.addAll(headers.getAllowed());
		return headerList;
	}

}
