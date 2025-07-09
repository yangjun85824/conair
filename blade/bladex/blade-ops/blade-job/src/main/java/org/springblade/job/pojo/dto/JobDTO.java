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
package org.springblade.job.pojo.dto;

import lombok.Data;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.entity.JobServer;
import tech.powerjob.client.PowerJobClient;

/**
 * 任务数据DTO
 *
 * @author Chill
 */
@Data
public class JobDTO {

	/**
	 * 任务信息类
	 */
	private JobInfo jobInfo;

	/**
	 * 任务服务类
	 */
	private JobServer jobServer;

	/**
	 * 任务客户端类
	 */
	private PowerJobClient powerJobClient;

}
