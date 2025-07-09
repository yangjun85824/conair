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
package org.flowable.editor.language.json.converter;

import java.util.Map;

/**
 * BpmnJsonCustomConverter
 *
 * @author Chill
 */
public class CustomBpmnJsonConverterContext extends StandaloneBpmnConverterContext {
	private final Map<String, String> formKeyMap;
	private final Map<String, String> decisionTableKeyMap;

	public CustomBpmnJsonConverterContext(Map<String, String> formKeyMap, Map<String, String> decisionTableKeyMap) {
		this.formKeyMap = formKeyMap;
		this.decisionTableKeyMap = decisionTableKeyMap;
	}

	@Override
	public String getFormModelKeyForFormModelId(String formModelId) {
		return formKeyMap.get(formModelId);
	}

	@Override
	public String getDecisionTableModelKeyForDecisionTableModelId(String decisionTableModelId) {
		return decisionTableKeyMap.get(decisionTableModelId);
	}
}
