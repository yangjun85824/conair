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
package org.springblade.core.boot.ctrl;

import org.springblade.core.boot.file.LocalFile;
import org.springblade.core.boot.file.BladeFileUtil;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Charsets;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Blade控制器封装类
 *
 * @author Chill
 */
public class BladeController {

	/**
	 * ============================     REQUEST    =================================================
	 */

	@Autowired
	private HttpServletRequest request;

	/**
	 * 获取request
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 获取当前用户
	 *
	 * @return
	 */
	public BladeUser getUser() {
		return AuthUtil.getUser();
	}

	/** ============================     API_RESULT    =================================================  */

	/**
	 * 返回ApiResult
	 *
	 * @param data
	 * @return R
	 */
	public <T> R<T> data(T data) {
		return R.data(data);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data
	 * @param message
	 * @return R
	 */
	public <T> R<T> data(T data, String message) {
		return R.data(data, message);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data
	 * @param message
	 * @param code
	 * @return R
	 */
	public <T> R<T> data(T data, String message, int code) {
		return R.data(code, data, message);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param message
	 * @return R
	 */
	public R success(String message) {
		return R.success(message);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param message
	 * @return R
	 */
	public R fail(String message) {
		return R.fail(message);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param flag
	 * @return R
	 */
	public R status(boolean flag) {
		return R.status(flag);
	}


	/**============================     FILE    =================================================  */

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file
	 * @return
	 */
	public LocalFile getFile(MultipartFile file) {
		return BladeFileUtil.getFile(file);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file
	 * @param dir
	 * @return
	 */
	public LocalFile getFile(MultipartFile file, String dir) {
		return BladeFileUtil.getFile(file, dir);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file
	 * @param dir
	 * @param path
	 * @param virtualPath
	 * @return
	 */
	public LocalFile getFile(MultipartFile file, String dir, String path, String virtualPath) {
		return BladeFileUtil.getFile(file, dir, path, virtualPath);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files
	 * @return
	 */
	public List<LocalFile> getFiles(List<MultipartFile> files) {
		return BladeFileUtil.getFiles(files);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files
	 * @param dir
	 * @return
	 */
	public List<LocalFile> getFiles(List<MultipartFile> files, String dir) {
		return BladeFileUtil.getFiles(files, dir);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files
	 * @param path
	 * @param virtualPath
	 * @return
	 */
	public List<LocalFile> getFiles(List<MultipartFile> files, String dir, String path, String virtualPath) {
		return BladeFileUtil.getFiles(files, dir, path, virtualPath);
	}
	/**
	 * 下载文件
	 *
	 * @param file 文件
	 * @return {ResponseEntity}
	 * @throws IOException io异常
	 */
	protected ResponseEntity<ResourceRegion> download(File file) throws IOException {
		String fileName = file.getName();
		return download(file, fileName);
	}

	/**
	 * 下载
	 *
	 * @param file     文件
	 * @param fileName 生成的文件名
	 * @return {ResponseEntity}
	 * @throws IOException io异常
	 */
	protected ResponseEntity<ResourceRegion> download(File file, String fileName) throws IOException {
		Resource resource = new FileSystemResource(file);
		return download(resource, fileName);
	}

	/**
	 * 下载
	 *
	 * @param resource 资源
	 * @param fileName 生成的文件名
	 * @return {ResponseEntity}
	 * @throws IOException io异常
	 */
	protected ResponseEntity<ResourceRegion> download(Resource resource, String fileName) throws IOException {
		HttpServletRequest request = WebUtil.getRequest();
		String header = request.getHeader(HttpHeaders.USER_AGENT);
		// 避免空指针
		header = header == null ? StringPool.EMPTY : header.toUpperCase();
		HttpStatus status;
		String msie= "MSIE";
		String trident= "TRIDENT";
		String edge= "EDGE";
		if (header.contains(msie) || header.contains(trident) || header.contains(edge)) {
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.CREATED;
		}
		// 断点续传
		long position = 0;
		long count = resource.contentLength();
		String range = request.getHeader(HttpHeaders.RANGE);
		if (null != range) {
			status = HttpStatus.PARTIAL_CONTENT;
			String[] rangeRange = range.replace("bytes=", StringPool.EMPTY).split(StringPool.DASH);
			position = Long.parseLong(rangeRange[0]);
			if (rangeRange.length > 1) {
				long end = Long.parseLong(rangeRange[1]);
				count = end - position + 1;
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String encodeFileName = UriUtils.encode(fileName, Charsets.UTF_8);
		// 兼容各种浏览器下载：
		// https://blog.robotshell.org/2012/deal-with-http-header-encoding-for-file-download/
		String disposition = "attachment;" +
			"filename=\"" + encodeFileName + "\";" +
			"filename*=utf-8''" + encodeFileName;
		headers.set(HttpHeaders.CONTENT_DISPOSITION, disposition);
		return new ResponseEntity<>(new ResourceRegion(resource, position, count), headers, status);
	}

}
