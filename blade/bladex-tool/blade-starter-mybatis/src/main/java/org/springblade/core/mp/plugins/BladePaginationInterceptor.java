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
package org.springblade.core.mp.plugins;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.OracleDialect;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springblade.core.mp.intercept.QueryInterceptor;

import java.sql.Connection;

/**
 * 拓展分页拦截器
 *
 * @author Chill
 */
@Setter
public class BladePaginationInterceptor extends PaginationInnerInterceptor {

	/**
	 * 查询拦截器
	 */
	private QueryInterceptor[] queryInterceptors;

	@SneakyThrows
	@Override
	public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
		QueryInterceptorExecutor.exec(queryInterceptors, executor, ms, parameter, rowBounds, resultHandler, boundSql);
		return super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
	}

	@SneakyThrows
	@Override
	public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
		super.setDialect(autoDialect(executor));
		super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
	}

	/**
	 * 自动配置分页方言类的逻辑
	 *
	 * @param executor Executor
	 * @return 分页方言类
	 */
	@SneakyThrows
	protected IDialect autoDialect(Executor executor) {
		// 增加YashanDB方言
		Connection conn = executor.getTransaction().getConnection();
		if (conn.getMetaData().getURL().contains(":yasdb:")) {
			return new OracleDialect();
		} else {
			return DialectFactory.getDialect(JdbcUtils.getDbType(executor));
		}
	}

}
