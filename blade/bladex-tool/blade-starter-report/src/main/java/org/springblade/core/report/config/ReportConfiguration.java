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
package org.springblade.core.report.config;

import com.bstek.ureport.UReportPropertyPlaceholderConfigurer;
import com.bstek.ureport.console.UReportServlet;
import com.bstek.ureport.provider.report.ReportProvider;
import org.springblade.core.report.props.ReportDatabaseProperties;
import org.springblade.core.report.props.ReportProperties;
import org.springblade.core.report.provider.DatabaseProvider;
import org.springblade.core.report.provider.ReportPlaceholderProvider;
import org.springblade.core.report.service.IReportFileService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;

import jakarta.servlet.Servlet;

/**
 * UReport配置类
 *
 * @author Chill
 */
@Order
@AutoConfiguration
@ConditionalOnProperty(value = "report.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ReportProperties.class, ReportDatabaseProperties.class})
@ImportResource("classpath:ureport-console-context.xml")
public class ReportConfiguration {

	@Bean
	public ServletRegistrationBean<Servlet> registrationBean() {
		return new ServletRegistrationBean<>(new UReportServlet(), "/ureport/*");
	}

	@Bean
	public UReportPropertyPlaceholderConfigurer uReportPropertyPlaceholderConfigurer(ReportProperties properties) {
		return new ReportPlaceholderProvider(properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public ReportProvider reportProvider(ReportDatabaseProperties properties, IReportFileService service) {
		return new DatabaseProvider(properties, service);
	}

}
