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
package org.springblade.flow.engine.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springblade.flow.core.pojo.entity.BladeFlow;
import org.springblade.flow.engine.entity.FlowExecution;
import org.springblade.flow.engine.entity.FlowModel;
import org.springblade.flow.engine.entity.FlowProcess;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * FlowEngineService
 *
 * @author Chill
 */
public interface FlowEngineService extends IService<FlowModel> {

	/**
	 * 自定义分页
	 *
	 * @param page      分页工具
	 * @param flowModel 流程模型
	 * @return
	 */
	IPage<FlowModel> selectFlowPage(IPage<FlowModel> page, FlowModel flowModel);

	/**
	 * 流程管理列表
	 *
	 * @param page     分页工具
	 * @param category 分类
	 * @param mode     形态
	 * @return
	 */
	IPage<FlowProcess> selectProcessPage(IPage<FlowProcess> page, String category, Integer mode);

	/**
	 * 流程管理列表
	 *
	 * @param page                 分页工具
	 * @param processInstanceId    流程实例id
	 * @param processDefinitionKey 流程key
	 * @return
	 */
	IPage<FlowExecution> selectFollowPage(IPage<FlowExecution> page, String processInstanceId, String processDefinitionKey);

	/**
	 * 获取流转历史列表
	 *
	 * @param processInstanceId 流程实例id
	 * @param startActivityId   开始节点id
	 * @param endActivityId     结束节点id
	 * @return
	 */
	List<BladeFlow> historyFlowList(String processInstanceId, String startActivityId, String endActivityId);

	/**
	 * 变更流程状态
	 *
	 * @param state     状态
	 * @param processId 流程ID
	 * @return
	 */
	String changeState(String state, String processId);

	/**
	 * 删除部署流程
	 *
	 * @param deploymentIds 部署流程id集合
	 * @return
	 */
	boolean deleteDeployment(String deploymentIds);

	/**
	 * 上传部署流程
	 *
	 * @param files        流程配置文件
	 * @param category     流程分类
	 * @param tenantIdList 租户id集合
	 * @return
	 */
	boolean deployUpload(List<MultipartFile> files, String category, List<String> tenantIdList);

	/**
	 * 部署流程
	 *
	 * @param modelId      模型id
	 * @param category     分类
	 * @param tenantIdList 租户id集合
	 * @return
	 */
	boolean deployModel(String modelId, String category, List<String> tenantIdList);

	/**
	 * 删除流程实例
	 *
	 * @param processInstanceId 流程实例id
	 * @param deleteReason      删除原因
	 * @return
	 */
	boolean deleteProcessInstance(String processInstanceId, String deleteReason);

	/**
	 * 保存/更新模型
	 *
	 * @param model 模型
	 * @return 模型
	 */
	FlowModel submitModel(FlowModel model);

	/**
	 * 流程节点进程图
	 *
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @return
	 */
	Map<String, Object> modelView(String processDefinitionId, String processInstanceId);

	/**
	 * 流程节点进程图
	 *
	 * @param processInstanceId
	 * @param httpServletResponse
	 */
	void diagramView(String processInstanceId, HttpServletResponse httpServletResponse);

	/**
	 * 流程图展示
	 *
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @param resourceType
	 * @param response
	 */
	void resourceView(String processDefinitionId, String processInstanceId, String resourceType, HttpServletResponse response);

	/**
	 * 获取XML
	 *
	 * @param model
	 * @return
	 */
	byte[] getModelEditorXML(FlowModel model);
}
