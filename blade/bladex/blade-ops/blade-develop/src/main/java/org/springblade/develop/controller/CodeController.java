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
package org.springblade.develop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.develop.constant.DevelopConstant;
import org.springblade.develop.pojo.entity.Code;
import org.springblade.develop.pojo.entity.Datasource;
import org.springblade.develop.pojo.entity.Model;
import org.springblade.develop.pojo.entity.ModelPrototype;
import org.springblade.develop.service.ICodeService;
import org.springblade.develop.service.IDatasourceService;
import org.springblade.develop.service.IModelPrototypeService;
import org.springblade.develop.service.IModelService;
import org.springblade.develop.support.BladeCodeGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/code")
@Tag(name = "代码生成", description = "代码生成")
@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
public class CodeController extends BladeController {

	private final ICodeService codeService;
	private final IDatasourceService datasourceService;
	private final IModelService modelService;
	private final IModelPrototypeService modelPrototypeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入code")
	public R<Code> detail(Code code) {
		Code detail = codeService.getOne(Condition.getQueryWrapper(code));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "codeName", description = "模块名", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "tableName", description = "表名", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "modelName", description = "实体名", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入code")
	public R<IPage<Code>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> code, Query query) {
		IPage<Code> pages = codeService.page(Condition.getPage(query), Condition.getQueryWrapper(code, Code.class));
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增或修改", description = "传入code")
	public R submit(@Valid @RequestBody Code code) {
		return R.status(codeService.submit(code));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(codeService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 复制
	 */
	@PostMapping("/copy")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "复制", description = "传入id")
	public R copy(@Parameter(description = "主键", required = true) @RequestParam Long id) {
		Code code = codeService.getById(id);
		code.setId(null);
		code.setCodeName(code.getCodeName() + "-copy");
		return R.status(codeService.save(code));
	}

	/**
	 * 代码生成
	 */
	@PostMapping("/gen-code")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "代码生成", description = "传入ids")
	public R genCode(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		Collection<Code> codes = codeService.listByIds(Func.toLongList(ids));
		codes.forEach(code -> {
			BladeCodeGenerator generator = new BladeCodeGenerator();
			// 设置基础模型
			Model model = modelService.getById(code.getModelId());
			generator.setModelCode(model.getModelCode());
			generator.setModelClass(model.getModelClass());
			// 设置模型集合
			List<ModelPrototype> prototypes = modelPrototypeService.prototypeList(model.getId());
			generator.setModel(JsonUtil.readMap(JsonUtil.toJson(model)));
			generator.setPrototypes(JsonUtil.readListMap(JsonUtil.toJson(prototypes)));
			if (StringUtil.isNotBlank(code.getSubModelId()) && StringUtil.equals(code.getTemplateType(), DevelopConstant.TEMPLATE_SUB)) {
				Model subModel = modelService.getById(Func.toLong(code.getSubModelId()));
				List<ModelPrototype> subPrototypes = modelPrototypeService.prototypeList(subModel.getId());
				generator.setSubModel(JsonUtil.readMap(JsonUtil.toJson(subModel)));
				generator.setSubPrototypes(JsonUtil.readListMap(JsonUtil.toJson(subPrototypes)));
			}
			// 设置数据源
			Datasource datasource = datasourceService.getById(model.getDatasourceId());
			generator.setDriverName(datasource.getDriverClass());
			generator.setUrl(datasource.getUrl());
			generator.setUsername(datasource.getUsername());
			generator.setPassword(datasource.getPassword());
			// 设置基础配置
			generator.setCodeStyle(code.getCodeStyle());
			generator.setCodeName(code.getCodeName());
			generator.setServiceName(code.getServiceName());
			generator.setPackageName(code.getPackageName());
			generator.setPackageDir(code.getApiPath());
			generator.setPackageWebDir(code.getWebPath());
			generator.setTablePrefix(Func.toStrArray(code.getTablePrefix()));
			generator.setIncludeTables(Func.toStrArray(code.getTableName()));
			// 设置模版信息
			generator.setTemplateType(code.getTemplateType());
			generator.setAuthor(code.getAuthor());
			generator.setSubModelId(code.getSubModelId());
			generator.setSubFkId(code.getSubFkId());
			generator.setTreeId(code.getTreeId());
			generator.setTreePid(code.getTreePid());
			generator.setTreeName(code.getTreeName());
			// 设置是否继承基础业务字段
			generator.setHasSuperEntity(code.getBaseMode() == 2);
			// 设置是否开启包装器模式
			generator.setHasWrapper(code.getWrapMode() == 2);
			// 设置是否开启远程调用模式
			generator.setHasFeign(code.getFeignMode() == 2);
			// 设置控制器服务名前缀
			generator.setHasServiceName(Boolean.TRUE);
			// 启动代码生成
			generator.run();
		});
		return R.success("代码生成成功");
	}

}
