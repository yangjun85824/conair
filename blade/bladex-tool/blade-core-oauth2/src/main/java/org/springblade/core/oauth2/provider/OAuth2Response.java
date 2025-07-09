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
package org.springblade.core.oauth2.provider;

import lombok.Data;
import org.springblade.core.tool.support.Kv;

import java.io.Serial;
import java.io.Serializable;

import static org.springblade.core.oauth2.constant.OAuth2ResponseConstant.*;

/**
 * OAuth2Response
 *
 * @author BladeX
 */
@Data
public class OAuth2Response implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 实例化
	 */
	public static OAuth2Response create() {
		return new OAuth2Response();
	}

	/**
	 * 响应参数
	 */
	private Kv args = Kv.create();

	public Kv ofFailure(int errorCode, String errorDescription) {
		args.set(SUCCESS, Boolean.FALSE);
		args.set(ERROR_CODE, errorCode);
		args.set(ERROR_DESCRIPTION, errorDescription);
		return args;
	}

	public Kv ofSuccessful(String successDescription) {
		args.set(SUCCESS, Boolean.TRUE);
		args.set(SUCCESS_CODE, DEFAULT_SUCCESS_CODE);
		args.set(SUCCESS_DESCRIPTION, successDescription);
		return args;
	}

	public Kv ofSuccessful(int successCode, String successDescription) {
		args.set(SUCCESS, Boolean.TRUE);
		args.set(SUCCESS_CODE, successCode);
		args.set(SUCCESS_DESCRIPTION, successDescription);
		return args;
	}

	public Kv ofSuccessful(int successCode, String successDescription, Kv successData) {
		args.set(SUCCESS, Boolean.TRUE);
		args.set(SUCCESS_CODE, successCode);
		args.set(SUCCESS_DESCRIPTION, successDescription);
		args.setAll(successData);
		return args;
	}

	public Kv ofValidation(OAuth2Validation validation) {
		args.set(SUCCESS, validation.isSuccess());
		args.set(ERROR_CODE, validation.getCode());
		args.set(ERROR_DESCRIPTION, validation.getMessage());
		return args;
	}
}
