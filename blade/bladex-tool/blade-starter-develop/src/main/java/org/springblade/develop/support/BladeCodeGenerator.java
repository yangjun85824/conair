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
package org.springblade.develop.support;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.springblade.develop.constant.DevelopConstant.*;

/**
 * 代码生成器配置类
 *
 * @author Chill
 */
@Data
@Slf4j
public class BladeCodeGenerator {
	/**
	 * 代码风格
	 */
	private String codeStyle = SABER_NAME;
	/**
	 * 代码模块名称
	 */
	private String codeName;
	/**
	 * 模型编号
	 */
	private String modelCode;
	/**
	 * 模型实体类
	 */
	private String modelClass;
	/**
	 * 代码所在服务名
	 */
	private String serviceName = "blade-service";
	/**
	 * 代码生成的包名
	 */
	private String packageName = "org.springblade.test";
	/**
	 * 模版类型
	 */
	private String templateType;
	/**
	 * 作者信息
	 */
	private String author;
	/**
	 * 子表模型主键
	 */
	private String subModelId;
	/**
	 * 子表绑定外键
	 */
	private String subFkId;
	/**
	 * 树主键字段
	 */
	private String treeId;
	/**
	 * 树父主键字段
	 */
	private String treePid;
	/**
	 * 树名称字段
	 */
	private String treeName;
	/**
	 * 代码后端生成的地址
	 */
	private String packageDir;
	/**
	 * 代码前端生成的地址
	 */
	private String packageWebDir;
	/**
	 * 需要去掉的表前缀
	 */
	private String[] tablePrefix = {"blade_"};
	/**
	 * 需要生成的表名(两者只能取其一)
	 */
	private String[] includeTables = {"blade_test"};
	/**
	 * 需要排除的表名(两者只能取其一)
	 */
	private String[] excludeTables = {};
	/**
	 * 是否包含基础业务字段
	 */
	private Boolean hasSuperEntity = Boolean.TRUE;
	/**
	 * 是否包含包装器
	 */
	private Boolean hasWrapper = Boolean.TRUE;
	/**
	 * 是否包含远程调用
	 */
	private Boolean hasFeign = Boolean.FALSE;
	/**
	 * 是否包含服务名
	 */
	private Boolean hasServiceName = Boolean.FALSE;
	/**
	 * 基础业务字段
	 */
	private String[] superEntityColumns = {"create_time", "create_user", "create_dept", "update_time", "update_user", "status", "is_deleted"};
	/**
	 * 租户字段
	 */
	private String tenantColumn = "tenant_id";
	/**
	 * 数据库驱动名
	 */
	private String driverName;
	/**
	 * 数据库链接地址
	 */
	private String url;
	/**
	 * 数据库用户名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;
	/**
	 * 数据模型
	 */
	private Map<String, Object> model;
	/**
	 * 数据原型
	 */
	private List<Map<String, Object>> prototypes;
	/**
	 * 子数据模型
	 */
	private Map<String, Object> subModel;
	/**
	 * 子数据原型
	 */
	private List<Map<String, Object>> subPrototypes;

	/**
	 * 代码生成执行
	 */
	public void run() {
		// 主模块代码生成
		getAutoGenerator(getCustomMap(TEMPLATE_MAIN), getCustomFile(TEMPLATE_MAIN)).templateEngine(new BladeTemplateEngine(getOutputDir(), getOutputWebDir())).execute();
		// 子模块代码生成
		if (Func.equals(templateType, TEMPLATE_SUB) && StringUtil.isNotBlank(subModelId)) {
			getAutoGenerator(getCustomMap(TEMPLATE_SUB), getCustomFile(TEMPLATE_SUB)).templateEngine(new BladeTemplateEngine(getOutputDir(), getOutputWebDir())).execute();
		}
	}

	/**
	 * 设置 customMap
	 */
	private Map<String, Object> getCustomMap(String generateType) {
		List<Map<String, Object>> prototypeList;
		String[] split = packageName.split("\\.");
		String serviceCode = split[split.length - 1];
		Map<String, Object> customMap = new HashMap<>(11);
		customMap.put("generateType", generateType);
		customMap.put("codeName", codeName);
		customMap.put("serviceName", serviceName);
		customMap.put("serviceCode", serviceCode);
		customMap.put("packageName", packageName);
		customMap.put("tenantColumn", tenantColumn);
		customMap.put("hasWrapper", hasWrapper);
		customMap.put("hasServiceName", hasServiceName);
		customMap.put("hasSuperEntity", hasSuperEntity);
		customMap.put("templateType", templateType);
		customMap.put("author", author);
		customMap.put("subModelId", subModelId);
		customMap.put("subFkId", subFkId);
		customMap.put("treeId", treeId);
		customMap.put("treePid", treePid);
		customMap.put("treeName", treeName);
		customMap.put("subFkIdHump", StringUtil.underlineToHump(subFkId));
		customMap.put("treeIdHump", StringUtil.underlineToHump(treeId));
		customMap.put("treePidHump", StringUtil.underlineToHump(treePid));
		if (Func.equals(generateType, TEMPLATE_SUB)) {
			prototypeList = subPrototypes;
			customMap.put("model", subModel);
			customMap.put("prototypes", subPrototypes);
			customMap.put("modelCode", subModel.get("modelCode"));
			customMap.put("modelClass", subModel.get("modelClass"));
			customMap.put("modelTable", subModel.get("modelTable"));
		} else {
			prototypeList = prototypes;
			customMap.put("model", model);
			customMap.put("prototypes", prototypes);
			customMap.put("subModel", subModel);
			customMap.put("subPrototypes", subPrototypes);
			customMap.put("modelCode", model.get("modelCode"));
			customMap.put("modelClass", model.get("modelClass"));
			customMap.put("modelTable", model.get("modelTable"));
		}
		List<String> propertyImport = prototypeList.stream().filter(prototype -> {
			String propertyType = String.valueOf(prototype.get("propertyType"));
			return !"String".equals(propertyType) && !"Integer".equals(propertyType) && !"Long".equals(propertyType);
		}).map(prototype -> String.valueOf(prototype.get("propertyEntity"))).distinct().collect(Collectors.toList());
		customMap.put("propertyImport", propertyImport);
		return customMap;
	}

	/**
	 * 设置 customFile
	 */
	private Map<String, String> getCustomFile(String type) {
		Map<String, String> customFile = new HashMap<>(15);
		if (!Func.equals(type, TEMPLATE_SUB)) {
			customFile.put("menu.sql", "/templates/sql/menu.sql.btl");
		}
		customFile.put("entityVO.java", "/templates/api/entityVO.java.btl");
		customFile.put("entityDTO.java", "/templates/api/entityDTO.java.btl");
		customFile.put("entityExcel.java", "/templates/api/entityExcel.java.btl");
		if (hasWrapper) {
			customFile.put("wrapper.java", "/templates/api/wrapper.java.btl");
		}
		if (hasFeign) {
			customFile.put("feign.java", "/templates/api/feign.java.btl");
			customFile.put("feignclient.java", "/templates/api/feignclient.java.btl");
		}
		if (Func.isNotBlank(packageWebDir)) {
			if (Func.equals(codeStyle, SWORD_NAME)) {
				customFile.put("action.js", "/templates/sword/action.js.btl");
				customFile.put("model.js", "/templates/sword/model.js.btl");
				customFile.put("service.js", "/templates/sword/service.js.btl");
				customFile.put("list.js", "/templates/sword/list.js.btl");
				customFile.put("add.js", "/templates/sword/add.js.btl");
				customFile.put("edit.js", "/templates/sword/edit.js.btl");
				customFile.put("view.js", "/templates/sword/view.js.btl");
			} else if (Func.equals(codeStyle, SABER_NAME)) {
				customFile.put("api.js", "/templates/saber/" + templateType + "/api.js.btl");
				customFile.put("option.js", "/templates/saber/" + templateType + "/option.js.btl");
				if (!Func.equals(type, TEMPLATE_SUB)) {
					customFile.put("crud.vue", "/templates/saber/" + templateType + "/crud.vue.btl");
				}
			} else if (Func.equals(codeStyle, SABER3_NAME)) {
				customFile.put("api.js", "/templates/saber3/" + templateType + "/api.js.btl");
				customFile.put("option.js", "/templates/saber3/" + templateType + "/option.js.btl");
				if (!Func.equals(type, TEMPLATE_SUB)) {
					customFile.put("crud.vue", "/templates/saber3/" + templateType + "/crud.vue.btl");
				}
			} else if (Func.equals(codeStyle, ELEMENT_NAME)) {
				customFile.put("api.js", "/templates/element/" + templateType + "/api.js.btl");
				customFile.put("option.js", "/templates/element/" + templateType + "/option.js.btl");
				if (!Func.equals(type, TEMPLATE_SUB)) {
					customFile.put("crud.vue", "/templates/element/" + templateType + "/crud.vue.btl");
				} else {
					customFile.put("sub.vue", "/templates/element/" + templateType + "/sub.vue.btl");
				}
			} else if (Func.equals(codeStyle, ELEMENT_PLUS_NAME)) {
				customFile.put("api.js", "/templates/element-plus/" + templateType + "/api.js.btl");
				customFile.put("option.js", "/templates/element-plus/" + templateType + "/option.js.btl");
				if (!Func.equals(type, TEMPLATE_SUB)) {
					customFile.put("crud.vue", "/templates/element-plus/" + templateType + "/crud.vue.btl");
				} else {
					customFile.put("sub.vue", "/templates/element-plus/" + templateType + "/sub.vue.btl");
				}
			} else if (Func.equals(codeStyle, LEMON_NAME)) {
				customFile.put("data.ts", "/templates/lemon/" + templateType + "/data.ts.btl");
				customFile.put("Modal.vue", "/templates/lemon/" + templateType + "/Modal.vue.btl");
				customFile.put("data.data.ts", "/templates/lemon/" + templateType + "/data.data.ts.btl");
				if (!Func.equals(type, TEMPLATE_SUB)) {
					customFile.put("index.vue", "/templates/lemon/" + templateType + "/index.vue.btl");
				} else {
					customFile.put("lemonSub.vue", "/templates/lemon/" + templateType + "/sub.vue.btl");
				}

			}
		}
		return customFile;
	}

	private FastAutoGenerator getAutoGenerator(Map<String, Object> customMap, Map<String, String> customFile) {
		Properties props = getProperties();
		String url = Func.toStr(this.url, props.getProperty("spring.datasource.url"));
		String username = Func.toStr(this.username, props.getProperty("spring.datasource.username"));
		String password = Func.toStr(this.password, props.getProperty("spring.datasource.password"));
		return FastAutoGenerator.create(url, username, password)
			.globalConfig(builder -> builder.author(StringUtil.isBlank(author) ? props.getProperty("author") : author).dateType(DateType.TIME_PACK).enableSwagger().outputDir(getOutputDir()).disableOpenDir())
			.packageConfig(builder -> builder.parent(packageName).controller("controller").entity("pojo.entity").service("service").serviceImpl("service.impl").mapper("mapper").xml("mapper"))
			.strategyConfig(builder -> builder.addTablePrefix(tablePrefix).addInclude(Func.toStrArray(String.valueOf(customMap.get("modelTable")))).addExclude(excludeTables)
				.entityBuilder().naming(NamingStrategy.underline_to_camel).columnNaming(NamingStrategy.underline_to_camel).enableLombok().superClass("org.springblade.core.mp.base.BaseEntity").formatFileName("%sEntity").addSuperEntityColumns(superEntityColumns).enableFileOverride()
				.javaTemplate("/templates/api/entity.java")
				.serviceBuilder().superServiceClass("org.springblade.core.mp.base.BaseService").superServiceImplClass("org.springblade.core.mp.base.BaseServiceImpl").formatServiceFileName("I%sService").formatServiceImplFileName("%sServiceImpl").enableFileOverride()
				.serviceTemplate("/templates/api/service.java")
				.serviceImplTemplate("/templates/api/serviceImpl.java")
				.mapperBuilder().mapperAnnotation(Mapper.class).enableBaseResultMap().enableBaseColumnList().formatMapperFileName("%sMapper").formatXmlFileName("%sMapper").enableFileOverride()
				.mapperTemplate("/templates/api/mapper.java")
				.mapperXmlTemplate("/templates/api/mapper.xml")
				.controllerBuilder().superClass("org.springblade.core.boot.ctrl.BladeController").formatFileName("%sController").enableRestStyle().enableHyphenStyle().enableFileOverride()
				.template("/templates/api/controller.java")
			)
			.injectionConfig(builder -> builder.beforeOutputFile(
					(tableInfo, objectMap) -> System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size())
				).customMap(customMap).customFile(customFile)
			);
	}

	/**
	 * 获取配置文件
	 *
	 * @return 配置Props
	 */
	private Properties getProperties() {
		// 读取配置文件
		Resource resource = new ClassPathResource("/templates/code.properties");
		Properties props = new Properties();
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * 生成到项目中
	 *
	 * @return outputDir
	 */
	public String getOutputDir() {
		return (Func.isBlank(packageDir) ? System.getProperty("user.dir") + "/blade-ops/blade-develop" : packageDir) + "/src/main/java";
	}


	/**
	 * 生成到Web项目中
	 *
	 * @return outputDir
	 */
	public String getOutputWebDir() {
		return (Func.isBlank(packageWebDir) ? System.getProperty("user.dir") : packageWebDir) + "/src";
	}

}
