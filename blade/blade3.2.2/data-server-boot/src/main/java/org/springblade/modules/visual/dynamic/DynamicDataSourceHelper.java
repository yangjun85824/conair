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
package org.springblade.modules.visual.dynamic;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 *
 * @author Chill
 */
public class DynamicDataSourceHelper {

	/**
	 * 测试数据库链接
	 */
	public Boolean dbTest(String driverClass, String url, String username, String password) {
		Connection conn = null;
		try {
			//测试驱动类
			Class.forName(driverClass);
			//创建连接
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(Boolean.FALSE);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			//关闭连接
			dbClose(conn);
		}
	}

	/**
	 * 关闭数据库链接
	 */
	private void dbClose(Connection conn) {
		try {
			//关闭数据源连接
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
