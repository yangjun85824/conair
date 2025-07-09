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
package org.springblade.core.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springblade.core.excel.listener.DataListener;
import org.springblade.core.excel.listener.ImportListener;
import org.springblade.core.excel.support.ExcelException;
import org.springblade.core.excel.support.ExcelImporter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * Excel工具类
 *
 * @author Chill
 * @apiNote https://easyexcel.opensource.alibaba.com/
 */
public class ExcelUtil {

	/**
	 * 读取excel的所有sheet数据
	 *
	 * @param excel excel文件
	 * @return List<Object>
	 */
	public static <T> List<T> read(MultipartFile excel, Class<T> clazz) {
		DataListener<T> dataListener = new DataListener<>();
		ExcelReaderBuilder builder = getReaderBuilder(excel, dataListener, clazz);
		if (builder == null) {
			return null;
		}
		builder.doReadAll();
		return dataListener.getDataList();
	}

	/**
	 * 读取excel的指定sheet数据
	 *
	 * @param excel   excel文件
	 * @param sheetNo sheet序号(从0开始)
	 * @return List<Object>
	 */
	public static <T> List<T> read(MultipartFile excel, int sheetNo, Class<T> clazz) {
		return read(excel, sheetNo, 1, clazz);
	}

	/**
	 * 读取excel的指定sheet数据
	 *
	 * @param excel         excel文件
	 * @param sheetNo       sheet序号(从0开始)
	 * @param headRowNumber 表头行数
	 * @return List<Object>
	 */
	public static <T> List<T> read(MultipartFile excel, int sheetNo, int headRowNumber, Class<T> clazz) {
		DataListener<T> dataListener = new DataListener<>();
		ExcelReaderBuilder builder = getReaderBuilder(excel, dataListener, clazz);
		if (builder == null) {
			return null;
		}
		builder.sheet(sheetNo).headRowNumber(headRowNumber).doRead();
		return dataListener.getDataList();
	}

	/**
	 * 读取并导入数据
	 *
	 * @param excel    excel文件
	 * @param importer 导入逻辑类
	 * @param <T>      泛型
	 */
	public static <T> void save(MultipartFile excel, ExcelImporter<T> importer, Class<T> clazz) {
		ImportListener<T> importListener = new ImportListener<>(importer);
		ExcelReaderBuilder builder = getReaderBuilder(excel, importListener, clazz);
		if (builder != null) {
			builder.doReadAll();
		}
	}

	/**
	 * 导出excel
	 *
	 * @param response 响应类
	 * @param dataList 数据列表
	 * @param clazz    class类
	 * @param <T>      泛型
	 */
	@SneakyThrows
	public static <T> void export(HttpServletResponse response, List<T> dataList, Class<T> clazz) {
		export(response, DateUtils.format(new Date(), DateUtils.DATE_FORMAT_14), "导出数据", dataList, clazz);
	}

	/**
	 * 导出excel
	 *
	 * @param response  响应类
	 * @param fileName  文件名
	 * @param sheetName sheet名
	 * @param dataList  数据列表
	 * @param clazz     class类
	 * @param <T>       泛型
	 */
	@SneakyThrows
	public static <T> void export(HttpServletResponse response, String fileName, String sheetName, List<T> dataList, Class<T> clazz) {
		export(response, DateUtils.format(new Date(), DateUtils.DATE_FORMAT_14), "导出数据", dataList, null, clazz);
	}

	/**
	 * 导出excel
	 *
	 * @param response     响应类
	 * @param fileName     文件名
	 * @param sheetName    sheet名
	 * @param dataList     数据列表
	 * @param clazz        class类
	 * @param writeHandler 自定义处理器
	 * @param <T>          泛型
	 */
	@SneakyThrows
	public static <T> void export(HttpServletResponse response, String fileName, String sheetName, List<T> dataList, WriteHandler writeHandler, Class<T> clazz) {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

		ExcelWriterBuilder write = EasyExcel.write(response.getOutputStream(), clazz);
		if (writeHandler != null) {
			write.registerWriteHandler(writeHandler);
		}
		write.sheet(sheetName).doWrite(dataList);
	}

	/**
	 * 获取构建类
	 *
	 * @param excel        excel文件
	 * @param readListener excel监听类
	 * @return ExcelReaderBuilder
	 */
	public static <T> ExcelReaderBuilder getReaderBuilder(MultipartFile excel, ReadListener<T> readListener, Class<T> clazz) {
		String filename = excel.getOriginalFilename();
		if (!StringUtils.hasText(filename)) {
			throw new ExcelException("请上传文件!");
		}
		if ((!StringUtils.endsWithIgnoreCase(filename, ".xls") && !StringUtils.endsWithIgnoreCase(filename, ".xlsx"))) {
			throw new ExcelException("请上传正确的excel文件!");
		}
		InputStream inputStream;
		try {
			inputStream = new BufferedInputStream(excel.getInputStream());
			return EasyExcel.read(inputStream, clazz, readListener);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
