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
package org.springblade.core.http;

import java.lang.annotation.*;

/**
 * xml CssQuery
 *
 * @author L.cm
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CssQuery {

	/**
	 * CssQuery
	 *
	 * @return CssQuery
	 */
	String value();

	/**
	 * 读取的 dom attr
	 *
	 * <p>
	 * attr：元素对于的 attr 的值
	 * html：整个元素的html
	 * text：元素内文本
	 * allText：多个元素的文本值
	 * </p>
	 *
	 * @return attr
	 */
	String attr() default "";

	/**
	 * 正则，用于对 attr value 处理
	 *
	 * @return regex
	 */
	String regex() default "";

	/**
	 * 默认的正则 group
	 */
	int DEFAULT_REGEX_GROUP = 0;

	/**
	 * 正则 group，默认为 0
	 *
	 * @return regexGroup
	 */
	int regexGroup() default DEFAULT_REGEX_GROUP;

	/**
	 * 嵌套的内部模型：默认 false
	 *
	 * @return 是否为内部模型
	 */
	boolean inner() default false;
}
