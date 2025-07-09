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
package org.springblade.core.secure.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.secure.TokenInfo;
import org.springblade.core.secure.constant.SecureConstant;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Charsets;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.WebUtil;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static org.springblade.core.secure.constant.SecureConstant.*;

/**
 * Secure工具类
 *
 * @author Chill
 */
public class SecureUtil extends AuthUtil {

	public static final String TYP = "typ";
	public static final String JWT = "JWT";
	public static final String AUDIENCE = "bladex";
	public static final String ISSUER = "bladex.cn";

	/**
	 * 创建令牌
	 *
	 * @param kv 构建参数
	 * @return TokenInfo
	 */
	public static TokenInfo createToken(Kv kv) {
		return createToken(kv, null, AUDIENCE, ISSUER);
	}

	/**
	 * 创建令牌
	 *
	 * @param kv     构建参数
	 * @param expire 过期秒数
	 * @return TokenInfo
	 */
	public static TokenInfo createToken(Kv kv, Integer expire) {
		return createToken(kv, expire, AUDIENCE, ISSUER);
	}

	/**
	 * 创建令牌
	 *
	 * @param kv       构建参数
	 * @param expire   过期秒数
	 * @param audience audience
	 * @param issuer   issuer
	 * @return TokenInfo
	 */
	public static TokenInfo createToken(Kv kv, Integer expire, String audience, String issuer) {
		// 添加Token过期时间
		Instant now = Instant.now();
		int expireSeconds = Optional.ofNullable(expire)
			.orElseGet(SecureUtil::getExpire); // 获取默认过期时间
		Instant exp = now.plus(expireSeconds, ChronoUnit.SECONDS);

		// 生成签名密钥
		SecretKey signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JwtUtil.getBase64Security()));

		// 添加构成JWT的类
		JwtBuilder builder = Jwts.builder().header().add(TYP, JWT)
			.and().issuer(issuer).audience().add(audience)
			.and().signWith(signingKey);

		// 设置JWT参数
		kv.forEach(builder::claim);

		// 设置Token过期时间
		builder.expiration(Date.from(exp)).notBefore(Date.from(now));

		// 组装Token信息
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setToken(builder.compact());
		tokenInfo.setExpire(expireSeconds);

		// 返回Token信息
		return tokenInfo;
	}

	/**
	 * 获取默认过期时间(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static int getExpire() {
		LocalTime threeAM = LocalTime.of(3, 0);
		LocalDate tomorrow = LocalDate.now(ZoneId.systemDefault()).plusDays(1);
		Instant threeAMTomorrow = tomorrow.atTime(threeAM).atZone(ZoneId.systemDefault()).toInstant();
		return (int) ChronoUnit.SECONDS.between(Instant.now(), threeAMTomorrow);
	}


	/**
	 * 获取请求头中的客户端id
	 */
	public static String getClientId() {
		String[] tokens = extractAndDecodeAuthorization();
		assert tokens.length == 2;
		return tokens[0];
	}

	/**
	 * 获取请求头中的客户端密钥
	 */
	public static String getClientSecret() {
		String[] tokens = extractAndDecodeAuthorization();
		assert tokens.length == 2;
		return tokens[1];
	}

	/**
	 * 客户端信息解码
	 */
	@SneakyThrows
	public static String[] extractAndDecodeAuthorization() {
		// 获取请求头客户端信息
		String header = Objects.requireNonNull(WebUtil.getRequest()).getHeader(SecureConstant.BASIC_HEADER_KEY);
		header = Func.toStr(header).replace(SecureConstant.BASIC_HEADER_PREFIX_EXT, SecureConstant.BASIC_HEADER_PREFIX);
		if (!header.startsWith(SecureConstant.BASIC_HEADER_PREFIX)) {
			throw new SecurityException(AUTHORIZATION_NOT_FOUND);
		}
		byte[] base64Token = header.substring(6).getBytes(Charsets.UTF_8_NAME);

		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException exception) {
			throw new SecurityException(CLIENT_TOKEN_PARSE_FAILED);
		}

		String token = new String(decoded, Charsets.UTF_8_NAME);
		int index = token.indexOf(StringPool.COLON);
		if (index == -1) {
			throw new SecurityException(INVALID_CLIENT_TOKEN);
		} else {
			return new String[]{token.substring(0, index), token.substring(index + 1)};
		}
	}

}
