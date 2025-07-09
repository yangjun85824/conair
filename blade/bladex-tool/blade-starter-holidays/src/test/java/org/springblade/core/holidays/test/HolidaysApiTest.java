package org.springblade.core.holidays.test;

import org.springblade.core.holidays.config.HolidaysApiConfiguration;
import org.springblade.core.holidays.config.HolidaysApiProperties;
import org.springblade.core.holidays.core.DaysType;
import org.springblade.core.holidays.core.HolidaysApi;
import org.springblade.core.holidays.impl.HolidaysApiImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

class HolidaysApiTest {

	private HolidaysApi holidaysApi;

	@BeforeEach
	public void setup() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		HolidaysApiConfiguration configuration = new HolidaysApiConfiguration();
		holidaysApi = configuration.holidaysApi(context, new HolidaysApiProperties());
		((HolidaysApiImpl) holidaysApi).afterPropertiesSet();
	}

	@Test
	void test() {
		DaysType daysType = holidaysApi.getDaysType(LocalDate.of(2023, 1, 1));
		Assertions.assertEquals(DaysType.HOLIDAYS, daysType);
		Assertions.assertFalse(holidaysApi.isWeekdays(LocalDate.of(2023, 9, 29)));
		Assertions.assertTrue(holidaysApi.isWeekdays(LocalDate.of(2023, 10, 7)));
	}

}
