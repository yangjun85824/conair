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
package org.springblade.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springblade.core.jwt.enums.SingleLevel;
import org.springblade.core.jwt.props.JwtProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * Jwt工具类
 *
 * @author Chill
 */
public class JwtUtil {

	/**
	 * token基础配置
	 */
	public static String BEARER = "bearer";
	public static String CRYPTO = "crypto";
	public static Integer AUTH_LENGTH = 7;

	/**
	 * token保存至redis的key
	 */
	private static final String ACCESS_TOKEN_CACHE = "blade:token";
	private static final String REFRESH_TOKEN_CACHE = "blade:refreshToken";
	private static final String TOKEN_KEY = "token:state:";

	/**
	 * jwt配置
	 */
	@Getter
	private static JwtProperties jwtProperties;

	/**
	 * redis工具
	 */
	@Getter
	private static RedisTemplate<String, Object> redisTemplate;

	public static void setJwtProperties(JwtProperties properties) {
		if (JwtUtil.jwtProperties == null) {
			JwtUtil.jwtProperties = properties;
		}
	}

	public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		if (JwtUtil.redisTemplate == null) {
			JwtUtil.redisTemplate = redisTemplate;
		}
	}

	/**
	 * 签名加密
	 */
	public static String getBase64Security() {
		return Base64.getEncoder().encodeToString(getJwtProperties().getSignKey().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 获取请求传递的token串
	 *
	 * @param auth token
	 * @return String
	 */
	public static String getToken(String auth) {
		if (isBearer(auth) || isCrypto(auth)) {
			return auth.substring(AUTH_LENGTH);
		}
		return null;
	}

	/**
	 * 判断token类型为bearer
	 *
	 * @param auth token
	 * @return String
	 */
	public static Boolean isBearer(String auth) {
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			return headStr.compareTo(BEARER) == 0;
		}
		return false;
	}

	/**
	 * 判断token类型为crypto
	 *
	 * @param auth token
	 * @return String
	 */
	public static Boolean isCrypto(String auth) {
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			return headStr.compareTo(CRYPTO) == 0;
		}
		return false;
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken token串
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			SecretKey secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(getBase64Security()));
			return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(jsonWebToken)
				.getPayload();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 获取保存在redis的accessToken
	 *
	 * @param tenantId    租户id
	 * @param userId      用户id
	 * @param accessToken accessToken
	 * @return accessToken
	 */
	public static String getAccessToken(String tenantId, String userId, String accessToken) {
		return getAccessToken(tenantId, null, userId, accessToken);
	}

	/**
	 * 获取保存在redis的accessToken
	 *
	 * @param tenantId    租户id
	 * @param clientId    应用id
	 * @param userId      用户id
	 * @param accessToken accessToken
	 * @return accessToken
	 */
	public static String getAccessToken(String tenantId, String clientId, String userId, String accessToken) {
		return String.valueOf(getRedisTemplate().opsForValue().get(getAccessTokenKey(tenantId, clientId, userId, accessToken)));
	}


	/**
	 * 添加accessToken至redis
	 *
	 * @param tenantId    租户id
	 * @param userId      用户id
	 * @param accessToken accessToken
	 * @param expire      过期时间
	 */
	public static void addAccessToken(String tenantId, String userId, String accessToken, int expire) {
		addAccessToken(tenantId, null, userId, accessToken, expire);
	}

	/**
	 * 添加accessToken至redis
	 *
	 * @param tenantId    租户id
	 * @param clientId    应用id
	 * @param userId      用户id
	 * @param accessToken accessToken
	 * @param expire      过期时间
	 */
	public static void addAccessToken(String tenantId, String clientId, String userId, String accessToken, int expire) {
		getRedisTemplate().delete(getAccessTokenKey(tenantId, clientId, userId, accessToken));
		getRedisTemplate().opsForValue().set(getAccessTokenKey(tenantId, clientId, userId, accessToken), accessToken, expire, TimeUnit.SECONDS);
	}

	/**
	 * 删除保存在redis的accessToken
	 *
	 * @param tenantId 租户id
	 * @param userId   用户id
	 */
	public static void removeAccessToken(String tenantId, String userId) {
		removeAccessToken(tenantId, userId, null);
	}

	/**
	 * 删除保存在redis的accessToken
	 *
	 * @param tenantId    租户id
	 * @param userId      用户id
	 * @param accessToken accessToken
	 */
	public static void removeAccessToken(String tenantId, String userId, String accessToken) {
		removeAccessToken(tenantId, null, userId, accessToken);
	}

	/**
	 * 删除保存在redis的accessToken
	 *
	 * @param tenantId    租户id
	 * @param clientId    应用id
	 * @param userId      用户id
	 * @param accessToken accessToken
	 */
	public static void removeAccessToken(String tenantId, String clientId, String userId, String accessToken) {
		getRedisTemplate().delete(getAccessTokenKey(tenantId, clientId, userId, accessToken));
	}

	/**
	 * 获取保存在redis的refreshToken
	 *
	 * @param tenantId 租户id
	 * @param userId   用户id
	 * @return refreshToken
	 */
	public static String getRefreshToken(String tenantId, String userId) {
		return getRefreshToken(tenantId, null, userId, null);
	}

	/**
	 * 获取保存在redis的refreshToken
	 *
	 * @param tenantId     租户id
	 * @param userId       用户id
	 * @param refreshToken refreshToken
	 * @return refreshToken
	 */
	public static String getRefreshToken(String tenantId, String userId, String refreshToken) {
		return getRefreshToken(tenantId, null, userId, refreshToken);
	}

	/**
	 * 获取保存在redis的refreshToken
	 *
	 * @param tenantId     租户id
	 * @param clientId     应用id
	 * @param userId       用户id
	 * @param refreshToken refreshToken
	 * @return accessToken
	 */
	public static String getRefreshToken(String tenantId, String clientId, String userId, String refreshToken) {
		return String.valueOf(getRedisTemplate().opsForValue().get(getRefreshTokenKey(tenantId, clientId, userId, refreshToken)));
	}

	/**
	 * 添加refreshToken至redis
	 *
	 * @param tenantId     租户id
	 * @param userId       用户id
	 * @param refreshToken refreshToken
	 * @param expire       过期时间
	 */
	public static void addRefreshToken(String tenantId, String userId, String refreshToken, int expire) {
		addRefreshToken(tenantId, null, userId, refreshToken, expire);
	}

	/**
	 * 添加refreshToken至redis
	 *
	 * @param tenantId     租户id
	 * @param clientId     应用id
	 * @param userId       用户id
	 * @param refreshToken refreshToken
	 * @param expire       过期时间
	 */
	public static void addRefreshToken(String tenantId, String clientId, String userId, String refreshToken, int expire) {
		getRedisTemplate().delete(getRefreshTokenKey(tenantId, clientId, userId, refreshToken));
		getRedisTemplate().opsForValue().set(getRefreshTokenKey(tenantId, clientId, userId, refreshToken), refreshToken, expire, TimeUnit.SECONDS);
	}

	/**
	 * 删除保存在refreshToken的token
	 *
	 * @param tenantId 租户id
	 * @param userId   用户id
	 */
	public static void removeRefreshToken(String tenantId, String userId) {
		removeRefreshToken(tenantId, null, userId, null);
	}

	/**
	 * 删除保存在refreshToken的token
	 *
	 * @param tenantId 租户id
	 * @param clientId 应用id
	 * @param userId   用户id
	 */
	public static void removeRefreshToken(String tenantId, String clientId, String userId) {
		removeRefreshToken(tenantId, clientId, userId, null);
	}

	/**
	 * 删除保存在refreshToken的token
	 *
	 * @param tenantId     租户id
	 * @param clientId     应用id
	 * @param userId       用户id
	 * @param refreshToken refreshToken
	 */
	public static void removeRefreshToken(String tenantId, String clientId, String userId, String refreshToken) {
		getRedisTemplate().delete(getRefreshTokenKey(tenantId, clientId, userId, refreshToken));
	}

	/**
	 * 获取accessToken索引
	 *
	 * @param tenantId    租户id
	 * @param userId      用户id
	 * @param accessToken accessToken
	 * @return token索引
	 */
	public static String getAccessTokenKey(String tenantId, String userId, String accessToken) {
		return getAccessTokenKey(tenantId, null, userId, accessToken);
	}

	/**
	 * 获取accessToken索引
	 *
	 * @param tenantId    租户id
	 * @param clientId    应用id
	 * @param userId      用户id
	 * @param accessToken accessToken
	 * @return token索引
	 */
	public static String getAccessTokenKey(String tenantId, String clientId, String userId, String accessToken) {
		return getTokenKey(ACCESS_TOKEN_CACHE, tenantId, clientId, userId, accessToken);
	}

	/**
	 * 获取refreshToken索引
	 *
	 * @param tenantId 租户id
	 * @param userId   用户id
	 * @return token索引
	 */
	public static String getRefreshTokenKey(String tenantId, String userId) {
		return getRefreshTokenKey(tenantId, null, userId, null);
	}

	/**
	 * 获取refreshToken索引
	 *
	 * @param tenantId 租户id
	 * @param clientId 应用id
	 * @param userId   用户id
	 * @return token索引
	 */
	public static String getRefreshTokenKey(String tenantId, String clientId, String userId) {
		return getRefreshTokenKey(tenantId, clientId, userId, null);
	}

	/**
	 * 获取refreshToken索引
	 *
	 * @param tenantId     租户id
	 * @param clientId     应用id
	 * @param userId       用户id
	 * @param refreshToken refreshToken
	 * @return token索引
	 */
	public static String getRefreshTokenKey(String tenantId, String clientId, String userId, String refreshToken) {
		return getTokenKey(REFRESH_TOKEN_CACHE, tenantId, clientId, userId, refreshToken);
	}

	/**
	 * 获取通用Token索引
	 *
	 * @param tokenCache 缓存名
	 * @param tenantId   租户id
	 * @param clientId   应用id
	 * @param userId     用户id
	 * @param tokenValue tokenValue
	 * @return token索引
	 */
	public static String getTokenKey(String tokenCache, String tenantId, String clientId, String userId, String tokenValue) {
		String key = tenantId.concat(":").concat(tokenCache).concat("::").concat(TOKEN_KEY);
		if (getJwtProperties().getSingle() || !StringUtils.hasText(tokenValue)) {
			if (getJwtProperties().getSingleLevel() == SingleLevel.CLIENT && StringUtils.hasText(clientId)) {
				key = key.concat(clientId).concat(":");
			}
			return key.concat(userId);
		} else {
			return key.concat(tokenValue);
		}
	}

}
