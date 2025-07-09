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

package org.springblade.core.launch.props;

import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 自定义资源文件读取，优先级最低
 *
 * @author L.cm
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BladePropertySource {

	/**
	 * Indicate the resource location(s) of the properties file to be loaded.
	 * for example, {@code "classpath:/com/example/app.yml"}
	 *
	 * @return location(s)
	 */
	String value();

	/**
	 * load app-{activeProfile}.yml
	 *
	 * @return {boolean}
	 */
	boolean loadActiveProfile() default true;

	/**
	 * Get the order value of this resource.
	 *
	 * @return order
	 */
	int order() default Ordered.LOWEST_PRECEDENCE;

}
