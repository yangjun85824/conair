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
import org.springblade.modules.visual.pojo.entity.VisualFun;
import org.springblade.modules.visual.pojo.vo.VisualFunVO;

/**
 * 可视化代码库表 服务类
 *
 * @author Blade
 */
public interface IVisualFunService extends IService<VisualFun> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param visualFun
	 * @return
	 */
	IPage<VisualFunVO> selectVisualFunPage(IPage<VisualFunVO> page, VisualFunVO visualFun);

}
