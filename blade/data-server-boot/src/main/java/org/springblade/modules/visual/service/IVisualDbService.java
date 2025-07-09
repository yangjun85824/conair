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
package org.springblade.modules.visual.service;

import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.visual.pojo.dto.VisualDbDTO;
import org.springblade.modules.visual.pojo.entity.VisualDb;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 可视化数据源配置表 服务类
 *
 * @author Chill
 */
public interface IVisualDbService extends BaseService<VisualDb> {

	/**
	 * 数据源测试连接
	 *
	 * @param db 数据源
	 * @return Boolean
	 */
	Boolean dbTest(VisualDb db);

	/**
	 * 数据源列表
	 *
	 * @return List
	 */
	List<VisualDbDTO> dbList();

	/**
	 * 自定义调用动态sql
	 *
	 * @param id  数据源id
	 * @param sql 可执行sql语句
	 * @return List
	 */
	List<LinkedHashMap<String, Object>> dynamicQuery(String id, String sql);

}
