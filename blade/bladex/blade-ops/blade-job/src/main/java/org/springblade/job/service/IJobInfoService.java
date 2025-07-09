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
package org.springblade.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.vo.JobInfoVO;

import java.util.List;

/**
 * 任务信息表 服务类
 *
 * @author BladeX
 */
public interface IJobInfoService extends BaseService<JobInfo> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param jobInfo
	 * @return
	 */
	IPage<JobInfoVO> selectJobInfoPage(IPage<JobInfoVO> page, JobInfoVO jobInfo);

	/**
	 * 保存并同步
	 *
	 * @return
	 */
	Boolean submitAndSync(JobInfo jobInfo);

	/**
	 * 删除并同步
	 *
	 * @return
	 */
	Boolean removeAndSync(List<Long> ids);

	/**
	 * 启用禁用服务
	 *
	 * @param id     任务服务ID
	 * @param enable 是否启用
	 * @return
	 */
	Boolean changeServerJob(Long id, Integer enable);

	/**
	 * 运行服务
	 *
	 * @param id 任务服务ID
	 * @return
	 */
	Boolean runServerJob(Long id);

	/**
	 * 数据同步
	 *
	 * @return
	 */
	Boolean sync();

}
