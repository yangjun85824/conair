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


import org.springblade.core.tool.beans.BeanProperty;
import org.springblade.core.tool.beans.BladeBeanCopier;
import org.springblade.core.tool.beans.BladeBeanMap;
import org.springblade.core.tool.convert.BladeConverter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * 实体工具类
 *
 * @author L.cm
 */
public class BeanUtil extends org.springframework.beans.BeanUtils {

	/**
	 * 实例化对象
	 *
	 * @param clazz 类
	 * @param <T>   泛型标记
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> clazz) {
		return (T) instantiateClass(clazz);
	}

	/**
	 * 实例化对象
	 *
	 * @param clazzStr 类名
	 * @param <T>      泛型标记
	 * @return 对象
	 */
	public static <T> T newInstance(String clazzStr) {
		try {
			Class<?> clazz = ClassUtil.forName(clazzStr, null);
			return newInstance(clazz);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取Bean的属性, 支持 propertyName 多级 ：test.user.name
	 *
	 * @param bean         bean
	 * @param propertyName 属性名
	 * @return 属性值
	 */
	@Nullable
	public static Object getProperty(@Nullable Object bean, String propertyName) {
		if (bean == null) {
			return null;
		}
		BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
		return beanWrapper.getPropertyValue(propertyName);
	}

	/**
	 * 设置Bean属性, 支持 propertyName 多级 ：test.user.name
	 *
	 * @param bean         bean
	 * @param propertyName 属性名
	 * @param value        属性值
	 */
	public static void setProperty(Object bean, String propertyName, Object value) {
		Objects.requireNonNull(bean, "bean Could not null");
		BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
		beanWrapper.setPropertyValue(propertyName, value);
	}

	/**
	 * 深复制
	 *
	 * <p>
	 * 支持 map bean
	 * </p>
	 *
	 * @param source 源对象
	 * @param <T>    泛型标记
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> T clone(@Nullable T source) {
		if (source == null) {
			return null;
		}
		return (T) BeanUtil.copy(source, source.getClass());
	}

	/**
	 * copy 对象属性，默认不使用Convert
	 *
	 * <p>
	 * 支持 map bean copy
	 * </p>
	 *
	 * @param source 源对象
	 * @param clazz  类名
	 * @param <T>    泛型标记
	 * @return T
	 */
	@Nullable
	public static <T> T copy(@Nullable Object source, Class<T> clazz) {
		if (source == null) {
			return null;
		}
		return BeanUtil.copy(source, source.getClass(), clazz);
	}

	/**
	 * copy 对象属性，默认不使用Convert
	 *
	 * <p>
	 * 支持 map bean copy
	 * </p>
	 *
	 * @param source      源对象
	 * @param sourceClazz 源类型
	 * @param targetClazz 转换成的类型
	 * @param <T>         泛型标记
	 * @return T
	 */
	@Nullable
	public static <T> T copy(@Nullable Object source, Class sourceClazz, Class<T> targetClazz) {
		if (source == null) {
			return null;
		}
		BladeBeanCopier copier = BladeBeanCopier.create(sourceClazz, targetClazz, false);
		T to = newInstance(targetClazz);
		copier.copy(source, to, null);
		return to;
	}

	/**
	 * copy 列表对象，默认不使用Convert
	 *
	 * <p>
	 * 支持 map bean copy
	 * </p>
	 *
	 * @param sourceList  源列表
	 * @param targetClazz 转换成的类型
	 * @param <T>         泛型标记
	 * @return T
	 */
	public static <T> List<T> copy(@Nullable Collection<?> sourceList, Class<T> targetClazz) {
		if (sourceList == null || sourceList.isEmpty()) {
			return Collections.emptyList();
		}
		List<T> outList = new ArrayList<>(sourceList.size());
		Class<?> sourceClazz = null;
		for (Object source : sourceList) {
			if (source == null) {
				continue;
			}
			if (sourceClazz == null) {
				sourceClazz = source.getClass();
			}
			T bean = BeanUtil.copy(source, sourceClazz, targetClazz);
			outList.add(bean);
		}
		return outList;
	}

	/**
	 * 拷贝对象
	 *
	 * <p>
	 * 支持 map bean copy
	 * </p>
	 *
	 * @param source     源对象
	 * @param targetBean 需要赋值的对象
	 */
	public static void copy(@Nullable Object source, @Nullable Object targetBean) {
		if (source == null || targetBean == null) {
			return;
		}
		BladeBeanCopier copier = BladeBeanCopier
			.create(source.getClass(), targetBean.getClass(), false);

		copier.copy(source, targetBean, null);
	}

	/**
	 * 拷贝对象，source 属性做 null 判断，Map 不支持，map 会做 instanceof 判断，不会
	 *
	 * <p>
	 * 支持 bean copy
	 * </p>
	 *
	 * @param source     源对象
	 * @param targetBean 需要赋值的对象
	 */
	public static void copyNonNull(@Nullable Object source, @Nullable Object targetBean) {
		if (source == null || targetBean == null) {
			return;
		}
		BladeBeanCopier copier = BladeBeanCopier
			.create(source.getClass(), targetBean.getClass(), false, true);

		copier.copy(source, targetBean, null);
	}

	/**
	 * 拷贝对象并对不同类型属性进行转换
	 *
	 * <p>
	 * 支持 map bean copy
	 * </p>
	 *
	 * @param source 源对象
	 * @param targetClazz 转换成的类
	 * @param <T>    泛型标记
	 * @return T
	 */
	@Nullable
	public static <T> T copyWithConvert(@Nullable Object source, Class<T> targetClazz) {
		if (source == null) {
			return null;
		}
		return BeanUtil.copyWithConvert(source, source.getClass(), targetClazz);
	}

	/**
	 * 拷贝对象并对不同类型属性进行转换
	 *
	 * <p>
	 * 支持 map bean copy
	 * </p>
	 *
	 * @param source 源对象
	 * @param sourceClazz 源类
	 * @param targetClazz 转换成的类
	 * @param <T>    泛型标记
	 * @return T
	 */
	@Nullable
	public static <T> T copyWithConvert(@Nullable Object source, Class<?> sourceClazz, Class<T> targetClazz) {
		if (source == null) {
			return null;
		}
		BladeBeanCopier copier = BladeBeanCopier.create(sourceClazz, targetClazz, true);
		T to = newInstance(targetClazz);
		copier.copy(source, to, new BladeConverter(sourceClazz, targetClazz));
		return to;
	}

	/**
	 * 拷贝列表并对不同类型属性进行转换
	 *
	 * <p>
	 * 支持 map bean copy
	 * </p>
	 *
	 * @param sourceList 源对象列表
	 * @param targetClazz 转换成的类
	 * @param <T>    泛型标记
	 * @return List
	 */
	public static <T> List<T> copyWithConvert(@Nullable Collection<?> sourceList, Class<T> targetClazz) {
		if (sourceList == null || sourceList.isEmpty()) {
			return Collections.emptyList();
		}
		List<T> outList = new ArrayList<>(sourceList.size());
		Class<?> sourceClazz = null;
		for (Object source : sourceList) {
			if (source == null) {
				continue;
			}
			if (sourceClazz == null) {
				sourceClazz = source.getClass();
			}
			T bean = BeanUtil.copyWithConvert(source, sourceClazz, targetClazz);
			outList.add(bean);
		}
		return outList;
	}

	/**
	 * Copy the property values of the given source bean into the target class.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 *
	 * @param source the source bean
	 * @param targetClazz the target bean class
	 * @param <T>    泛型标记
	 * @return T
	 * @throws BeansException if the copying failed
	 */
	@Nullable
	public static <T> T copyProperties(@Nullable Object source, Class<T> targetClazz) throws BeansException {
		if (source == null) {
			return null;
		}
		T to = newInstance(targetClazz);
		BeanUtil.copyProperties(source, to);
		return to;
	}

	/**
	 * Copy the property values of the given source bean into the target class.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 *
	 * @param sourceList the source list bean
	 * @param targetClazz the target bean class
	 * @param <T>    泛型标记
	 * @return List
	 * @throws BeansException if the copying failed
	 */
	public static <T> List<T> copyProperties(@Nullable Collection<?> sourceList, Class<T> targetClazz) throws BeansException {
		if (sourceList == null || sourceList.isEmpty()) {
			return Collections.emptyList();
		}
		List<T> outList = new ArrayList<>(sourceList.size());
		for (Object source : sourceList) {
			if (source == null) {
				continue;
			}
			T bean = BeanUtil.copyProperties(source, targetClazz);
			outList.add(bean);
		}
		return outList;
	}

	/**
	 * 将对象装成map形式
	 *
	 * @param bean 源对象
	 * @return {Map}
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(@Nullable Object bean) {
		if (bean == null) {
			return new HashMap<>(0);
		}
		return BladeBeanMap.create(bean);
	}

	/**
	 * 将map 转为 bean
	 *
	 * @param beanMap   map
	 * @param valueType 对象类型
	 * @param <T>       泛型标记
	 * @return {T}
	 */
	public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
		Objects.requireNonNull(beanMap, "beanMap Could not null");
		T to = newInstance(valueType);
		if (beanMap.isEmpty()) {
			return to;
		}
		BeanUtil.copy(beanMap, to);
		return to;
	}

	/**
	 * 给一个Bean添加字段
	 *
	 * @param superBean 父级Bean
	 * @param props     新增属性
	 * @return {Object}
	 */
	@Nullable
	public static Object generator(@Nullable Object superBean, BeanProperty... props) {
		if (superBean == null) {
			return null;
		}
		Class<?> superclass = superBean.getClass();
		Object genBean = generator(superclass, props);
		BeanUtil.copy(superBean, genBean);
		return genBean;
	}

	/**
	 * 给一个class添加字段
	 *
	 * @param superclass 父级
	 * @param props      新增属性
	 * @return {Object}
	 */
	public static Object generator(Class<?> superclass, BeanProperty... props) {
		BeanGenerator generator = new BeanGenerator();
		generator.setSuperclass(superclass);
		generator.setUseCache(true);
		for (BeanProperty prop : props) {
			generator.addProperty(prop.getName(), prop.getType());
		}
		return generator.create();
	}

}
