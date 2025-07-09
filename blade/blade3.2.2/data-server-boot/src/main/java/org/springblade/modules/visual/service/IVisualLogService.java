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
package org.springblade.modules.visual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.visual.pojo.dto.VisualLogDTO;
import org.springblade.modules.visual.pojo.entity.VisualLog;

/**
 * 服务类
 *
 * @author BladeX
 */
public interface IVisualLogService extends IService<VisualLog> {

	/**
	 * 获取 日志信息
	 *
	 * @param id 主键
	 * @return VisualDTO
	 */
	VisualLogDTO detail(Long id);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param visualLog
	 * @return
	 */
	IPage<VisualLogDTO> selectVisualLogPage(IPage<VisualLogDTO> page, VisualLogDTO visualLog);

}
