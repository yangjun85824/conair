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
package org.springblade.core.tool.tuple;

import lombok.RequiredArgsConstructor;
import org.springblade.core.tool.utils.RsaUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * rsa 的 key pair 封装
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class KeyPair {
	private final java.security.KeyPair keyPair;

	public PublicKey getPublic() {
		return keyPair.getPublic();
	}

	public PrivateKey getPrivate() {
		return keyPair.getPrivate();
	}

	public byte[] getPublicBytes() {
		return this.getPublic().getEncoded();
	}

	public byte[] getPrivateBytes() {
		return this.getPrivate().getEncoded();
	}

	public String getPublicBase64() {
		return RsaUtil.getKeyString(this.getPublic());
	}

	public String getPrivateBase64() {
		return RsaUtil.getKeyString(this.getPrivate());
	}

	@Override
	public String toString() {
		return "PublicKey=" + this.getPublicBase64() + '\n' + "PrivateKey=" + this.getPrivateBase64();
	}
}
