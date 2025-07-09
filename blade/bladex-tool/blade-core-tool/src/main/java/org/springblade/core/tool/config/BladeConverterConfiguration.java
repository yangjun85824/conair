package org.springblade.core.tool.config;

import org.springblade.core.tool.convert.EnumToStringConverter;
import org.springblade.core.tool.convert.StringToEnumConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * blade enum 《-》 String 转换配置
 *
 * @author L.cm
 */
@AutoConfiguration
public class BladeConverterConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new EnumToStringConverter());
		registry.addConverter(new StringToEnumConverter());
	}

}
