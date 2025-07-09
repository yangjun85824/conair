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
package org.springblade.system.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * UserExcel
 *
 * @author Chill
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class UserExcel implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@ColumnWidth(15)
	@ExcelProperty("租户编号")
	private String tenantId;

	@ExcelIgnore
	@ExcelProperty("用户平台")
	private String userType;

	@ColumnWidth(20)
	@ExcelProperty("用户平台名称")
	private String userTypeName;

	@ColumnWidth(15)
	@ExcelProperty("账户")
	private String account;

	@ColumnWidth(10)
	@ExcelProperty("昵称")
	private String name;

	@ColumnWidth(10)
	@ExcelProperty("姓名")
	private String realName;

	@ExcelProperty("邮箱")
	private String email;

	@ColumnWidth(15)
	@ExcelProperty("手机")
	private String phone;

	@ExcelIgnore
	@ExcelProperty("角色ID")
	private String roleId;

	@ExcelIgnore
	@ExcelProperty("部门ID")
	private String deptId;

	@ExcelIgnore
	@ExcelProperty("岗位ID")
	private String postId;

	@ExcelProperty("角色名称")
	private String roleName;

	@ExcelProperty("部门名称")
	private String deptName;

	@ExcelProperty("岗位名称")
	private String postName;

	@ColumnWidth(20)
	@ExcelProperty("生日")
	private Date birthday;

}
