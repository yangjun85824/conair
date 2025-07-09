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
package org.springblade.core.report.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.report.entity.ReportFileEntity;
import org.springblade.core.report.props.ReportDatabaseProperties;
import org.springblade.core.report.service.IReportFileService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据库文件处理
 *
 * @author Chill
 */
@AllArgsConstructor
public class DatabaseProvider implements ReportProvider {

	private final ReportDatabaseProperties properties;
	private final IReportFileService service;

	@Override
	public InputStream loadReport(String file) {
		ReportFileEntity reportFileEntity = service.getOne(Wrappers.<ReportFileEntity>lambdaQuery().eq(ReportFileEntity::getName, getFileName(file)));
		byte[] content = reportFileEntity.getContent();
		return new ByteArrayInputStream(content);
	}

	@Override
	public void deleteReport(String file) {
		service.remove(Wrappers.<ReportFileEntity>lambdaUpdate().eq(ReportFileEntity::getName, getFileName(file)));
	}

	@Override
	public List<ReportFile> getReportFiles() {
		List<ReportFileEntity> list = service.list();
		List<ReportFile> reportFiles = new ArrayList<>();
		list.forEach(reportFileEntity -> reportFiles.add(new ReportFile(reportFileEntity.getName(), reportFileEntity.getUpdateTime())));
		return reportFiles;
	}

	@Override
	public void saveReport(String file, String content) {
		String fileName = getFileName(file);
		ReportFileEntity reportFileEntity = service.getOne(Wrappers.<ReportFileEntity>lambdaQuery().eq(ReportFileEntity::getName, fileName));
		Date now = DateUtil.now();
		if (reportFileEntity == null) {
			reportFileEntity = new ReportFileEntity();
			reportFileEntity.setName(fileName);
			reportFileEntity.setContent(content.getBytes());
			reportFileEntity.setCreateTime(now);
			reportFileEntity.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		} else {
			reportFileEntity.setContent(content.getBytes());
		}
		reportFileEntity.setUpdateTime(now);
		service.saveOrUpdate(reportFileEntity);
	}

	@Override
	public String getName() {
		return properties.getName();
	}

	@Override
	public boolean disabled() {
		return properties.isDisabled();
	}

	@Override
	public String getPrefix() {
		return properties.getPrefix();
	}

	/**
	 * 获取标准格式文件名
	 *
	 * @param name 原文件名
	 */
	private String getFileName(String name) {
		if (name.startsWith(getPrefix())) {
			name = name.substring(getPrefix().length());
		}
		return name;
	}

}
