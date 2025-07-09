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
package org.springblade.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.core.tenant.annotation.TenantIgnore;
import org.springblade.system.pojo.entity.DictBiz;
import org.springblade.system.pojo.vo.DictBizVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface DictBizMapper extends BaseMapper<DictBiz> {

	/**
	 * 获取字典表对应中文
	 *
	 * @param tenantId 租户ID
	 * @param code     字典编号
	 * @param dictKey  字典序号
	 * @return
	 */
	@TenantIgnore
	String getValue(String tenantId, String code, String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param tenantId 租户ID
	 * @param code     字典编号
	 * @return
	 */
	@TenantIgnore
	List<DictBiz> getList(String tenantId, String code);

	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictBizVO> tree();

	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictBizVO> parentTree();

}
