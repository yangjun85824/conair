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
package org.springblade.core.auto.service;

import java.lang.annotation.*;

/**
 * An annotation for service providers as described in {@link java.util.ServiceLoader}. The {@link
 * AutoServiceProcessor} generates the configuration files which
 * allows service providers to be loaded with {@link java.util.ServiceLoader#load(Class)}.
 *
 * <p>Service providers assert that they conform to the service provider specification.
 * Specifically, they must:
 *
 * <ul>
 * <li>be a non-inner, non-anonymous, concrete class
 * <li>have a publicly accessible no-arg constructor
 * <li>implement the interface type returned by {@code value()}
 * </ul>
 *
 * @author google
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoService {
	/**
	 * Returns the interfaces implemented by this service provider.
	 *
	 * @return interface array
	 */
	Class<?>[] value();
}
