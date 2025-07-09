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
package org.springblade.system.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * RegionExcel
 *
 * @author Chill
 */
@Data
@ColumnWidth(16)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class RegionExcel implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@ExcelProperty("区划编号")
	private String code;

	@ExcelProperty("父区划编号")
	private String parentCode;

	@ExcelProperty("祖区划编号")
	private String ancestors;

	@ExcelProperty("区划名称")
	private String name;

	@ExcelProperty("省级区划编号")
	private String provinceCode;

	@ExcelProperty("省级名称")
	private String provinceName;

	@ExcelProperty("市级区划编号")
	private String cityCode;

	@ExcelProperty("市级名称")
	private String cityName;

	@ExcelProperty("区级区划编号")
	private String districtCode;

	@ExcelProperty("区级名称")
	private String districtName;

	@ExcelProperty("镇级区划编号")
	private String townCode;

	@ExcelProperty("镇级名称")
	private String townName;

	@ExcelProperty("村级区划编号")
	private String villageCode;

	@ExcelProperty("村级名称")
	private String villageName;

	@ExcelProperty("层级")
	private Integer regionLevel;

	@ExcelProperty("排序")
	private Integer sort;

	@ExcelProperty("备注")
	private String remark;

}
