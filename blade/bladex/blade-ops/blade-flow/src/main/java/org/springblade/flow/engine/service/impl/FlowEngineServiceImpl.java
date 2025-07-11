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
package org.springblade.flow.engine.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.common.engine.impl.util.io.StringStreamSource;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.BpmnJsonConverterContext;
import org.flowable.editor.language.json.converter.CustomBpmnJsonConverterContext;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.FileUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.flow.core.pojo.entity.BladeFlow;
import org.springblade.flow.core.pojo.enums.FlowModeEnum;
import org.springblade.flow.core.utils.TaskUtil;
import org.springblade.flow.engine.constant.FlowEngineConstant;
import org.springblade.flow.engine.entity.FlowExecution;
import org.springblade.flow.engine.entity.FlowModel;
import org.springblade.flow.engine.entity.FlowProcess;
import org.springblade.flow.engine.mapper.FlowMapper;
import org.springblade.flow.engine.service.FlowEngineService;
import org.springblade.flow.engine.utils.FlowCache;
import org.springblade.system.cache.UserCache;
import org.springblade.system.pojo.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 工作流服务实现类
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class FlowEngineServiceImpl extends ServiceImpl<FlowMapper, FlowModel> implements FlowEngineService {
	private static final String ALREADY_IN_STATE = "already in state";
	private static final String USR_TASK = "userTask";
	private static final String IMAGE_NAME = "image";
	private static final String XML_NAME = "xml";
	private static final Integer INT_1024 = 1024;
	private static final BpmnJsonConverter BPMN_JSON_CONVERTER = new BpmnJsonConverter();
	private static final BpmnXMLConverter BPMN_XML_CONVERTER = new BpmnXMLConverter();
	private final ObjectMapper objectMapper;
	private final RepositoryService repositoryService;
	private final RuntimeService runtimeService;
	private final HistoryService historyService;
	private final TaskService taskService;
	private final ProcessEngine processEngine;

	@Override
	public IPage<FlowModel> selectFlowPage(IPage<FlowModel> page, FlowModel flowModel) {
		return page.setRecords(baseMapper.selectFlowPage(page, flowModel));
	}

	@Override
	public IPage<FlowProcess> selectProcessPage(IPage<FlowProcess> page, String category, Integer mode) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion().orderByProcessDefinitionKey().asc();
		// 通用流程
		if (mode == FlowModeEnum.COMMON.getMode()) {
			processDefinitionQuery.processDefinitionWithoutTenantId();
		}
		// 定制流程
		else if (!AuthUtil.isAdministrator()) {
			processDefinitionQuery.processDefinitionTenantId(AuthUtil.getTenantId());
		}
		if (StringUtils.isNotEmpty(category)) {
			processDefinitionQuery.processDefinitionCategory(category);
		}
		List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(Func.toInt((page.getCurrent() - 1) * page.getSize()), Func.toInt(page.getSize()));
		List<FlowProcess> flowProcessList = new ArrayList<>();
		processDefinitionList.forEach(processDefinition -> {
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			FlowProcess flowProcess = new FlowProcess((ProcessDefinitionEntityImpl) processDefinition);
			flowProcess.setDeploymentTime(deployment.getDeploymentTime());
			flowProcessList.add(flowProcess);
		});
		page.setTotal(processDefinitionQuery.count());
		page.setRecords(flowProcessList);
		return page;
	}

	@Override
	public IPage<FlowExecution> selectFollowPage(IPage<FlowExecution> page, String processInstanceId, String processDefinitionKey) {
		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		if (StringUtil.isNotBlank(processInstanceId)) {
			processInstanceQuery.processInstanceId(processInstanceId);
		}
		if (StringUtil.isNotBlank(processDefinitionKey)) {
			processInstanceQuery.processDefinitionKey(processDefinitionKey);
		}
		List<FlowExecution> flowList = new ArrayList<>();
		List<ProcessInstance> procInsList = processInstanceQuery.listPage(Func.toInt((page.getCurrent() - 1) * page.getSize()), Func.toInt(page.getSize()));
		procInsList.forEach(processInstance -> {
			ExecutionEntityImpl execution = (ExecutionEntityImpl) processInstance;
			FlowExecution flowExecution = new FlowExecution();
			flowExecution.setId(execution.getId());
			flowExecution.setName(execution.getName());
			flowExecution.setStartUserId(execution.getStartUserId());
			User taskUser = UserCache.getUserByTaskUser(execution.getStartUserId());
			if (taskUser != null) {
				flowExecution.setStartUser(taskUser.getName());
			}
			flowExecution.setStartTime(execution.getStartTime());
			flowExecution.setExecutionId(execution.getId());
			flowExecution.setProcessInstanceId(execution.getProcessInstanceId());
			flowExecution.setProcessDefinitionId(execution.getProcessDefinitionId());
			flowExecution.setProcessDefinitionKey(execution.getProcessDefinitionKey());
			flowExecution.setSuspensionState(execution.getSuspensionState());
			FlowProcess processDefinition = FlowCache.getProcessDefinition(execution.getProcessDefinitionId());
			flowExecution.setCategory(processDefinition.getCategory());
			flowExecution.setCategoryName(FlowCache.getCategoryName(processDefinition.getCategory()));
			flowList.add(flowExecution);
		});
		page.setTotal(processInstanceQuery.count());
		page.setRecords(flowList);
		return page;
	}

	@Override
	public List<BladeFlow> historyFlowList(String processInstanceId, String startActivityId, String endActivityId) {
		List<BladeFlow> flowList = new LinkedList<>();
		List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
		boolean start = false;
		Map<String, Integer> activityMap = new HashMap<>(16);
		for (int i = 0; i < historicActivityInstanceList.size(); i++) {
			HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(i);
			// 过滤开始节点前的节点
			if (StringUtil.isNotBlank(startActivityId) && startActivityId.equals(historicActivityInstance.getActivityId())) {
				start = true;
			}
			if (StringUtil.isNotBlank(startActivityId) && !start) {
				continue;
			}
			// 显示开始节点和结束节点，并且执行人不为空的任务
			if (StringUtils.equals(USR_TASK, historicActivityInstance.getActivityType())
				|| FlowEngineConstant.START_EVENT.equals(historicActivityInstance.getActivityType())
				|| FlowEngineConstant.END_EVENT.equals(historicActivityInstance.getActivityType())) {
				// 给节点增加序号
				activityMap.computeIfAbsent(historicActivityInstance.getActivityId(), k -> activityMap.size());
				BladeFlow flow = new BladeFlow();
				flow.setHistoryActivityId(historicActivityInstance.getActivityId());
				flow.setHistoryActivityName(historicActivityInstance.getActivityName());
				flow.setCreateTime(historicActivityInstance.getStartTime());
				flow.setEndTime(historicActivityInstance.getEndTime());
				String durationTime = DateUtil.secondToTime(Func.toLong(historicActivityInstance.getDurationInMillis(), 0L) / 1000);
				flow.setHistoryActivityDurationTime(durationTime);
				// 获取流程发起人名称
				if (FlowEngineConstant.START_EVENT.equals(historicActivityInstance.getActivityType())) {
					List<HistoricProcessInstance> processInstanceList = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).orderByProcessInstanceStartTime().asc().list();
					if (!processInstanceList.isEmpty()) {
						if (StringUtil.isNotBlank(processInstanceList.get(0).getStartUserId())) {
							String taskUser = processInstanceList.get(0).getStartUserId();
							User user = UserCache.getUser(TaskUtil.getUserId(taskUser));
							if (user != null) {
								flow.setAssignee(historicActivityInstance.getAssignee());
								flow.setAssigneeName(user.getName());
							}
						}
					}
				}
				// 获取任务执行人名称
				if (StringUtil.isNotBlank(historicActivityInstance.getAssignee())) {
					User user = UserCache.getUser(TaskUtil.getUserId(historicActivityInstance.getAssignee()));
					if (user != null) {
						flow.setAssignee(historicActivityInstance.getAssignee());
						flow.setAssigneeName(user.getName());
					}
				}
				// 获取意见评论内容
				if (StringUtil.isNotBlank(historicActivityInstance.getTaskId())) {
					List<Comment> commentList = taskService.getTaskComments(historicActivityInstance.getTaskId());
					if (!commentList.isEmpty()) {
						flow.setComment(commentList.get(0).getFullMessage());
					}
				}
				flowList.add(flow);
			}
			// 过滤结束节点后的节点
			if (StringUtils.isNotBlank(endActivityId) && endActivityId.equals(historicActivityInstance.getActivityId())) {
				boolean temp = false;
				Integer activityNum = activityMap.get(historicActivityInstance.getActivityId());
				// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
				for (int j = i + 1; j < historicActivityInstanceList.size(); j++) {
					HistoricActivityInstance hi = historicActivityInstanceList.get(j);
					Integer activityNumA = activityMap.get(hi.getActivityId());
					boolean numberTemp = activityNumA != null && activityNumA < activityNum;
					boolean equalsTemp = StringUtils.equals(hi.getActivityId(), historicActivityInstance.getActivityId());
					if (numberTemp || equalsTemp) {
						temp = true;
					}
				}
				if (!temp) {
					break;
				}
			}
		}
		return flowList;
	}

	@Override
	public String changeState(String state, String processId) {
		try {
			if (state.equals(FlowEngineConstant.ACTIVE)) {
				repositoryService.activateProcessDefinitionById(processId, true, null);
				return StringUtil.format("激活ID为 [{}] 的流程成功", processId);
			} else if (state.equals(FlowEngineConstant.SUSPEND)) {
				repositoryService.suspendProcessDefinitionById(processId, true, null);
				return StringUtil.format("挂起ID为 [{}] 的流程成功", processId);
			} else {
				return "暂无流程变更";
			}
		} catch (Exception e) {
			if (e.getMessage().contains(ALREADY_IN_STATE)) {
				return StringUtil.format("ID为 [{}] 的流程已是此状态，无需操作", processId);
			}
			return e.getMessage();
		}
	}

	@Override
	public boolean deleteDeployment(String deploymentIds) {
		Func.toStrList(deploymentIds).forEach(deploymentId -> repositoryService.deleteDeployment(deploymentId, true));
		return true;
	}

	@Override
	public boolean deployUpload(List<MultipartFile> files, String category, List<String> tenantIdList) {
		files.forEach(file -> {
			try {
				String fileName = file.getOriginalFilename();
				InputStream fileInputStream = file.getInputStream();
				byte[] bytes = FileUtil.copyToByteArray(fileInputStream);
				if (Func.isNotEmpty(tenantIdList)) {
					tenantIdList.forEach(tenantId -> {
						Deployment deployment = repositoryService.createDeployment().addBytes(fileName, bytes).tenantId(tenantId).deploy();
						deploy(deployment, category);
					});
				} else {
					Deployment deployment = repositoryService.createDeployment().addBytes(fileName, bytes).deploy();
					deploy(deployment, category);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return true;
	}

	@Override
	public boolean deployModel(String modelId, String category, List<String> tenantIdList) {
		FlowModel model = this.getById(modelId);
		if (model == null) {
			throw new ServiceException("未找到模型 id: " + modelId);
		}
		byte[] bytes = getBpmnXML(model);
		String processName = model.getName();
		if (!StringUtil.endsWithIgnoreCase(processName, FlowEngineConstant.SUFFIX)) {
			processName += FlowEngineConstant.SUFFIX;
		}
		String finalProcessName = processName;
		if (Func.isNotEmpty(tenantIdList)) {
			tenantIdList.forEach(tenantId -> {
				Deployment deployment = repositoryService.createDeployment().addBytes(finalProcessName, bytes).name(model.getName()).key(model.getModelKey()).tenantId(tenantId).deploy();
				deploy(deployment, category);
			});
		} else {
			Deployment deployment = repositoryService.createDeployment().addBytes(finalProcessName, bytes).name(model.getName()).key(model.getModelKey()).deploy();
			deploy(deployment, category);
		}
		return true;
	}

	@Override
	public boolean deleteProcessInstance(String processInstanceId, String deleteReason) {
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
		return true;
	}

	private void deploy(Deployment deployment, String category) {
		log.debug("流程部署--------deploy:  " + deployment + "  分类---------->" + category);
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
		StringBuilder logBuilder = new StringBuilder(500);
		List<Object> logArgs = new ArrayList<>();
		// 设置流程分类
		for (ProcessDefinition processDefinition : list) {
			if (StringUtil.isNotBlank(category)) {
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
			}
			logBuilder.append("部署成功,流程ID={} \n");
			logArgs.add(processDefinition.getId());
		}
		if (list.isEmpty()) {
			throw new ServiceException("部署失败,未找到流程");
		} else {
			log.info(logBuilder.toString(), logArgs.toArray());
		}
	}

	@Override
	public FlowModel submitModel(FlowModel model) {
		FlowModel flowModel = new FlowModel();
		flowModel.setId(model.getId());
		flowModel.setVersion(Func.toInt(model.getVersion(), 0) + 1);
		flowModel.setName(model.getName());
		flowModel.setModelKey(model.getModelKey());
		flowModel.setModelType(FlowModel.MODEL_TYPE_BPMN);
		flowModel.setCreatedBy(TaskUtil.getTaskUser());
		flowModel.setDescription(model.getDescription());
		flowModel.setLastUpdated(Calendar.getInstance().getTime());
		flowModel.setLastUpdatedBy(TaskUtil.getTaskUser());
		flowModel.setTenantId(AuthUtil.getTenantId());
		flowModel.setModelEditorXml(model.getModelEditorXml());
		if (StringUtil.isBlank(model.getId())) {
			flowModel.setCreated(Calendar.getInstance().getTime());
		}
		if (StringUtil.isNotBlank(model.getModelEditorXml())) {
			flowModel.setModelEditorJson(getBpmnJson(model.getModelEditorXml()));
		}
		this.saveOrUpdate(flowModel);
		return flowModel;
	}

	@Override
	public Map<String, Object> modelView(String processDefinitionId, String processInstanceId) {
		Map<String, Object> result = new HashMap<>();
		// 节点标记
		if (StringUtil.isNotBlank(processInstanceId)) {
			result.put("flow", this.historyFlowList(processInstanceId, null, null));
			HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
			processDefinitionId = processInstance.getProcessDefinitionId();
		}
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		// 流程图展示
		result.put("xml", new String(new BpmnXMLConverter().convertToXML(bpmnModel)));
		return result;
	}

	@Override
	public void diagramView(String processInstanceId, HttpServletResponse httpServletResponse) {
		// 获得当前活动的节点
		String processDefinitionId;
		// 如果流程已经结束，则得到结束节点
		if (this.isFinished(processInstanceId)) {
			HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			processDefinitionId = pi.getProcessDefinitionId();
		} else {
			// 如果流程没有结束，则取当前活动节点
			// 根据流程实例ID获得当前处于活动状态的ActivityId合集
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			processDefinitionId = pi.getProcessDefinitionId();
		}
		List<String> highLightedActivities = new ArrayList<>();

		// 获得活动的节点
		List<HistoricActivityInstance> highLightedActivityList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();

		for (HistoricActivityInstance tempActivity : highLightedActivityList) {
			String activityId = tempActivity.getActivityId();
			highLightedActivities.add(activityId);
		}

		List<String> flows = new ArrayList<>();
		// 获取流程图
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		ProcessEngineConfiguration engConf = processEngine.getProcessEngineConfiguration();

		ProcessDiagramGenerator diagramGenerator = engConf.getProcessDiagramGenerator();
		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "bmp", highLightedActivities, flows, engConf.getActivityFontName(),
			engConf.getLabelFontName(), engConf.getAnnotationFontName(), engConf.getClassLoader(), 1.0, true);
		OutputStream out = null;
		byte[] buf = new byte[1024];
		int length;
		try {
			out = httpServletResponse.getOutputStream();
			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
			}
		} catch (IOException e) {
			log.error("操作异常", e);
		} finally {
			IoUtil.closeSilently(out);
			IoUtil.closeSilently(in);
		}
	}

	@Override
	public void resourceView(String processDefinitionId, String processInstanceId, String resourceType, HttpServletResponse response) {
		if (StringUtil.isAllBlank(processDefinitionId, processInstanceId)) {
			return;
		}
		if (StringUtil.isBlank(processDefinitionId)) {
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			processDefinitionId = processInstance.getProcessDefinitionId();
		}
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
		if (resourceType.equals(IMAGE_NAME)) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals(XML_NAME)) {
			resourceName = processDefinition.getResourceName();
		}
		try {
			InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
			byte[] b = new byte[1024];
			int len;
			while ((len = resourceAsStream.read(b, 0, INT_1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public byte[] getModelEditorXML(FlowModel model) {
		return getBpmnXML(model);
	}

	/**
	 * 是否已完结
	 *
	 * @param processInstanceId 流程实例id
	 * @return bool
	 */
	private boolean isFinished(String processInstanceId) {
		return historyService.createHistoricProcessInstanceQuery().finished()
			.processInstanceId(processInstanceId).count() > 0;
	}


	/**
	 * xml转bpmn json
	 *
	 * @param xml xml
	 * @return json
	 */
	private String getBpmnJson(String xml) {
		return BPMN_JSON_CONVERTER.convertToJson(getBpmnModel(xml)).toString();
	}

	/**
	 * xml转bpmnModel
	 *
	 * @param xml xml
	 * @return bpmnModel
	 */
	private BpmnModel getBpmnModel(String xml) {
		return BPMN_XML_CONVERTER.convertToBpmnModel(new StringStreamSource(xml), false, false);
	}

	private byte[] getBpmnXML(FlowModel model) {
		BpmnModel bpmnModel = getBpmnModel(model);
		return getBpmnXML(bpmnModel);
	}

	private byte[] getBpmnXML(BpmnModel bpmnModel) {
		for (Process process : bpmnModel.getProcesses()) {
			if (StringUtils.isNotEmpty(process.getId())) {
				char firstCharacter = process.getId().charAt(0);
				if (Character.isDigit(firstCharacter)) {
					process.setId("a" + process.getId());
				}
			}
		}
		return BPMN_XML_CONVERTER.convertToXML(bpmnModel);
	}

	private BpmnModel getBpmnModel(FlowModel model) {
		BpmnModel bpmnModel;
		try {
			Map<String, FlowModel> formMap = new HashMap<>(16);
			Map<String, FlowModel> decisionTableMap = new HashMap<>(16);

			List<FlowModel> referencedModels = baseMapper.findByParentModelId(model.getId());
			for (FlowModel childModel : referencedModels) {
				if (FlowModel.MODEL_TYPE_FORM == childModel.getModelType()) {
					formMap.put(childModel.getId(), childModel);

				} else if (FlowModel.MODEL_TYPE_DECISION_TABLE == childModel.getModelType()) {
					decisionTableMap.put(childModel.getId(), childModel);
				}
			}
			bpmnModel = getBpmnModel(model, formMap, decisionTableMap);
		} catch (Exception e) {
			log.error("Could not generate BPMN 2.0 model for {}", model.getId(), e);
			throw new ServiceException("Could not generate BPMN 2.0 model");
		}
		return bpmnModel;
	}

	private BpmnModel getBpmnModel(FlowModel model, Map<String, FlowModel> formMap, Map<String, FlowModel> decisionTableMap) {
		try {
			ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(model.getModelEditorJson());
			Map<String, String> formKeyMap = new HashMap<>(16);
			for (FlowModel formModel : formMap.values()) {
				formKeyMap.put(formModel.getId(), formModel.getModelKey());
			}
			Map<String, String> decisionTableKeyMap = new HashMap<>(16);
			for (FlowModel decisionTableModel : decisionTableMap.values()) {
				decisionTableKeyMap.put(decisionTableModel.getId(), decisionTableModel.getModelKey());
			}
			BpmnJsonConverterContext converterContext = new CustomBpmnJsonConverterContext(formKeyMap, decisionTableKeyMap);
			return BPMN_JSON_CONVERTER.convertToBpmnModel(editorJsonNode, converterContext);
		} catch (Exception e) {
			log.error("Could not generate BPMN 2.0 model for {}", model.getId(), e);
			throw new ServiceException("Could not generate BPMN 2.0 model");
		}
	}

}
