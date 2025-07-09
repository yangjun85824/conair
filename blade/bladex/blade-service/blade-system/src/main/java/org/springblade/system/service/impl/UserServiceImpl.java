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
package org.springblade.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.common.cache.CacheNames;
import org.springblade.common.constant.CommonConstant;
import org.springblade.common.constant.TenantConstant;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.enums.StatusType;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.BladeTenantProperties;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.*;
import org.springblade.system.cache.DictCache;
import org.springblade.system.cache.ParamCache;
import org.springblade.system.cache.SysCache;
import org.springblade.system.cache.UserCache;
import org.springblade.system.pojo.entity.*;
import org.springblade.system.pojo.enums.DictEnum;
import org.springblade.system.pojo.enums.UserType;
import org.springblade.system.excel.UserExcel;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.mapper.UserMapper;
import org.springblade.system.service.IUserDeptService;
import org.springblade.system.service.IUserOauthService;
import org.springblade.system.service.IUserService;
import org.springblade.system.pojo.vo.UserVO;
import org.springblade.system.wrapper.UserWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springblade.common.constant.CommonConstant.DEFAULT_PARAM_PASSWORD;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
	private static final String GUEST_NAME = "guest";

	private final IUserDeptService userDeptService;
	private final IUserOauthService userOauthService;
	private final ISysClient sysClient;
	private final BladeTenantProperties tenantProperties;
	private final BladeRedis bladeRedis;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submit(User user) {
		if (StringUtil.isBlank(user.getTenantId())) {
			user.setTenantId(BladeConstant.ADMIN_TENANT_ID);
		}
		String tenantId = user.getTenantId();
		Tenant tenant = SysCache.getTenant(tenantId);
		if (Func.isNotEmpty(tenant)) {
			Integer accountNumber = tenant.getAccountNumber();
			if (tenantProperties.getLicense()) {
				String licenseKey = tenant.getLicenseKey();
				String decrypt = DesUtil.decryptFormHex(licenseKey, TenantConstant.DES_KEY);
				accountNumber = JsonUtil.parse(decrypt, Tenant.class).getAccountNumber();
			}
			Long tenantCount = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId));
			if (accountNumber != null && accountNumber > 0 && accountNumber <= tenantCount) {
				throw new ServiceException("当前租户已到最大账号额度!");
			}
		}
		if (Func.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtil.encrypt(user.getPassword()));
		}
		Long userCount = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId).eq(User::getAccount, user.getAccount()));
		if (userCount > 0L && Func.isEmpty(user.getId())) {
			throw new ServiceException(StringUtil.format("当前用户 [{}] 已存在!", user.getAccount()));
		}
		Long phoneCount = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId).eq(User::getPhone, user.getPhone()));
		if (phoneCount > 0L && StringUtil.isNotBlank(user.getPhone())) {
			throw new ServiceException(StringUtil.format("当前手机 [{}] 已存在!", user.getPhone()));
		}
		return save(user) && submitUserDept(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUser(User user) {
		String tenantId = user.getTenantId();
		Long userCount = baseMapper.selectCount(
			Wrappers.<User>query().lambda()
				.eq(User::getTenantId, tenantId)
				.eq(User::getAccount, user.getAccount())
				.ne(User::getId, user.getId())
		);
		if (userCount > 0L) {
			throw new ServiceException(StringUtil.format("当前用户 [{}] 已存在!", user.getAccount()));
		}
		Long phoneCount = baseMapper.selectCount(
			Wrappers.<User>query().lambda()
				.eq(User::getTenantId, tenantId)
				.eq(User::getPhone, user.getPhone())
				.ne(User::getId, user.getId())
		);
		if (phoneCount > 0L && StringUtil.isNotBlank(user.getPhone())) {
			throw new ServiceException(StringUtil.format("当前手机 [{}] 已存在!", user.getPhone()));
		}
		return updateUserInfo(user) && submitUserDept(user);
	}

	@Override
	public boolean updateUserInfo(User user) {
		Long phoneCount = baseMapper.selectCount(
			Wrappers.<User>query().lambda()
				.eq(User::getTenantId, AuthUtil.getTenantId())
				.eq(User::getPhone, user.getPhone())
				.ne(User::getId, user.getId())
		);
		if (phoneCount > 0L && StringUtil.isNotBlank(user.getPhone())) {
			throw new ServiceException(StringUtil.format("当前手机 [{}] 已存在!", user.getPhone()));
		}
		user.setPassword(null);
		return updateById(user);
	}

	private boolean submitUserDept(User user) {
		List<Long> deptIdList = Func.toLongList(user.getDeptId());
		List<UserDept> userDeptList = new ArrayList<>();
		deptIdList.forEach(deptId -> {
			UserDept userDept = new UserDept();
			userDept.setUserId(user.getId());
			userDept.setDeptId(deptId);
			userDeptList.add(userDept);
		});
		userDeptService.remove(Wrappers.<UserDept>update().lambda().eq(UserDept::getUserId, user.getId()));
		return userDeptService.saveBatch(userDeptList);
	}

	@Override
	public IPage<User> selectUserPage(IPage<User> page, User user, Long deptId, String tenantId) {
		List<Long> deptIdList = SysCache.getDeptChildIds(deptId);
		return page.setRecords(baseMapper.selectUserPage(page, user, deptIdList, tenantId));
	}

	@Override
	public IPage<UserVO> selectUserSearch(UserVO user, Query query) {
		LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>query().lambda();
		String tenantId = AuthUtil.getTenantId();
		if (StringUtil.isNotBlank(tenantId)) {
			queryWrapper.eq(User::getTenantId, tenantId);
		}
		if (StringUtil.isNotBlank(user.getName())) {
			queryWrapper.like(User::getName, user.getName());
		}
		if (StringUtil.isNotBlank(user.getDeptName())) {
			String deptIds = SysCache.getDeptIdsByFuzzy(AuthUtil.getTenantId(), user.getDeptName());
			if (StringUtil.isNotBlank(deptIds)) {
				queryWrapper.and(wrapper -> {
					List<String> ids = Func.toStrList(deptIds);
					ids.forEach(id -> wrapper.like(User::getDeptId, id).or());
				});
			}
		}
		if (StringUtil.isNotBlank(user.getPostName())) {
			String postIds = SysCache.getPostIdsByFuzzy(AuthUtil.getTenantId(), user.getPostName());
			if (StringUtil.isNotBlank(postIds)) {
				queryWrapper.and(wrapper -> {
					List<String> ids = Func.toStrList(postIds);
					ids.forEach(id -> wrapper.like(User::getPostId, id).or());
				});
			}
		}
		IPage<User> pages = this.page(Condition.getPage(query), queryWrapper);
		return UserWrapper.build().pageVO(pages);
	}

	@Override
	public User userByAccount(String tenantId, String account) {
		return baseMapper.selectOne(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId).eq(User::getAccount, account).eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED));
	}

	@Override
	public UserInfo userInfo(Long userId) {
		User user = baseMapper.selectById(userId);
		return buildUserInfo(user);
	}

	@Override
	public UserInfo userInfo(Long userId, UserType userType) {
		User user = baseMapper.selectById(userId);
		return buildUserInfo(user, userType);
	}

	@Override
	public UserInfo userInfo(String tenantId, String account) {
		User user = baseMapper.getUser(tenantId, account);
		return buildUserInfo(user);
	}

	@Override
	public UserInfo userInfo(String tenantId, String account, UserType userType) {
		User user = baseMapper.getUser(tenantId, account);
		return buildUserInfo(user, userType);
	}

	@Override
	public UserInfo userInfoByPhone(String tenantId, String phone) {
		User user = baseMapper.getUserByPhone(tenantId, phone);
		return buildUserInfo(user);
	}

	@Override
	public UserInfo userInfoByPhone(String tenantId, String phone, UserType userType) {
		User user = baseMapper.getUserByPhone(tenantId, phone);
		return buildUserInfo(user, userType);
	}

	private UserInfo buildUserInfo(User user) {
		return buildUserInfo(user, UserType.WEB);
	}

	private UserInfo buildUserInfo(User user, UserType userType) {
		if (ObjectUtil.isEmpty(user)) {
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {
			R<List<String>> result = sysClient.getRoleAliases(user.getRoleId());
			if (result.isSuccess()) {
				List<String> roleAlias = result.getData();
				userInfo.setRoles(roleAlias);
			}
		}
		// 根据每个用户平台，建立对应的detail表，通过查询将结果集写入到detail字段
		Kv detail = Kv.create().set("type", userType.getName());
		if (userType == UserType.WEB) {
			UserWeb userWeb = new UserWeb();
			UserWeb query = userWeb.selectOne(Wrappers.<UserWeb>lambdaQuery().eq(UserWeb::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				detail.set("ext", query.getUserExt());
			}
		} else if (userType == UserType.APP) {
			UserApp userApp = new UserApp();
			UserApp query = userApp.selectOne(Wrappers.<UserApp>lambdaQuery().eq(UserApp::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				detail.set("ext", query.getUserExt());
			}
		} else {
			UserOther userOther = new UserOther();
			UserOther query = userOther.selectOne(Wrappers.<UserOther>lambdaQuery().eq(UserOther::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				detail.set("ext", query.getUserExt());
			}
		}
		userInfo.setDetail(detail);
		return userInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserInfo userInfo(UserOauth userOauth) {
		UserOauth uo = userOauthService.getOne(Wrappers.<UserOauth>query().lambda().eq(UserOauth::getUuid, userOauth.getUuid()).eq(UserOauth::getSource, userOauth.getSource()));
		UserInfo userInfo;
		if (Func.isNotEmpty(uo) && Func.isNotEmpty(uo.getUserId())) {
			userInfo = this.userInfo(uo.getUserId());
			userInfo.setOauthId(Func.toStr(uo.getId()));
		} else {
			userInfo = new UserInfo();
			if (Func.isEmpty(uo)) {
				userOauthService.save(userOauth);
				userInfo.setOauthId(Func.toStr(userOauth.getId()));
			} else {
				userInfo.setOauthId(Func.toStr(uo.getId()));
			}
			User user = new User();
			user.setAccount(userOauth.getUsername());
			user.setTenantId(userOauth.getTenantId());
			userInfo.setUser(user);
			userInfo.setRoles(Collections.singletonList(GUEST_NAME));
		}
		return userInfo;
	}

	@Override
	public boolean grant(String userIds, String roleIds) {
		User user = new User();
		user.setRoleId(roleIds);
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
	}

	@Override
	public boolean resetPassword(String userIds) {
		User user = new User();
		user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
		user.setUpdateTime(DateUtil.now());
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
	}

	@Override
	public boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1) {
		User user = getById(userId);
		if (!newPassword.equals(newPassword1)) {
			throw new ServiceException("请输入正确的确认密码!");
		}
		if (!user.getPassword().equals(DigestUtil.hex(oldPassword))) {
			throw new ServiceException("原密码不正确!");
		}
		return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.hex(newPassword)).eq(User::getId, userId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeUser(String userIds) {
		if (Func.contains(Func.toLongArray(userIds), AuthUtil.getUserId())) {
			throw new ServiceException("不能删除本账号!");
		}
		boolean tempUser = this.deleteLogic(Func.toLongList(userIds));
		boolean tempUserDept = userDeptService.remove(Wrappers.<UserDept>lambdaQuery().in(UserDept::getUserId, Func.toLongList(userIds)));
		if (tempUser && tempUserDept) {
			UserOauth userOauth = new UserOauth();
			userOauth.delete(Wrappers.<UserOauth>lambdaQuery().in(UserOauth::getUserId, Func.toLongList(userIds)));
			UserWeb userWeb = new UserWeb();
			userWeb.delete(Wrappers.<UserWeb>lambdaQuery().in(UserWeb::getUserId, Func.toLongList(userIds)));
			UserApp userApp = new UserApp();
			userApp.delete(Wrappers.<UserApp>lambdaQuery().in(UserApp::getUserId, Func.toLongList(userIds)));
			UserOther userOther = new UserOther();
			userOther.delete(Wrappers.<UserOther>lambdaQuery().in(UserOther::getUserId, Func.toLongList(userIds)));
			return true;
		} else {
			throw new ServiceException("删除用户失败!");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importUser(List<UserExcel> data, Boolean isCovered) {
		data.forEach(userExcel -> {
			User user = Objects.requireNonNull(BeanUtil.copyProperties(userExcel, User.class));
			// 设置用户平台
			user.setUserType(Func.toInt(DictCache.getKey(DictEnum.USER_TYPE, userExcel.getUserTypeName()), 1));
			// 设置部门ID
			user.setDeptId(Func.toStrWithEmpty(SysCache.getDeptIds(userExcel.getTenantId(), userExcel.getDeptName()), StringPool.EMPTY));
			// 设置岗位ID
			user.setPostId(Func.toStrWithEmpty(SysCache.getPostIds(userExcel.getTenantId(), userExcel.getPostName()), StringPool.EMPTY));
			// 设置角色ID
			user.setRoleId(Func.toStrWithEmpty(SysCache.getRoleIds(userExcel.getTenantId(), userExcel.getRoleName()), StringPool.EMPTY));
			// 设置租户ID
			if (!AuthUtil.isAdministrator() || StringUtil.isBlank(user.getTenantId())) {
				user.setTenantId(AuthUtil.getTenantId());
			}
			// 覆盖数据
			if (isCovered) {
				// 查询用户是否存在
				User oldUser = UserCache.getUser(userExcel.getTenantId(), userExcel.getAccount());
				if (oldUser != null && oldUser.getId() != null) {
					user.setId(oldUser.getId());
					this.updateUser(user);
					return;
				}
			}
			// 获取默认密码配置
			String initPassword = ParamCache.getValue(DEFAULT_PARAM_PASSWORD);
			user.setPassword(initPassword);
			this.submit(user);
		});
	}

	@Override
	public List<UserExcel> exportUser(Wrapper<User> queryWrapper) {
		List<UserExcel> userList = baseMapper.exportUser(queryWrapper);
		userList.forEach(user -> {
			user.setUserTypeName(DictCache.getValue(DictEnum.USER_TYPE, user.getUserType()));
			user.setRoleName(StringUtil.join(SysCache.getRoleNames(user.getRoleId())));
			user.setDeptName(StringUtil.join(SysCache.getDeptNames(user.getDeptId())));
			user.setPostName(StringUtil.join(SysCache.getPostNames(user.getPostId())));
		});
		return userList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerGuest(User user, Long oauthId) {
		Tenant tenant = SysCache.getTenant(user.getTenantId());
		if (tenant == null || tenant.getId() == null) {
			throw new ServiceException("租户信息错误!");
		}
		UserOauth userOauth = userOauthService.getById(oauthId);
		if (userOauth == null || userOauth.getId() == null) {
			throw new ServiceException("第三方登陆信息错误!");
		}
		user.setRealName(user.getName());
		user.setAvatar(userOauth.getAvatar());
		user.setRoleId(StringPool.MINUS_ONE);
		user.setDeptId(StringPool.MINUS_ONE);
		user.setPostId(StringPool.MINUS_ONE);
		user.setStatus(StatusType.IN_ACTIVE.getType());
		boolean userTemp = this.submit(user);
		userOauth.setUserId(user.getId());
		userOauth.setTenantId(user.getTenantId());
		boolean oauthTemp = userOauthService.updateById(userOauth);
		return (userTemp && oauthTemp);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerUser(User user) {
		Tenant tenant = SysCache.getTenant(user.getTenantId());
		if (tenant == null || tenant.getId() == null) {
			throw new ServiceException("租户信息错误!");
		}
		user.setRealName(user.getName());
		user.setRoleId(StringPool.MINUS_ONE);
		user.setDeptId(StringPool.MINUS_ONE);
		user.setPostId(StringPool.MINUS_ONE);
		user.setStatus(StatusType.IN_ACTIVE.getType());
		return this.submit(user);
	}

	@Override
	public boolean updatePlatform(Long userId, Integer userType, String userExt) {
		if (userType.equals(UserType.WEB.getCategory())) {
			UserWeb userWeb = new UserWeb();
			UserWeb query = userWeb.selectOne(Wrappers.<UserWeb>lambdaQuery().eq(UserWeb::getUserId, userId));
			if (ObjectUtil.isNotEmpty(query)) {
				userWeb.setId(query.getId());
			}
			userWeb.setUserId(userId);
			userWeb.setUserExt(userExt);
			return userWeb.insertOrUpdate();
		} else if (userType.equals(UserType.APP.getCategory())) {
			UserApp userApp = new UserApp();
			UserApp query = userApp.selectOne(Wrappers.<UserApp>lambdaQuery().eq(UserApp::getUserId, userId));
			if (ObjectUtil.isNotEmpty(query)) {
				userApp.setId(query.getId());
			}
			userApp.setUserId(userId);
			userApp.setUserExt(userExt);
			return userApp.insertOrUpdate();
		} else {
			UserOther userOther = new UserOther();
			UserOther query = userOther.selectOne(Wrappers.<UserOther>lambdaQuery().eq(UserOther::getUserId, userId));
			if (ObjectUtil.isNotEmpty(query)) {
				userOther.setId(query.getId());
			}
			userOther.setUserId(userId);
			userOther.setUserExt(userExt);
			return userOther.insertOrUpdate();
		}
	}

	@Override
	public UserVO platformDetail(User user) {
		User detail = baseMapper.selectOne(Condition.getQueryWrapper(user));
		UserVO userVO = UserWrapper.build().entityVO(detail);
		if (userVO.getUserType().equals(UserType.WEB.getCategory())) {
			UserWeb userWeb = new UserWeb();
			UserWeb query = userWeb.selectOne(Wrappers.<UserWeb>lambdaQuery().eq(UserWeb::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				userVO.setUserExt(query.getUserExt());
			}
		} else if (userVO.getUserType().equals(UserType.APP.getCategory())) {
			UserApp userApp = new UserApp();
			UserApp query = userApp.selectOne(Wrappers.<UserApp>lambdaQuery().eq(UserApp::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				userVO.setUserExt(query.getUserExt());
			}
		} else {
			UserOther userOther = new UserOther();
			UserOther query = userOther.selectOne(Wrappers.<UserOther>lambdaQuery().eq(UserOther::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				userVO.setUserExt(query.getUserExt());
			}
		}
		return userVO;
	}

	@Override
	public boolean unlock(String userIds) {
		if (StringUtil.isBlank(userIds)) {
			throw new ServiceException("请至少选择一个用户!");
		}
		List<User> userList = baseMapper.selectList(Wrappers.<User>lambdaQuery().in(User::getId, Func.toLongList(userIds)));
		userList.forEach(user -> bladeRedis.del(CacheNames.tenantKey(user.getTenantId(), CacheNames.USER_FAIL_KEY, user.getAccount())));
		return true;
	}

	@Override
	public boolean auditPass(String userIds) {
		if (StringUtil.isBlank(userIds)) {
			throw new ServiceException("请至少选择一个用户!");
		}
		List<User> userList = baseMapper.selectList(Wrappers.<User>lambdaQuery().in(User::getId, Func.toLongList(userIds)));
		userList.forEach(user -> {
			boolean roleTemp = StringUtil.isBlank(user.getRoleId()) || user.getRoleId().equals(StringPool.MINUS_ONE);
			boolean deptTemp = StringUtil.isBlank(user.getDeptId()) || user.getDeptId().equals(StringPool.MINUS_ONE);
			boolean postTemp = StringUtil.isBlank(user.getPostId()) || user.getPostId().equals(StringPool.MINUS_ONE);
			if (roleTemp || deptTemp || postTemp) {
				throw new ServiceException("请先给账号 [" + user.getAccount() + "] 分配角色、部门、岗位后再审批通过!");
			}
		});
		return changeStatus(Func.toLongList(userIds), StatusType.ACTIVE.getType());
	}

	@Override
	public boolean auditRefuse(String userIds) {
		if (StringUtil.isBlank(userIds)) {
			throw new ServiceException("请至少选择一个用户!");
		}
		return changeStatus(Func.toLongList(userIds), StatusType.DISABLED.getType());
	}

}
