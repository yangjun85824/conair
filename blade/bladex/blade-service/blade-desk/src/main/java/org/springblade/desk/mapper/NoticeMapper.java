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
package org.springblade.desk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.desk.pojo.entity.Notice;
import org.springblade.desk.pojo.vo.NoticeVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface NoticeMapper extends BaseMapper<Notice> {

	/**
	 * 前N条数据
	 *
	 * @param number 数量
	 * @return List<Notice>
	 */
	List<Notice> topList(Integer number);

	/**
	 * 自定义分页
	 *
	 * @param page   分页
	 * @param notice 实体
	 * @return List<NoticeVO>
	 */
	List<NoticeVO> selectNoticePage(IPage page, NoticeVO notice);

}
