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

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.utils.StringUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 代码模版生成实现类
 *
 * @author Chill
 */
@AllArgsConstructor
public class BladeTemplateEngine extends BeetlTemplateEngine {

	private String outputDir;
	private String outputWebDir;

	@Override
	protected void outputCustomFile(List<CustomFile> customFiles, TableInfo tableInfo, Map<String, Object> objectMap) {
		String packageName = String.valueOf(objectMap.get("packageName"));
		String serviceCode = String.valueOf(objectMap.get("serviceCode"));
		String modelCode = String.valueOf(objectMap.get("modelCode"));
		String entityName = String.valueOf(objectMap.get("modelClass"));
		String entityNameLower = entityName.toLowerCase();

		customFiles.forEach(customFile -> {
			String key = customFile.getFileName();
			String value = customFile.getTemplatePath();
			String outputPath = getPathInfo(OutputFile.parent);
			objectMap.put("entityKey", entityNameLower);
			if (StringUtil.equals(key, "menu.sql")) {
				objectMap.put("menuId", IdWorker.getId());
				objectMap.put("addMenuId", IdWorker.getId());
				objectMap.put("editMenuId", IdWorker.getId());
				objectMap.put("removeMenuId", IdWorker.getId());
				objectMap.put("viewMenuId", IdWorker.getId());
				outputPath = outputDir + StringPool.SLASH + "sql" + StringPool.SLASH + entityNameLower + ".menu.sql";
			}
			if (StringUtil.equals(key, "entityVO.java")) {
				outputPath = outputDir + StringPool.SLASH + packageName.replace(StringPool.DOT, StringPool.SLASH) + StringPool.SLASH + "/pojo/vo" + StringPool.SLASH + entityName + "VO" + StringPool.DOT_JAVA;
			}

			if (StringUtil.equals(key, "entityDTO.java")) {
				outputPath = outputDir + StringPool.SLASH + packageName.replace(StringPool.DOT, StringPool.SLASH) + StringPool.SLASH + "/pojo/dto" + StringPool.SLASH + entityName + "DTO" + StringPool.DOT_JAVA;
			}

			if (StringUtil.equals(key, "entityExcel.java")) {
				outputPath = outputDir + StringPool.SLASH + packageName.replace(StringPool.DOT, StringPool.SLASH) + StringPool.SLASH + "/excel" + StringPool.SLASH + entityName + "Excel" + StringPool.DOT_JAVA;
			}

			if (StringUtil.equals(key, "wrapper.java")) {
				outputPath = outputDir + StringPool.SLASH + packageName.replace(StringPool.DOT, StringPool.SLASH) + StringPool.SLASH + "wrapper" + StringPool.SLASH + entityName + "Wrapper" + StringPool.DOT_JAVA;
			}

			if (StringUtil.equals(key, "feign.java")) {
				outputPath = outputDir + StringPool.SLASH + packageName.replace(StringPool.DOT, StringPool.SLASH) + StringPool.SLASH + "feign" + StringPool.SLASH + "I" + entityName + "Client" + StringPool.DOT_JAVA;
			}

			if (StringUtil.equals(key, "feignclient.java")) {
				outputPath = outputDir + StringPool.SLASH + packageName.replace(StringPool.DOT, StringPool.SLASH) + StringPool.SLASH + "feign" + StringPool.SLASH + entityName + "Client" + StringPool.DOT_JAVA;
			}

			if (StringUtil.equals(key, "action.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "actions" + StringPool.SLASH + entityNameLower + ".js";
			}

			if (StringUtil.equals(key, "model.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "models" + StringPool.SLASH + entityNameLower + ".js";
			}

			if (StringUtil.equals(key, "service.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "services" + StringPool.SLASH + entityNameLower + ".js";
			}

			if (StringUtil.equals(key, "list.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "pages" + StringPool.SLASH + StringUtil.firstCharToUpper(modelCode) + StringPool.SLASH + entityName + StringPool.SLASH + entityName + ".js";
			}

			if (StringUtil.equals(key, "add.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "pages" + StringPool.SLASH + StringUtil.firstCharToUpper(modelCode) + StringPool.SLASH + entityName + StringPool.SLASH + entityName + "Add.js";
			}

			if (StringUtil.equals(key, "edit.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "pages" + StringPool.SLASH + StringUtil.firstCharToUpper(modelCode) + StringPool.SLASH + entityName + StringPool.SLASH + entityName + "Edit.js";
			}

			if (StringUtil.equals(key, "view.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "pages" + StringPool.SLASH + StringUtil.firstCharToUpper(modelCode) + StringPool.SLASH + entityName + StringPool.SLASH + entityName + "View.js";
			}

			if (StringUtil.equals(key, "api.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "api" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + ".js";
			}

			if (StringUtil.equals(key, "option.js")) {
				outputPath = outputWebDir + StringPool.SLASH + "option" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + ".js";
			}

			if (StringUtil.equals(key, "crud.vue")) {
				outputPath = outputWebDir + StringPool.SLASH + "views" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + ".vue";
			}

			if (StringUtil.equals(key, "sub.vue")) {
				outputPath = outputWebDir + StringPool.SLASH + "views" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + "Sub.vue";
			}

			if (StringUtil.equals(key, "data.ts")) {
				outputPath = outputWebDir + StringPool.SLASH + "api" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + ".ts";
			}

			if (StringUtil.equals(key, "data.data.ts")) {

				outputPath = outputWebDir + StringPool.SLASH + "views" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + StringPool.SLASH + modelCode + ".data.ts";
			}

			if (StringUtil.equals(key, "index.vue")) {
				outputPath = outputWebDir + StringPool.SLASH + "views" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + StringPool.SLASH + "index.vue";
			}

			if (StringUtil.equals(key, "Modal.vue")) {
				outputPath = outputWebDir + StringPool.SLASH + "views" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + StringPool.SLASH + entityName + "Modal.vue";
			}

			if (StringUtil.equals(key, "lemonSub.vue")) {
				outputPath = outputWebDir + StringPool.SLASH + "views" + StringPool.SLASH + serviceCode + StringPool.SLASH + modelCode + StringPool.SLASH + entityName + "Sub.vue";
			}
			outputFile(new File(String.valueOf(outputPath)), objectMap, value, Boolean.TRUE);
		});
	}


}
