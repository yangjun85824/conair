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
package org.springblade.core.oss;


import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.oss.model.OssFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * OssTemplate抽象API
 *
 * @author Chill
 */
public interface OssTemplate {

	/**
	 * 创建 存储桶
	 *
	 * @param bucketName 存储桶名称
	 */
	void makeBucket(String bucketName);

	/**
	 * 删除 存储桶
	 *
	 * @param bucketName 存储桶名称
	 */
	void removeBucket(String bucketName);

	/**
	 * 存储桶是否存在
	 *
	 * @param bucketName 存储桶名称
	 * @return boolean
	 */
	boolean bucketExists(String bucketName);

	/**
	 * 拷贝文件
	 *
	 * @param bucketName     存储桶名称
	 * @param fileName       存储桶文件名称
	 * @param destBucketName 目标存储桶名称
	 */
	void copyFile(String bucketName, String fileName, String destBucketName);

	/**
	 * 拷贝文件
	 *
	 * @param bucketName     存储桶名称
	 * @param fileName       存储桶文件名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶文件名称
	 */
	void copyFile(String bucketName, String fileName, String destBucketName, String destFileName);

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶文件名称
	 * @return InputStream
	 */
	OssFile statFile(String fileName);

	/**
	 * 获取文件信息
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶文件名称
	 * @return InputStream
	 */
	OssFile statFile(String bucketName, String fileName);

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	String filePath(String fileName);

	/**
	 * 获取文件相对路径
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @return String
	 */
	String filePath(String bucketName, String fileName);

	/**
	 * 获取文件地址
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	String fileLink(String fileName);

	/**
	 * 获取文件地址
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @return String
	 */
	String fileLink(String bucketName, String fileName);

	/**
	 * 上传文件
	 *
	 * @param file 上传文件类
	 * @return BladeFile
	 */
	BladeFile putFile(MultipartFile file);

	/**
	 * 上传文件
	 *
	 * @param file     上传文件类
	 * @param fileName 上传文件名
	 * @return BladeFile
	 */
	BladeFile putFile(String fileName, MultipartFile file);

	/**
	 * 上传文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   上传文件名
	 * @param file       上传文件类
	 * @return BladeFile
	 */
	BladeFile putFile(String bucketName, String fileName, MultipartFile file);

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param stream   文件流
	 * @return BladeFile
	 */
	BladeFile putFile(String fileName, InputStream stream);

	/**
	 * 上传文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @param stream     文件流
	 * @return BladeFile
	 */
	BladeFile putFile(String bucketName, String fileName, InputStream stream);

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 */
	void removeFile(String fileName);

	/**
	 * 删除文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 */
	void removeFile(String bucketName, String fileName);

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 */
	void removeFiles(List<String> fileNames);

	/**
	 * 批量删除文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileNames  存储桶对象名称集合
	 */
	void removeFiles(String bucketName, List<String> fileNames);


	/**
	 * 获取私有存储文件输入流
	 *
	 * @param fileName 存储桶文件名称
	 * @return InputStream
	 */
	InputStream statFileStream(String fileName);

	/**
	 * 获取私有存储文件输入流
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶文件名称
	 * @return InputStream
	 */
	InputStream statFileStream(String bucketName, String fileName);
}
