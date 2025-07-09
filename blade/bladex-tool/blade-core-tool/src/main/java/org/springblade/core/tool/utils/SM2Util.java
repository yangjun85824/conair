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
package org.springblade.core.tool.utils;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.Security;

/**
 * SM2加密、解密、签名和验证工具类。
 *
 * @author BladeX
 */
public class SM2Util {
	public final static String ENCRYPT_PREFIX = "04";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	private static final ECDomainParameters DOMAIN_PARAMS;

	static {
		X9ECParameters spec = GMNamedCurves.getByName("sm2p256v1");
		DOMAIN_PARAMS = new ECDomainParameters(spec.getCurve(), spec.getG(), spec.getN(), spec.getH());
	}

	/**
	 * 生成SM2密钥对。
	 *
	 * @return 密钥对（公钥和私钥）
	 */
	public static AsymmetricCipherKeyPair generateKeyPair() {
		ECKeyPairGenerator generator = new ECKeyPairGenerator();
		generator.init(new ECKeyGenerationParameters(DOMAIN_PARAMS, new SecureRandom()));
		return generator.generateKeyPair();
	}


	/**
	 * 使用SM2算法加密文本。
	 *
	 * @param input     文本数据
	 * @param publicKey 公钥
	 * @return 加密后的数据
	 */
	public static byte[] encrypt(String input, String publicKey) {
		return encrypt(input, stringToPublicKey(publicKey));
	}

	/**
	 * 使用SM2算法加密文本。
	 *
	 * @param input     文本数据
	 * @param publicKey 公钥
	 * @return 加密后的数据
	 */
	public static byte[] encrypt(String input, ECPublicKeyParameters publicKey) {
		try {
			SM2Engine engine = new SM2Engine();
			engine.init(true, new ParametersWithRandom(publicKey, new SecureRandom()));
			byte[] inputBytes = input.getBytes();
			return engine.processBlock(inputBytes, 0, inputBytes.length);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 使用SM2算法解密数据。
	 *
	 * @param encrypted  加密的数据
	 * @param privateKey 私钥
	 * @return 解密后的文本
	 */
	public static String decrypt(String encrypted, String privateKey) {
		return decrypt(Hex.decode(encrypted), privateKey);
	}

	/**
	 * 使用SM2算法解密数据。
	 *
	 * @param encrypted  加密的数据
	 * @param privateKey 私钥
	 * @return 解密后的文本
	 */
	public static String decrypt(byte[] encrypted, String privateKey) {
		return decrypt(encrypted, stringToPrivateKey(privateKey));
	}

	/**
	 * 使用SM2算法解密数据。
	 *
	 * @param encrypted  加密的数据
	 * @param privateKey 私钥
	 * @return 解密后的文本
	 */
	public static String decrypt(byte[] encrypted, ECPrivateKeyParameters privateKey) {
		try {
			SM2Engine engine = new SM2Engine();
			engine.init(false, privateKey);
			byte[] decryptedBytes = engine.processBlock(encrypted, 0, encrypted.length);
			return new String(decryptedBytes);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据密钥对生成解密字符串。
	 *
	 * @param encrypted  加密的数据
	 * @param publicKey  公钥
	 * @param privateKey 私钥
	 * @return 解密后的字符串
	 */
	public static String decrypt(String encrypted, String publicKey, String privateKey) {
		// 其中有空则匹配失败
		if (StringUtil.isAnyBlank(publicKey, privateKey)) {
			return StringPool.EMPTY;
		}
		// 处理部分工具类加密不带04前缀的情况
		if (!StringUtil.startsWithIgnoreCase(encrypted, ENCRYPT_PREFIX)) {
			encrypted = ENCRYPT_PREFIX + encrypted;
		}
		// 解密数据
		String decrypted = SM2Util.decrypt(encrypted, privateKey);
		// 签名校验
		boolean isVerified = SM2Util.verify(decrypted, SM2Util.sign(decrypted, privateKey), publicKey);
		// 返回解密结果
		return isVerified ? decrypted : StringPool.EMPTY;
	}

	/**
	 * 使用SM2算法进行数据签名。
	 *
	 * @param input      需要签名的数据
	 * @param privateKey 私钥
	 * @return 签名
	 */
	public static byte[] sign(String input, String privateKey) {
		return sign(input, stringToPrivateKey(privateKey));
	}

	/**
	 * 使用SM2算法进行数据签名。
	 *
	 * @param input      需要签名的数据
	 * @param privateKey 私钥
	 * @return 签名
	 */
	public static byte[] sign(String input, ECPrivateKeyParameters privateKey) {
		try {
			SM2Signer signer = new SM2Signer();
			signer.init(true, new ParametersWithRandom(privateKey, new SecureRandom()));
			byte[] inputBytes = input.getBytes();
			signer.update(inputBytes, 0, inputBytes.length);
			return signer.generateSignature();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 使用SM2算法验证签名。
	 *
	 * @param input     数据
	 * @param signature 签名
	 * @param publicKey 公钥
	 * @return 是否验证成功
	 */
	public static boolean verify(String input, byte[] signature, String publicKey) {
		return verify(input, signature, stringToPublicKey(publicKey));
	}

	/**
	 * 使用SM2算法验证签名。
	 *
	 * @param input     数据
	 * @param signature 签名
	 * @param publicKey 公钥
	 * @return 是否验证成功
	 */
	public static boolean verify(String input, byte[] signature, ECPublicKeyParameters publicKey) {
		try {
			SM2Signer signer = new SM2Signer();
			signer.init(false, publicKey);
			byte[] inputBytes = input.getBytes();
			signer.update(inputBytes, 0, inputBytes.length);
			return signer.verifySignature(signature);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取公钥字符串。
	 *
	 * @param keyPair 密钥对
	 * @return 公钥字符串
	 */
	public static String getPublicKeyString(AsymmetricCipherKeyPair keyPair) {
		ECPublicKeyParameters ecPublicKeyParameters = (ECPublicKeyParameters) keyPair.getPublic();
		return Hex.toHexString(ecPublicKeyParameters.getQ().getEncoded(false));
	}

	/**
	 * 获取私钥字符串。
	 *
	 * @param keyPair 密钥对
	 * @return 私钥字符串
	 */
	public static String getPrivateKeyString(AsymmetricCipherKeyPair keyPair) {
		ECPrivateKeyParameters ecPrivateKeyParameters = (ECPrivateKeyParameters) keyPair.getPrivate();
		return Hex.toHexString(ecPrivateKeyParameters.getD().toByteArray());
	}

	/**
	 * 从字符串恢复公钥。
	 *
	 * @param data 公钥字符串
	 * @return 公钥
	 */
	public static ECPublicKeyParameters stringToPublicKey(String data) {
		return new ECPublicKeyParameters(DOMAIN_PARAMS.getCurve().decodePoint(Hex.decode(data)), DOMAIN_PARAMS);
	}

	/**
	 * 从字符串恢复私钥。
	 *
	 * @param data 私钥字符串
	 * @return 私钥
	 */
	public static ECPrivateKeyParameters stringToPrivateKey(String data) {
		return new ECPrivateKeyParameters(new BigInteger(Hex.decode(data)), DOMAIN_PARAMS);
	}

}
