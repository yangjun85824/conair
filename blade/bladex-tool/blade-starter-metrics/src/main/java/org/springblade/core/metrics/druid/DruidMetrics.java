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

package org.springblade.core.metrics.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.JdbcConnectionStat;
import com.alibaba.druid.stat.JdbcDataSourceStat;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;

/**
 * druid Metrics
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class DruidMetrics implements MeterBinder {
	/**
	 * Prefix used for all Druid metric names.
	 */
	public static final String DRUID_METRIC_NAME_PREFIX = "druid";

	private static final String METRIC_CATEGORY = "name";
	private static final String METRIC_NAME_CONNECT_MAX_TIME = DRUID_METRIC_NAME_PREFIX + ".connections.connect.max.time";
	private static final String METRIC_NAME_ALIVE_MAX_TIME = DRUID_METRIC_NAME_PREFIX + ".connections.alive.max.time";
	private static final String METRIC_NAME_ALIVE_MIN_TIME = DRUID_METRIC_NAME_PREFIX + ".connections.alive.min.time";

	private static final String METRIC_NAME_CONNECT_COUNT = DRUID_METRIC_NAME_PREFIX + ".connections.connect.count";
	private static final String METRIC_NAME_ACTIVE_COUNT = DRUID_METRIC_NAME_PREFIX + ".connections.active.count";
	private static final String METRIC_NAME_CLOSE_COUNT = DRUID_METRIC_NAME_PREFIX + ".connections.close.count";
	private static final String METRIC_NAME_ERROR_COUNT = DRUID_METRIC_NAME_PREFIX + ".connections.error.count";
	private static final String METRIC_NAME_CONNECT_ERROR_COUNT = DRUID_METRIC_NAME_PREFIX + ".connections.connect.error.count";
	private static final String METRIC_NAME_COMMIT_COUNT = DRUID_METRIC_NAME_PREFIX + ".connections.commit.count";
	private static final String METRIC_NAME_ROLLBACK_COUNT = DRUID_METRIC_NAME_PREFIX + ".connections.rollback.count";

	private final Map<String, DruidDataSource> druidDataSourceMap;
	private final Iterable<Tag> tags;

	public DruidMetrics(Map<String, DruidDataSource> druidDataSourceMap) {
		this(druidDataSourceMap, Collections.emptyList());
	}

	@Override
	public void bindTo(MeterRegistry meterRegistry) {
		druidDataSourceMap.forEach((name, dataSource) -> {
			JdbcDataSourceStat dsStats = dataSource.getDataSourceStat();
			JdbcConnectionStat connectionStat = dsStats.getConnectionStat();
			// time
			Gauge.builder(METRIC_NAME_CONNECT_MAX_TIME, connectionStat, JdbcConnectionStat::getConnectMillisMax)
				.description("Connection connect max time")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.baseUnit(BaseUnits.MILLISECONDS)
				.register(meterRegistry);
			Gauge.builder(METRIC_NAME_ALIVE_MAX_TIME, connectionStat, JdbcConnectionStat::getAliveMillisMax)
				.description("Connection alive max time")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.baseUnit(BaseUnits.MILLISECONDS)
				.register(meterRegistry);
			Gauge.builder(METRIC_NAME_ALIVE_MIN_TIME, connectionStat, JdbcConnectionStat::getAliveMillisMin)
				.description("Connection alive min time")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.baseUnit(BaseUnits.MILLISECONDS)
				.register(meterRegistry);
			// count
			Gauge.builder(METRIC_NAME_ACTIVE_COUNT, connectionStat, JdbcConnectionStat::getActiveCount)
				.description("Connection active count")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.register(meterRegistry);
			Gauge.builder(METRIC_NAME_CONNECT_COUNT, connectionStat, JdbcConnectionStat::getConnectCount)
				.description("Connection connect count")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.register(meterRegistry);
			Gauge.builder(METRIC_NAME_CLOSE_COUNT, connectionStat, JdbcConnectionStat::getCloseCount)
				.description("Connection close count")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.register(meterRegistry);
			Gauge.builder(METRIC_NAME_ERROR_COUNT, connectionStat, JdbcConnectionStat::getErrorCount)
				.description("Connection error count")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.register(meterRegistry);
			Gauge.builder(METRIC_NAME_CONNECT_ERROR_COUNT, connectionStat, JdbcConnectionStat::getConnectErrorCount)
				.description("Connection connect error count")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.register(meterRegistry);
			Gauge.builder(METRIC_NAME_COMMIT_COUNT, connectionStat, JdbcConnectionStat::getCommitCount)
				.description("Connecting commit count")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.register(meterRegistry);
			Gauge.builder(METRIC_NAME_ROLLBACK_COUNT, connectionStat, JdbcConnectionStat::getRollbackCount)
				.description("Connection rollback count")
				.tags(tags)
				.tag(METRIC_CATEGORY, name)
				.register(meterRegistry);
		});
	}
}
