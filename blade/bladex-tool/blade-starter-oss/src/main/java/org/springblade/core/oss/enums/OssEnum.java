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
package org.springblade.core.oss.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Oss枚举类
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum OssEnum {

	/**
	 * minio
	 */
	MINIO("minio", 1),

	/**
	 * qiniu
	 */
	QINIU("qiniu", 2),

	/**
	 * ali
	 */
	ALI("alioss", 3),

	/**
	 * tencent
	 */
	TENCENT("tencent", 4),

	/**
	 * huawei
	 */
	HUAWEI("huawei", 5),

	/**
	 * amazons3
	 */
	AMAZONS3("amazon s3", 6);

	/**
	 * 名称
	 */
	final String name;
	/**
	 * 类型
	 */
	final int category;

	/**
	 * 匹配枚举值
	 *
	 * @param name 名称
	 * @return OssEnum
	 */
	public static OssEnum of(String name) {
		if (name == null) {
			return null;
		}
		OssEnum[] values = OssEnum.values();
		for (OssEnum ossEnum : values) {
			if (ossEnum.name.equals(name)) {
				return ossEnum;
			}
		}
		return null;
	}

}
