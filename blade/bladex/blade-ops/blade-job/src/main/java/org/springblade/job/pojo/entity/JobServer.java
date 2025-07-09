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
package org.springblade.job.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 任务服务表 实体类
 *
 * @author BladeX
 */
@Data
@TableName("blade_job_server")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "任务服务表")
public class JobServer extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 任务服务名称
	 */
	@Schema(description = "任务服务名称")
	private String jobServerName;
	/**
	 * 任务服务器地址
	 */
	@Schema(description = "任务服务器地址")
	private String jobServerUrl;
	/**
	 * 任务应用名称
	 */
	@Schema(description = "任务应用名称")
	private String jobAppName;
	/**
	 * 任务应用密码
	 */
	@Schema(description = "任务应用密码")
	private String jobAppPassword;
	/**
	 * 任务备注
	 */
	@Schema(description = "任务备注")
	private String jobRemark;

}
