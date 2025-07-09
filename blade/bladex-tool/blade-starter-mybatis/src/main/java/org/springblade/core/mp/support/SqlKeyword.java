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
package org.springblade.core.mp.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;

import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 定义常用的 sql关键字
 *
 * @author Chill
 */
public class SqlKeyword {
	/**
	 * 常规sql字符匹配关键词
	 */
	private final static String SQL_REGEX = "(?i)(?<![a-z])('|%|--|insert|delete|select|sleep|count|update|updatexml|group|union|drop|truncate|alter|grant|execute|exec|xp_cmdshell|call|declare|sql)(?![a-z])";
	/**
	 * 二次匹配防止双写等注入手段
	 */
	private final static Pattern PATTERN = Pattern.compile("(?:--|[\"';%]|\\binsert\\b|\\bdelete\\b|\\bselect\\b|\\bcount\\b|\\bupdate\\b|\\bupdatexml\\b|\\bsleep\\b|group\\s+by|\\bunion\\b|\\bdrop\\b|\\btruncate\\b|\\balter\\b|\\bgrant\\b|\\bexecute\\b|\\bxp_cmdshell\\b|\\bcall\\b|\\bdeclare\\b|\\bsql\\b)");
	/**
	 * sql注入警告语
	 */
	private final static String SQL_INJECTION_MESSAGE = "SQL keyword injection prevention processing!";
	/**
	 * sql关键字为空
	 */
	private final static String SQL_EMPTY_MESSAGE = "SQL keyword is empty!";
	/**
	 * 等于
	 */
	private static final String EQUAL = "_equal";
	/**
	 * 不等于
	 */
	private static final String NOT_EQUAL = "_notequal";
	/**
	 * 模糊
	 */
	private static final String LIKE = "_like";
	/**
	 * 左模糊
	 */
	private static final String LIKE_LEFT = "_likeleft";
	/**
	 * 右模糊
	 */
	private static final String LIKE_RIGHT = "_likeright";
	/**
	 * 不包含
	 */
	private static final String NOT_LIKE = "_notlike";
	/**
	 * 大于等于
	 */
	private static final String GE = "_ge";
	/**
	 * 小于等于
	 */
	private static final String LE = "_le";
	/**
	 * 大于
	 */
	private static final String GT = "_gt";
	/**
	 * 小于
	 */
	private static final String LT = "_lt";
	/**
	 * 日期大于等于
	 */
	private static final String DATE_GE = "_datege";
	/**
	 * 日期大于
	 */
	private static final String DATE_GT = "_dategt";
	/**
	 * 日期等于
	 */
	private static final String DATE_EQUAL = "_dateequal";
	/**
	 * 日期小于
	 */
	private static final String DATE_LT = "_datelt";
	/**
	 * 日期小于等于
	 */
	private static final String DATE_LE = "_datele";
	/**
	 * 为空
	 */
	private static final String IS_NULL = "_null";
	/**
	 * 不为空
	 */
	private static final String NOT_NULL = "_notnull";
	/**
	 * 忽略
	 */
	private static final String IGNORE = "_ignore";

	/**
	 * 条件构造器
	 *
	 * @param query 查询字段
	 * @param qw    查询包装类
	 */
	public static void buildCondition(Map<String, Object> query, QueryWrapper<?> qw) {
		if (Func.isEmpty(query)) {
			return;
		}
		query.forEach((k, v) -> {
			if (Func.hasEmpty(k, v) || k.endsWith(IGNORE)) {
				return;
			}
			// 过滤sql注入关键词
			k = filter(k);
			if (k.endsWith(EQUAL)) {
				qw.eq(getColumn(k, EQUAL), v);
			} else if (k.endsWith(NOT_EQUAL)) {
				qw.ne(getColumn(k, NOT_EQUAL), v);
			} else if (k.endsWith(LIKE_LEFT)) {
				qw.likeLeft(getColumn(k, LIKE_LEFT), v);
			} else if (k.endsWith(LIKE_RIGHT)) {
				qw.likeRight(getColumn(k, LIKE_RIGHT), v);
			} else if (k.endsWith(NOT_LIKE)) {
				qw.notLike(getColumn(k, NOT_LIKE), v);
			} else if (k.endsWith(GE)) {
				qw.ge(getColumn(k, GE), v);
			} else if (k.endsWith(LE)) {
				qw.le(getColumn(k, LE), v);
			} else if (k.endsWith(GT)) {
				qw.gt(getColumn(k, GT), v);
			} else if (k.endsWith(LT)) {
				qw.lt(getColumn(k, LT), v);
			} else if (k.endsWith(DATE_GE)) {
				qw.ge(getColumn(k, DATE_GE), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(DATE_GT)) {
				qw.gt(getColumn(k, DATE_GT), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(DATE_EQUAL)) {
				qw.eq(getColumn(k, DATE_EQUAL), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(DATE_LE)) {
				qw.le(getColumn(k, DATE_LE), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(DATE_LT)) {
				qw.lt(getColumn(k, DATE_LT), DateUtil.parse(String.valueOf(v), DateUtil.PATTERN_DATETIME));
			} else if (k.endsWith(IS_NULL)) {
				qw.isNull(getColumn(k, IS_NULL));
			} else if (k.endsWith(NOT_NULL)) {
				qw.isNotNull(getColumn(k, NOT_NULL));
			} else {
				qw.like(getColumn(k, LIKE), v);
			}
		});
	}

	/**
	 * 获取数据库字段
	 *
	 * @param column  字段名
	 * @param keyword 关键字
	 * @return string
	 */
	private static String getColumn(String column, String keyword) {
		return StringUtil.humpToUnderline(StringUtil.removeSuffix(column, keyword));
	}

	/**
	 * 把SQL关键字替换为空字符串
	 *
	 * @param param 关键字
	 * @return string
	 */
	@SneakyThrows(SQLException.class)
	public static String filter(String param) {
		// 清除特殊字符
		String cleaned = StringUtil.cleanIdentifier(param);
		if (cleaned == null) {
			throw new SQLException(SQL_EMPTY_MESSAGE);
		}
		// 将校验到的sql关键词替换为空字符串
		String sql = cleaned.replaceAll(SQL_REGEX, StringPool.EMPTY);
		// 二次校验，避免双写绕过等情况出现
		if (match(sql)) {
			throw new SQLException(SQL_INJECTION_MESSAGE);
		}
		return sql;
	}

	/**
	 * 判断字符是否包含SQL关键字
	 *
	 * @param param 关键字
	 * @return boolean
	 */
	public static Boolean match(String param) {
		return Func.isNotEmpty(param) && PATTERN.matcher(param).find();
	}

}
