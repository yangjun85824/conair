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
package org.springblade.core.tool.utils;

import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

/**
 * hex 工具，编解码全用 byte
 *
 * @author L.cm
 */
public class HexUtil {
	public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
	private static final byte[] DIGITS_LOWER = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static final byte[] DIGITS_UPPER = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	/**
	 * encode Hex
	 *
	 * @param data data to hex
	 * @return hex bytes
	 */
	public static byte[] encode(byte[] data) {
		return encode(data, true);
	}

	/**
	 * encode Hex
	 *
	 * @param data        data to hex
	 * @param toLowerCase 是否小写
	 * @return hex bytes
	 */
	public static byte[] encode(byte[] data, boolean toLowerCase) {
		return encode(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * encode Hex
	 *
	 * @param data Data to Hex
	 * @return bytes as a hex string
	 */
	private static byte[] encode(byte[] data, byte[] digits) {
		int len = data.length;
		byte[] out = new byte[len << 1];
		for (int i = 0, j = 0; i < len; i++) {
			out[j++] = digits[(0xF0 & data[i]) >>> 4];
			out[j++] = digits[0xF & data[i]];
		}
		return out;
	}

	/**
	 * encode Hex
	 *
	 * @param data        Data to Hex
	 * @param toLowerCase 是否小写
	 * @return bytes as a hex string
	 */
	public static String encodeToString(byte[] data, boolean toLowerCase) {
		return new String(encode(data, toLowerCase), DEFAULT_CHARSET);
	}

	/**
	 * encode Hex
	 *
	 * @param data Data to Hex
	 * @return bytes as a hex string
	 */
	public static String encodeToString(byte[] data) {
		return encodeToString(data, DEFAULT_CHARSET);
	}

	/**
	 * encode Hex
	 *
	 * @param data    Data to Hex
	 * @param charset Charset
	 * @return bytes as a hex string
	 */
	public static String encodeToString(byte[] data, Charset charset) {
		return new String(encode(data), charset);
	}

	/**
	 * encode Hex
	 *
	 * @param data Data to Hex
	 * @return bytes as a hex string
	 */
	@Nullable
	public static String encodeToString(@Nullable String data) {
		if (StringUtil.isBlank(data)) {
			return null;
		}
		return encodeToString(data.getBytes(DEFAULT_CHARSET));
	}

	/**
	 * decode Hex
	 *
	 * @param data Hex data
	 * @return decode hex to bytes
	 */
	public static byte[] decode(String data) {
		return decode(data, DEFAULT_CHARSET);
	}

	/**
	 * decode Hex
	 *
	 * @param data    Hex data
	 * @param charset Charset
	 * @return decode hex to bytes
	 */
	public static byte[] decode(String data, Charset charset) {
		if (StringUtil.isBlank(data)) {
			return null;
		}
		return decode(data.getBytes(charset));
	}

	/**
	 * decodeToString Hex
	 *
	 * @param data Data to Hex
	 * @return bytes as a hex string
	 */
	public static String decodeToString(byte[] data) {
		byte[] decodeBytes = decode(data);
		return new String(decodeBytes, DEFAULT_CHARSET);
	}

	/**
	 * decodeToString Hex
	 *
	 * @param data Data to Hex
	 * @return bytes as a hex string
	 */
	@Nullable
	public static String decodeToString(@Nullable String data) {
		if (StringUtil.isBlank(data)) {
			return null;
		}
		return decodeToString(data.getBytes(DEFAULT_CHARSET));
	}

	/**
	 * decode Hex
	 *
	 * @param data Hex data
	 * @return decode hex to bytes
	 */
	public static byte[] decode(byte[] data) {
		int len = data.length;
		if ((len & 0x01) != 0) {
			throw new IllegalArgumentException("hexBinary needs to be even-length: " + len);
		}
		byte[] out = new byte[len >> 1];
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f |= toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}

	private static int toDigit(byte b, int index) {
		int digit = Character.digit(b, 16);
		if (digit == -1) {
			throw new IllegalArgumentException("Illegal hexadecimal byte " + b + " at index " + index);
		}
		return digit;
	}

}
