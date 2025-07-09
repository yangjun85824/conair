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
package org.springblade.core.mp.plugins;

import com.alibaba.druid.DbType;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.mp.props.MybatisPlusProperties;
import org.springblade.core.tool.utils.StringUtil;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 打印可执行的 sql 日志
 *
 * @author L.cm，Chill
 */
@Slf4j
public class SqlLogInterceptor extends FilterEventAdapter {
	private static final SQLUtils.FormatOption FORMAT_OPTION = new SQLUtils.FormatOption(false, false);

	private static final List<String> SQL_LOG_EXCLUDE = new ArrayList<>(Arrays.asList("ACT_RU_JOB", "ACT_RU_TIMER_JOB"));

	private final MybatisPlusProperties properties;

	public SqlLogInterceptor(MybatisPlusProperties properties) {
		this.properties = properties;
		if (properties.getSqlLogExclude().size() > 0) {
			SQL_LOG_EXCLUDE.addAll(properties.getSqlLogExclude());
		}
	}

	@Override
	protected void statementExecuteBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteBatchBefore(StatementProxy statement) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	@SneakyThrows
	public void statement_close(FilterChain chain, StatementProxy statement) {
		// 是否开启日志
		if (!properties.getSqlLog()) {
			chain.statement_close(statement);
			return;
		}
		// 是否开启调试
		if (!log.isInfoEnabled()) {
			chain.statement_close(statement);
			return;
		}
		// 打印可执行的 sql
		String sql = statement.getBatchSql();
		// sql 为空直接返回
		if (StringUtil.isEmpty(sql)) {
			chain.statement_close(statement);
			return;
		}
		// sql 包含排除的关键字直接返回
		if (excludeSql(sql)) {
			chain.statement_close(statement);
			return;
		}
		int parametersSize = statement.getParametersSize();
		List<Object> parameters = new ArrayList<>(parametersSize);
		for (int i = 0; i < parametersSize; ++i) {
			// 转换参数，处理 java8 时间
			parameters.add(getJdbcParameter(statement.getParameter(i)));
		}
		String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
		String formattedSql = SQLUtils.format(sql, DbType.of(dbType), parameters, FORMAT_OPTION);
		printSql(formattedSql, statement);
		chain.statement_close(statement);
	}

	private static Object getJdbcParameter(JdbcParameter jdbcParam) {
		if (jdbcParam == null) {
			return null;
		}
		Object value = jdbcParam.getValue();
		// 处理 java8 时间
		if (value instanceof TemporalAccessor) {
			return value.toString();
		}
		return value;
	}

	private static void printSql(String sql, StatementProxy statement) {
		// 打印 sql
		String sqlLogger = "\n\n==============  Sql Start  ==============" +
			"\nExecute SQL : {}" +
			"\nExecute Time: {}" +
			"\n==============  Sql  End   ==============\n";
		log.info(sqlLogger, sql.trim(), StringUtil.format(statement.getLastExecuteTimeNano()));
	}

	private static boolean excludeSql(String sql) {
		// 判断关键字
		for (String exclude : SQL_LOG_EXCLUDE) {
			if (sql.contains(exclude)) {
				return true;
			}
		}
		return false;
	}

}
