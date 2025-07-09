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
package org.springblade.core.launch.constant;

/**
 * 系统常量
 *
 * @author Chill
 */
public interface AppConstant {

	/**
	 * 应用版本
	 */
	String APPLICATION_VERSION = "4.1.0.RELEASE";

	/**
	 * 基础包
	 */
	String BASE_PACKAGES = "org.springblade";

	/**
	 * 应用名前缀
	 */
	String APPLICATION_NAME_PREFIX = "blade-";
	/**
	 * 网关模块名称
	 */
	String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";
	/**
	 * 授权模块名称
	 */
	String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";
	/**
	 * 监控模块名称
	 */
	String APPLICATION_ADMIN_NAME = APPLICATION_NAME_PREFIX + "admin";
	/**
	 * 报表系统名称
	 */
	String APPLICATION_REPORT_NAME = APPLICATION_NAME_PREFIX + "report";
	/**
	 * 集群监控名称
	 */
	String APPLICATION_TURBINE_NAME = APPLICATION_NAME_PREFIX + "turbine";
	/**
	 * 链路追踪名称
	 */
	String APPLICATION_ZIPKIN_NAME = APPLICATION_NAME_PREFIX + "zipkin";
	/**
	 * websocket名称
	 */
	String APPLICATION_WEBSOCKET_NAME = APPLICATION_NAME_PREFIX + "websocket";
	/**
	 * 首页模块名称
	 */
	String APPLICATION_DESK_NAME = APPLICATION_NAME_PREFIX + "desk";
	/**
	 * 系统模块名称
	 */
	String APPLICATION_SYSTEM_NAME = APPLICATION_NAME_PREFIX + "system";
	/**
	 * 用户模块名称
	 */
	String APPLICATION_USER_NAME = APPLICATION_NAME_PREFIX + "user";
	/**
	 * 日志模块名称
	 */
	String APPLICATION_LOG_NAME = APPLICATION_NAME_PREFIX + "log";
	/**
	 * 开发模块名称
	 */
	String APPLICATION_DEVELOP_NAME = APPLICATION_NAME_PREFIX + "develop";
	/**
	 * 流程设计器模块名称
	 */
	String APPLICATION_FLOWDESIGN_NAME = APPLICATION_NAME_PREFIX + "flowdesign";
	/**
	 * 工作流模块名称
	 */
	String APPLICATION_FLOW_NAME = APPLICATION_NAME_PREFIX + "flow";
	/**
	 * 资源模块名称
	 */
	String APPLICATION_RESOURCE_NAME = APPLICATION_NAME_PREFIX + "resource";
	/**
	 * 接口文档模块名称
	 */
	String APPLICATION_SWAGGER_NAME = APPLICATION_NAME_PREFIX + "swagger";
	/**
	 * 任务模块名称
	 */
	String APPLICATION_JOB_NAME = APPLICATION_NAME_PREFIX + "job";
	/**
	 * 测试模块名称
	 */
	String APPLICATION_TEST_NAME = APPLICATION_NAME_PREFIX + "test";
	/**
	 * 演示模块名称
	 */
	String APPLICATION_DEMO_NAME = APPLICATION_NAME_PREFIX + "demo";

	/**
	 * 开发环境
	 */
	String DEV_CODE = "dev";
	/**
	 * 生产环境
	 */
	String PROD_CODE = "prod";
	/**
	 * 测试环境
	 */
	String TEST_CODE = "test";

	/**
	 * 代码部署于 linux 上，工作默认为 mac 和 Windows
	 */
	String OS_NAME_LINUX = "LINUX";

}
