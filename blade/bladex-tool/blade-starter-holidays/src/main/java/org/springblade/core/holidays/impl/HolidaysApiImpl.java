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
package org.springblade.core.holidays.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.holidays.config.HolidaysApiProperties;
import org.springblade.core.holidays.core.DaysType;
import org.springblade.core.holidays.core.HolidaysApi;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节假日实现
 *
 * @author L.cm
 */
@Slf4j
@RequiredArgsConstructor
public class HolidaysApiImpl implements HolidaysApi, InitializingBean {
	/**
	 * 存储节假日
	 */
	private static final Map<Integer, Map<String, Byte>> YEAR_DATA_MAP = new HashMap<>();
	private final ResourceLoader resourceLoader;
	private final HolidaysApiProperties properties;

	@Override
	public DaysType getDaysType(LocalDate localDate) {
		int year = localDate.getYear();
		Map<String, Byte> dataMap = YEAR_DATA_MAP.get(year);
		// 对于没有数据的，我们按正常的周六日来判断，
		if (dataMap == null) {
			log.error("没有对应年:[{}]的数据，请升级或者自行维护数据！", year);
			return isWeekDay(localDate);
		}
		// 日期信息
		int monthValue = localDate.getMonthValue();
		int dayOfMonth = localDate.getDayOfMonth();
		// 月份和日期
		String monthAndDay = String.format("%02d%02d", monthValue, dayOfMonth);
		Byte result = dataMap.get(monthAndDay);
		if (result != null) {
			return DaysType.from(result);
		} else {
			return isWeekDay(localDate);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		int[] years = new int[]{2019, 2020, 2021, 2022, 2023, 2024};
        for (int year : years) {
            Resource resource = resourceLoader.getResource("classpath:data/" + year + "_data.json");
            try (InputStream inputStream = resource.getInputStream()) {
                Map<String, Byte> dataMap = JsonUtil.readMap(inputStream, Byte.class);
                YEAR_DATA_MAP.put(year, dataMap);
            }
        }
		List<HolidaysApiProperties.ExtData> extDataList = properties.getExtData();
		for (HolidaysApiProperties.ExtData extData : extDataList) {
			String dataPath = extData.getDataPath();
			Resource resource = resourceLoader.getResource(dataPath);
			try (InputStream inputStream = resource.getInputStream()) {
				Map<String, Byte> dataMap = JsonUtil.readMap(inputStream, Byte.class);
				YEAR_DATA_MAP.put(extData.getYear(), dataMap);
			}
		}
	}

	/**
	 * 判断是否工作日
	 *
	 * @param localDate LocalDate
	 * @return DaysType
	 */
	private static DaysType isWeekDay(LocalDate localDate) {
		int week = localDate.getDayOfWeek().getValue();
		return week == 6 || week == 7 ? DaysType.REST_DAYS : DaysType.WEEKDAYS;
	}

}
