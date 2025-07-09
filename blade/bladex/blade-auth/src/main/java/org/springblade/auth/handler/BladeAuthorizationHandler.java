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
package org.springblade.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.cache.CacheNames;
import org.springblade.common.constant.TenantConstant;
import org.springblade.core.oauth2.exception.ExceptionCode;
import org.springblade.core.oauth2.handler.AbstractAuthorizationHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Validation;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tenant.BladeTenantProperties;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.DesUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.cache.ParamCache;
import org.springblade.system.cache.SysCache;
import org.springblade.system.pojo.entity.Tenant;

import java.time.Duration;
import java.util.Date;

import static org.springblade.auth.constant.BladeAuthConstant.FAIL_COUNT;
import static org.springblade.auth.constant.BladeAuthConstant.FAIL_COUNT_VALUE;

/**
 * AbstractAuthorizationHandler
 *
 * @author BladeX
 */
@Slf4j
@RequiredArgsConstructor
public class BladeAuthorizationHandler extends AbstractAuthorizationHandler {

	private final BladeRedis bladeRedis;
	private final BladeTenantProperties tenantProperties;

	/**
	 * 认证校验
	 *
	 * @param user    用户信息
	 * @param request 请求信息
	 * @return boolean
	 */
	@Override
	public OAuth2Validation authValidation(OAuth2User user, OAuth2Request request) {
		// 密码模式、刷新token模式、验证码模式需要校验租户状态
		if (request.isPassword() || request.isRefreshToken() || request.isCaptchaCode()) {
			// 租户校验
			OAuth2Validation tenantValidation = validateTenant(user.getTenantId());
			if (!tenantValidation.isSuccess()) {
				return tenantValidation;
			}
			// 判断登录是否锁定
			OAuth2Validation failCountValidation = validateFailCount(user.getTenantId(), user.getAccount());
			if (!failCountValidation.isSuccess()) {
				return failCountValidation;
			}
		}
		return super.authValidation(user, request);
	}

	/**
	 * 认证成功回调
	 *
	 * @param user 用户信息
	 */
	@Override
	public void authSuccessful(OAuth2User user, OAuth2Request request) {
		// 清空错误锁定次数
		delFailCount(user.getTenantId(), user.getAccount());

		log.info("用户：{}，认证成功", user.getAccount());
	}

	/**
	 * 认证失败回调
	 *
	 * @param user       用户信息
	 * @param validation 失败信息
	 */
	@Override
	public void authFailure(OAuth2User user, OAuth2Request request, OAuth2Validation validation) {
		// 增加错误锁定次数
		addFailCount(user.getTenantId(), user.getAccount());

		log.error("用户：{}，认证失败，失败原因：{}", user.getAccount(), validation.getMessage());
	}

	/**
	 * 租户授权校验
	 *
	 * @param tenantId 租户id
	 * @return OAuth2Validation
	 */
	private OAuth2Validation validateTenant(String tenantId) {
		// 租户校验
		Tenant tenant = SysCache.getTenant(tenantId);
		if (tenant == null) {
			return buildValidationFailure(ExceptionCode.USER_TENANT_NOT_FOUND);
		}
		// 租户授权时间校验
		Date expireTime = tenant.getExpireTime();
		if (tenantProperties.getLicense()) {
			String licenseKey = tenant.getLicenseKey();
			String decrypt = DesUtil.decryptFormHex(licenseKey, TenantConstant.DES_KEY);
			Tenant license = JsonUtil.parse(decrypt, Tenant.class);
			if (license == null || !license.getId().equals(tenant.getId())) {
				return buildValidationFailure(ExceptionCode.UNAUTHORIZED_USER_TENANT);
			}
			expireTime = license.getExpireTime();
		}
		if (expireTime != null && expireTime.before(DateUtil.now())) {
			return buildValidationFailure(ExceptionCode.UNAUTHORIZED_USER_TENANT);
		}
		return new OAuth2Validation();
	}

	/**
	 * 判断登录是否锁定
	 *
	 * @param tenantId 租户id
	 * @param account  账号
	 * @return OAuth2Validation
	 */
	private OAuth2Validation validateFailCount(String tenantId, String account) {
		int cnt = getFailCount(tenantId, account);
		int failCount = Func.toInt(ParamCache.getValue(FAIL_COUNT_VALUE), FAIL_COUNT);
		if (cnt >= failCount) {
			return buildValidationFailure(ExceptionCode.USER_TOO_MANY_FAILS);
		}
		return new OAuth2Validation();
	}

	/**
	 * 获取账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @return int
	 */
	private int getFailCount(String tenantId, String username) {
		return Func.toInt(bladeRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username)), 0);
	}

	/**
	 * 设置账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 */
	private void addFailCount(String tenantId, String username) {
		int count = getFailCount(tenantId, username);
		bladeRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username), count + 1, Duration.ofMinutes(30));
	}

	/**
	 * 设置账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @param count    次数
	 */
	private void setFailCount(String tenantId, String username, int count) {
		bladeRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username), count + 1, Duration.ofMinutes(30));
	}

	/**
	 * 清空账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 */
	private void delFailCount(String tenantId, String username) {
		bladeRedis.del(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username));
	}
}
