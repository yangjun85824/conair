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
package org.springblade.core.tool.constant;

/**
 * 系统常量
 *
 * @author Chill
 */
public interface BladeConstant {

	/**
	 * 编码
	 */
	String UTF_8 = "UTF-8";

	/**
	 * contentType
	 */
	String CONTENT_TYPE_NAME = "Content-type";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json;charset=utf-8";

	/**
	 * 上下文键值
	 */
	String CONTEXT_KEY = "bladeContext";

	/**
	 * mdc request id key
	 */
	String MDC_REQUEST_ID_KEY = "requestId";

	/**
	 * mdc account id key
	 */
	String MDC_ACCOUNT_ID_KEY = "accountId";

	/**
	 * mdc tenant id key
	 */
	String MDC_TENANT_ID_KEY = "tenantId";

	/**
	 * 角色前缀
	 */
	String SECURITY_ROLE_PREFIX = "ROLE_";

	/**
	 * 主键字段名
	 */
	String DB_PRIMARY_KEY = "id";

	/**
	 * 主键字段get方法
	 */
	String DB_PRIMARY_KEY_METHOD = "getId";

	/**
	 * 租户字段名
	 */
	String DB_TENANT_KEY = "tenantId";

	/**
	 * 租户字段get方法
	 */
	String DB_TENANT_KEY_GET_METHOD = "getTenantId";

	/**
	 * 租户字段set方法
	 */
	String DB_TENANT_KEY_SET_METHOD = "setTenantId";

	/**
	 * 业务状态[1:正常]
	 */
	int DB_STATUS_NORMAL = 1;


	/**
	 * 删除状态[0:正常,1:删除]
	 */
	int DB_NOT_DELETED = 0;
	int DB_IS_DELETED = 1;

	/**
	 * 用户锁定状态
	 */
	int DB_ADMIN_NON_LOCKED = 0;
	int DB_ADMIN_LOCKED = 1;

	/**
	 * 顶级父节点id
	 */
	Long TOP_PARENT_ID = 0L;

	/**
	 * 顶级父节点名称
	 */
	String TOP_PARENT_NAME = "顶级";

	/**
	 * 管理员对应的租户ID
	 */
	String ADMIN_TENANT_ID = "000000";

	/**
	 * 日志默认状态
	 */
	String LOG_NORMAL_TYPE = "1";

	/**
	 * 默认为空消息
	 */
	String DEFAULT_NULL_MESSAGE = "暂无承载数据";
	/**
	 * 默认成功消息
	 */
	String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	/**
	 * 默认失败消息
	 */
	String DEFAULT_FAILURE_MESSAGE = "操作失败";
	/**
	 * 默认未授权消息
	 */
	String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";

}
