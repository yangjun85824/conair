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
package org.springblade.system.feign;

import org.springblade.core.datascope.model.DataScopeModel;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 数据权限Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = IDataScopeClientFallback.class
)
public interface IDataScopeClient {

	String API_PREFIX = "/feign/client/data-scope";
	String GET_DATA_SCOPE_BY_MAPPER = API_PREFIX + "/by-mapper";
	String GET_DATA_SCOPE_BY_CODE = API_PREFIX + "/by-code";
	String GET_DEPT_ANCESTORS = API_PREFIX + "/dept-ancestors";

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	@GetMapping(GET_DATA_SCOPE_BY_MAPPER)
	DataScopeModel getDataScopeByMapper(@RequestParam("mapperId") String mapperId, @RequestParam("roleId") String roleId);

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	@GetMapping(GET_DATA_SCOPE_BY_CODE)
	DataScopeModel getDataScopeByCode(@RequestParam("code") String code);

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	@GetMapping(GET_DEPT_ANCESTORS)
	List<Long> getDeptAncestors(@RequestParam("deptId") Long deptId);


}
