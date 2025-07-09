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
package org.springblade.desk.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.desk.pojo.entity.Notice;
import org.springblade.desk.pojo.vo.NoticeVO;
import org.springblade.system.cache.DictCache;
import org.springblade.system.pojo.enums.DictEnum;

import java.util.Map;
import java.util.Objects;

/**
 * Notice包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class NoticeWrapper extends BaseEntityWrapper<Notice, NoticeVO> {

	public static NoticeWrapper build() {
		return new NoticeWrapper();
	}

	@Override
	public NoticeVO entityVO(Notice notice) {
		NoticeVO noticeVO = Objects.requireNonNull(BeanUtil.copyProperties(notice, NoticeVO.class));
		String dictValue = DictCache.getValue(DictEnum.NOTICE, noticeVO.getCategory());
		noticeVO.setCategoryName(dictValue);
		return noticeVO;
	}

	/**
	 * 查询条件处理
	 */
	public void noticeQuery(Map<String, Object> notice) {
		// 此场景仅在 pg数据库 map类型传参的情况下需要处理，entity传参已经包含数据类型，则无需关心
		// 针对 pg数据库 int类型字段查询需要强转的处理示例
		String searchKey = "category";
		if (Func.isNotEmpty(notice.get(searchKey))) {
			// 数据库字段为int类型，设置"="查询，具体查询参数请见 @org.springblade.core.mp.support.SqlKeyword
			notice.put(searchKey.concat("_equal"), Func.toInt(notice.get(searchKey)));
			// 默认"like"查询，pg数据库 场景会报错，所以将其删除
			notice.remove(searchKey);
		}
	}

}
