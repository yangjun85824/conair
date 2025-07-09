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
package org.springblade.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.cache.DictCache;
import org.springblade.system.cache.SysCache;
import org.springblade.system.pojo.entity.Tenant;
import org.springblade.system.pojo.entity.User;
import org.springblade.system.pojo.enums.DictEnum;
import org.springblade.system.pojo.vo.UserVO;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = Objects.requireNonNull(BeanUtil.copyProperties(user, UserVO.class));
		Tenant tenant = SysCache.getTenant(user.getTenantId());
		if (StringUtil.isNotBlank(user.getRoleId()) && !StringUtil.equals(user.getRoleId(), StringPool.MINUS_ONE)) {
			List<String> roleName = SysCache.getRoleNames(user.getRoleId());
			userVO.setRoleName(Func.join(roleName));
		} else {
			userVO.setRoleId(StringPool.EMPTY);
			userVO.setRoleName("暂未分配");
		}
		if (StringUtil.isNotBlank(user.getDeptId()) && !StringUtil.equals(user.getDeptId(), StringPool.MINUS_ONE)) {
			List<String> deptName = SysCache.getDeptNames(user.getDeptId());
			userVO.setDeptName(Func.join(deptName));
		} else {
			userVO.setDeptId(StringPool.EMPTY);
			userVO.setDeptName("暂未分配");
		}
		if (StringUtil.isNotBlank(user.getPostId()) && !StringUtil.equals(user.getPostId(), StringPool.MINUS_ONE)) {
			List<String> postName = SysCache.getPostNames(user.getPostId());
			userVO.setPostName(Func.join(postName));
		} else {
			userVO.setPostId(StringPool.EMPTY);
			userVO.setPostName("暂未分配");
		}
		userVO.setTenantName(tenant.getTenantName());
		userVO.setSexName(DictCache.getValue(DictEnum.SEX, user.getSex()));
		userVO.setUserTypeName(DictCache.getValue(DictEnum.USER_TYPE, user.getUserType()));
		return userVO;
	}

}
