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
package org.springblade.core.tenant;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.CollectionUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 租户拦截器
 *
 * @author Chill
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BladeTenantInterceptor extends TenantLineInnerInterceptor {

	/**
	 * 租户处理器
	 */
	private TenantLineHandler tenantLineHandler;
	/**
	 * 租户配置文件
	 */
	private BladeTenantProperties tenantProperties;
	/**
	 * 超管需要启用租户过滤的表
	 */
	private List<String> adminTenantTables = Arrays.asList("blade_top_menu", "blade_dict_biz");

	@Override
	public void setTenantLineHandler(TenantLineHandler tenantLineHandler) {
		super.setTenantLineHandler(tenantLineHandler);
		this.tenantLineHandler = tenantLineHandler;
	}

	@Override
	protected void processInsert(Insert insert, int index, String sql, Object obj) {
		// 官方已支持租户ID自定义设置，无需再定义租户增强功能
		super.processInsert(insert, index, sql, obj);
	}

	/**
	 * 处理 PlainSelect
	 */
	@Override
	protected void processPlainSelect(final PlainSelect plainSelect, final String whereSegment) {
		//#3087 github
		List<SelectItem<?>> selectItems = plainSelect.getSelectItems();
		if (CollectionUtils.isNotEmpty(selectItems)) {
			selectItems.forEach(selectItem -> processSelectItem(selectItem, whereSegment));
		}

		// 处理 where 中的子查询
		Expression where = plainSelect.getWhere();
		processWhereSubSelect(where, whereSegment);

		// 处理 fromItem
		FromItem fromItem = plainSelect.getFromItem();
		List<Table> list = processFromItem(fromItem, whereSegment);
		List<Table> mainTables = new ArrayList<>(list);

		// 处理 join
		List<Join> joins = plainSelect.getJoins();
		if (CollectionUtils.isNotEmpty(joins)) {
			processJoins(mainTables, joins, whereSegment);
		}

		// 当有 mainTable 时，进行 where 条件追加
		if (CollectionUtils.isNotEmpty(mainTables) && !doTenantFilters(mainTables)) {
			plainSelect.setWhere(builderExpression(where, mainTables, whereSegment));
		}
	}

	/**
	 * update 语句处理
	 */
	@Override
	protected void processUpdate(Update update, int index, String sql, Object obj) {
		final Table table = update.getTable();
		if (tenantLineHandler.ignoreTable(table.getName())) {
			// 过滤退出执行
			return;
		}
		if (doTenantFilter(table.getName())) {
			// 过滤退出执行
			return;
		}
		List<UpdateSet> sets = update.getUpdateSets();
		if (!CollectionUtils.isEmpty(sets)) {
			sets.forEach(us -> us.getValues().forEach(ex -> {
				if (ex instanceof Select) {
					processSelectBody(((Select) ex), (String) obj);
				}
			}));
		}
		update.setWhere(this.andExpression(table, update.getWhere(), (String) obj));
	}

	/**
	 * delete 语句处理
	 */
	@Override
	protected void processDelete(Delete delete, int index, String sql, Object obj) {
		final Table table = delete.getTable();
		if (tenantLineHandler.ignoreTable(table.getName())) {
			// 过滤退出执行
			return;
		}
		if (doTenantFilter(table.getName())) {
			// 过滤退出执行
			return;
		}
		delete.setWhere(this.andExpression(table, delete.getWhere(), (String) obj));
	}

	/**
	 * delete update 语句 where 处理
	 */
	@Override
	protected Expression andExpression(Table table, Expression where, final String whereSegment) {
		//获得where条件表达式
		final Expression expression = buildTableExpression(table, where, whereSegment);
		if (expression == null) {
			return where;
		}
		if (where != null) {
			if (where instanceof OrExpression) {
				return new AndExpression(new Parenthesis(where), expression);
			} else {
				return new AndExpression(where, expression);
			}
		}
		return expression;
	}

	/**
	 * 构建租户条件表达式
	 *
	 * @param table        表对象
	 * @param where        当前where条件
	 * @param whereSegment 所属Mapper对象全路径（在原租户拦截器功能中，这个参数并不需要参与相关判断）
	 * @return 租户条件表达式
	 */
	@Override
	public Expression buildTableExpression(final Table table, final Expression where, final String whereSegment) {
		//若是忽略的表则不进行数据隔离
		if (tenantLineHandler.ignoreTable(table.getName())) {
			return null;
		}
		//若是超管则不进行数据隔离
		if (doTenantFilter(table.getName())) {
			return null;
		}
		//获得条件表达式
		return new EqualsTo(getAliasColumn(table), tenantLineHandler.getTenantId());
	}

	private List<Table> processFromItem(FromItem fromItem, final String whereSegment) {
		// 处理括号括起来的表达式
		List<Table> mainTables = new ArrayList<>();
		// 无 join 时的处理逻辑
		if (fromItem instanceof Table) {
			Table fromTable = (Table) fromItem;
			mainTables.add(fromTable);
		} else if (fromItem instanceof ParenthesedFromItem) {
			// SubJoin 类型则还需要添加上 where 条件
			List<Table> tables = processSubJoin((ParenthesedFromItem) fromItem, whereSegment);
			mainTables.addAll(tables);
		} else {
			// 处理下 fromItem
			processOtherFromItem(fromItem, whereSegment);
		}
		return mainTables;
	}

	/**
	 * 处理 sub join
	 *
	 * @param subJoin subJoin
	 * @return Table subJoin 中的主表
	 */
	private List<Table> processSubJoin(ParenthesedFromItem subJoin, final String whereSegment) {
		List<Table> mainTables = new ArrayList<>();
		while (subJoin.getJoins() == null && subJoin.getFromItem() instanceof ParenthesedFromItem) {
			subJoin = (ParenthesedFromItem) subJoin.getFromItem();
		}
		if (subJoin.getJoins() != null) {
			List<Table> list = processFromItem(subJoin.getFromItem(), whereSegment);
			mainTables.addAll(list);
			processJoins(mainTables, subJoin.getJoins(), whereSegment);
		}
		return mainTables;
	}


	/**
	 * 处理 joins
	 *
	 * @param mainTables 可以为 null
	 * @param joins      join 集合
	 * @return List<Table> 右连接查询的 Table 列表
	 */
	private List<Table> processJoins(List<Table> mainTables, List<Join> joins, final String whereSegment) {
		// join 表达式中最终的主表
		Table mainTable = null;
		// 当前 join 的左表
		Table leftTable = null;

		if (mainTables.size() == 1) {
			mainTable = mainTables.get(0);
			leftTable = mainTable;
		}

		//对于 on 表达式写在最后的 join，需要记录下前面多个 on 的表名
		Deque<List<Table>> onTableDeque = new LinkedList<>();
		for (Join join : joins) {
			// 处理 on 表达式
			FromItem joinItem = join.getRightItem();

			// 获取当前 join 的表，subJoint 可以看作是一张表
			List<Table> joinTables = null;
			if (joinItem instanceof Table) {
				joinTables = new ArrayList<>();
				joinTables.add((Table) joinItem);
			} else if (joinItem instanceof ParenthesedFromItem) {
				joinTables = processSubJoin((ParenthesedFromItem) joinItem, whereSegment);
			}

			if (joinTables != null) {

				// 如果是隐式内连接
				if (join.isSimple()) {
					mainTables.addAll(joinTables);
					continue;
				}

				// 当前表是否忽略
				Table joinTable = joinTables.get(0);

				List<Table> onTables = null;
				// 如果不要忽略，且是右连接，则记录下当前表
				if (join.isRight()) {
					mainTable = joinTable;
					mainTables.clear();
					if (leftTable != null) {
						onTables = Collections.singletonList(leftTable);
					}
				} else if (join.isInner()) {
					if (mainTable == null) {
						onTables = Collections.singletonList(joinTable);
					} else {
						onTables = Arrays.asList(mainTable, joinTable);
					}
					mainTable = null;
					mainTables.clear();
				} else {
					onTables = Collections.singletonList(joinTable);
				}

				if (mainTable != null && !mainTables.contains(mainTable)) {
					mainTables.add(mainTable);
				}

				// 获取 join 尾缀的 on 表达式列表
				Collection<Expression> originOnExpressions = join.getOnExpressions();
				// 正常 join on 表达式只有一个，立刻处理
				if (originOnExpressions.size() == 1 && onTables != null) {
					List<Expression> onExpressions = new LinkedList<>();
					onExpressions.add(builderExpression(originOnExpressions.iterator().next(), onTables, whereSegment));
					join.setOnExpressions(onExpressions);
					leftTable = mainTable == null ? joinTable : mainTable;
					continue;
				}
				// 表名压栈，忽略的表压入 null，以便后续不处理
				onTableDeque.push(onTables);
				// 尾缀多个 on 表达式的时候统一处理
				if (originOnExpressions.size() > 1) {
					Collection<Expression> onExpressions = new LinkedList<>();
					for (Expression originOnExpression : originOnExpressions) {
						List<Table> currentTableList = onTableDeque.poll();
						if (CollectionUtils.isEmpty(currentTableList)) {
							onExpressions.add(originOnExpression);
						} else {
							onExpressions.add(builderExpression(originOnExpression, currentTableList, whereSegment));
						}
					}
					join.setOnExpressions(onExpressions);
				}
				leftTable = joinTable;
			} else {
				processOtherFromItem(joinItem, whereSegment);
				leftTable = null;
			}
		}

		return mainTables;
	}


	/**
	 * 判断当前操作是否需要进行过滤
	 *
	 * @param tableName 表名
	 */
	public boolean doTenantFilter(String tableName) {
		return AuthUtil.isAdministrator() && !adminTenantTables.contains(tableName);
	}

	/**
	 * 判断当前操作是否需要进行过滤
	 *
	 * @param tables 表名
	 */
	public boolean doTenantFilters(List<Table> tables) {
		List<String> tableNames = tables.stream().map(Table::getName).collect(Collectors.toList());
		return AuthUtil.isAdministrator() && !CollectionUtil.containsAny(adminTenantTables, tableNames);
	}

}
