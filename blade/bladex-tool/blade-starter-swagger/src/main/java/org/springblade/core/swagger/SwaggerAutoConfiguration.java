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
package org.springblade.core.swagger;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.core.tool.utils.NumberUtil;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * swagger配置
 *
 * @author Chill
 */
@Slf4j
@EnableSwagger
@Configuration
@AllArgsConstructor
@AutoConfigureBefore(SpringDocConfiguration.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerAutoConfiguration {

	private static final String DEFAULT_BASE_PATH = "/**";
	private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_HEADER = TokenConstant.AUTH_HEADER;
	private static final String TENANT_HEADER = "Tenant-Id";

	/**
	 * 引入Swagger配置类
	 */
	private final SwaggerProperties swaggerProperties;

	/**
	 * 初始化OpenAPI对象
	 */
	@Bean
	public OpenAPI openApi() {
		// 初始化OpenAPI对象，并设置API的基本信息、安全策略、联系人信息、许可信息以及外部文档链接
		return new OpenAPI()
			.components(new Components()
				// 添加安全策略，配置API密钥（Token）和鉴权机制
				.addSecuritySchemes(TOKEN_HEADER,
					new SecurityScheme()
						.type(SecurityScheme.Type.APIKEY)
						.in(SecurityScheme.In.HEADER)
						.scheme("bearer")
						.bearerFormat("JWT")
						.name(TOKEN_HEADER)
				)
				// 添加安全策略，配置API密钥（Authorization）和鉴权机制
				.addSecuritySchemes(AUTHORIZATION_HEADER,
					new SecurityScheme()
						.type(SecurityScheme.Type.APIKEY)
						.in(SecurityScheme.In.HEADER)
						.name(AUTHORIZATION_HEADER)
				)
				// 添加安全策略，配置租户ID（Tenant-Id）和鉴权机制
				.addSecuritySchemes(TENANT_HEADER,
					new SecurityScheme()
						.type(SecurityScheme.Type.APIKEY)
						.in(SecurityScheme.In.HEADER)
						.name(TENANT_HEADER)
				)
			)
			// 设置API文档的基本信息，包括标题、描述、联系方式和许可信息
			.info(new Info()
				.title(swaggerProperties.getTitle())
				.description(swaggerProperties.getDescription())
				.termsOfService(swaggerProperties.getTermsOfServiceUrl())
				.contact(new Contact()
					.name(swaggerProperties.getContact().getName())
					.email(swaggerProperties.getContact().getEmail())
					.url(swaggerProperties.getContact().getUrl())
				)
				.license(new License()
					.name(swaggerProperties.getLicense())
					.url(swaggerProperties.getLicenseUrl())
				)
				.version(swaggerProperties.getVersion())
			);
	}

	/**
	 * 初始化GlobalOpenApiCustomizer对象
	 */
	@Bean
	@ConditionalOnMissingBean
	public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
		return openApi -> {
			if (openApi.getPaths() != null) {
				openApi.getPaths().forEach((s, pathItem) -> pathItem.readOperations().forEach(operation ->
					operation.addSecurityItem(new SecurityRequirement()
						.addList(AUTHORIZATION_HEADER)
						.addList(TOKEN_HEADER)
						.addList(TENANT_HEADER))));
			}
			if (openApi.getTags() != null) {
				openApi.getTags().forEach(tag -> {
					Map<String, Object> map = new HashMap<>();
					map.put("x-order", NumberUtil.parseFirstInt(tag.getDescription()));
					tag.setExtensions(map);
				});
			}
		};
	}

	/**
	 * 初始化GroupedOpenApi对象
	 */
	@Bean
	@ConditionalOnMissingBean
	public GroupedOpenApi defaultApi() {
		// 如果Swagger配置中的基本路径和排除路径为空，则设置默认的基本路径和排除路径
		if (CollectionUtil.isEmpty(swaggerProperties.getBasePath())) {
			swaggerProperties.getBasePath().add(DEFAULT_BASE_PATH);
		}
		if (CollectionUtil.isEmpty(swaggerProperties.getExcludePath())) {
			swaggerProperties.getExcludePath().addAll(DEFAULT_EXCLUDE_PATH);
		}
		// 获取Swagger配置中的基本路径、排除路径、基本包路径和排除包路径
		List<String> basePath = swaggerProperties.getBasePath();
		List<String> excludePath = swaggerProperties.getExcludePath();
		List<String> basePackages = swaggerProperties.getBasePackages();
		List<String> excludePackages = swaggerProperties.getExcludePackages();
		// 创建并返回GroupedOpenApi对象
		return GroupedOpenApi.builder()
			.group("default")
			.pathsToMatch(basePath.toArray(new String[0]))
			.pathsToExclude(excludePath.toArray(new String[0]))
			.packagesToScan(basePackages.toArray(new String[0]))
			.packagesToExclude(excludePackages.toArray(new String[0]))
			.build();
	}

}
