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

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.oss.model.OssFile;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.oss.rule.OssRule;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 腾讯云 COS 操作
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020/1/7 17:24
 */
@AllArgsConstructor
public class TencentCosTemplate implements OssTemplate {
	private final COSClient cosClient;
	private final OssProperties ossProperties;
	private final OssRule ossRule;

	@Override
	@SneakyThrows
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			cosClient.createBucket(getBucketName(bucketName));
			// TODO: 权限是否需要修改为私有，当前为 公有读、私有写
			cosClient.setBucketAcl(getBucketName(bucketName), CannedAccessControlList.PublicRead);
		}
	}

	@Override
	@SneakyThrows
	public void removeBucket(String bucketName) {
		cosClient.deleteBucket(getBucketName(bucketName));
	}

	@Override
	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		return cosClient.doesBucketExist(getBucketName(bucketName));
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		cosClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		cosClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@Override
	@SneakyThrows
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public OssFile statFile(String bucketName, String fileName) {
		ObjectMetadata stat = cosClient.getObjectMetadata(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(fileName);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.getContentMD5());
		ossFile.setLength(stat.getContentLength());
		ossFile.setPutTime(stat.getLastModified());
		ossFile.setContentType(stat.getContentType());
		return ossFile;
	}

	@Override
	@SneakyThrows
	public String filePath(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String filePath(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String fileLink(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String fileLink(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StringPool.SLASH).concat(fileName);
	}

	/**
	 * 文件对象
	 *
	 * @param file 上传文件类
	 * @return
	 */
	@Override
	@SneakyThrows
	public BladeFile putFile(MultipartFile file) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file);
	}

	/**
	 * @param fileName 上传文件名
	 * @param file     上传文件类
	 * @return
	 */
	@Override
	@SneakyThrows
	public BladeFile putFile(String fileName, MultipartFile file) {
		return putFile(ossProperties.getBucketName(), fileName, file);
	}

	@Override
	@SneakyThrows
	public BladeFile putFile(String bucketName, String fileName, MultipartFile file) {
		return putFile(bucketName, fileName, file.getInputStream());
	}

	@Override
	@SneakyThrows
	public BladeFile putFile(String fileName, InputStream stream) {
		return putFile(ossProperties.getBucketName(), fileName, stream);
	}

	@Override
	@SneakyThrows
	public BladeFile putFile(String bucketName, String fileName, InputStream stream) {
		return put(bucketName, stream, fileName, false);
	}

	@SneakyThrows
	public BladeFile put(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);
		String originalName = key;
		key = getFileName(key);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		// 覆盖上传
		if (cover) {
			cosClient.putObject(getBucketName(bucketName), key, stream, objectMetadata);
		} else {
			PutObjectResult response = cosClient.putObject(getBucketName(bucketName), key, stream, objectMetadata);
			int retry = 0;
			int retryCount = 5;
			while (!StringUtils.hasText(response.getETag()) && retry < retryCount) {
				response = cosClient.putObject(getBucketName(bucketName), key, stream, objectMetadata);
				retry++;
			}
		}
		BladeFile file = new BladeFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setDomain(getOssHost(bucketName));
		file.setLink(fileLink(bucketName, key));
		return file;
	}

	@Override
	@SneakyThrows
	public void removeFile(String fileName) {
		cosClient.deleteObject(getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public void removeFile(String bucketName, String fileName) {
		cosClient.deleteObject(getBucketName(bucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);
	}

	@Override
	@SneakyThrows
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}

	/**
	 * 获取私有存储文件输入流
	 *
	 * @param fileName 存储桶文件名称
	 * @return InputStream
	 */
	@Override
	public InputStream statFileStream(String fileName) {
		return statFileStream(ossProperties.getBucketName(), fileName);
	}

	/**
	 * 获取私有存储文件输入流
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶文件名称
	 * @return InputStream
	 */
	@Override
	public InputStream statFileStream(String bucketName, String fileName) {
		COSObject object = cosClient.getObject(getBucketName(bucketName), fileName);
		return object.getObjectContent();
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @return String
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	private String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName).concat(StringPool.DASH).concat(ossProperties.getAppId());
	}

	/**
	 * 根据规则生成文件名称规则
	 *
	 * @param originalFilename 原始文件名
	 * @return string
	 */
	private String getFileName(String originalFilename) {
		return ossRule.fileName(originalFilename);
	}

	/**
	 * 获取域名
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	public String getOssHost(String bucketName) {
		String prefix = getEndpoint().contains("https://") ? "https://" : "http://";
		return prefix + cosClient.getClientConfig().getEndpointBuilder().buildGeneralApiEndpoint(getBucketName(bucketName));
	}

	/**
	 * 获取域名
	 *
	 * @return String
	 */
	public String getOssHost() {
		return getOssHost(ossProperties.getBucketName());
	}

	/**
	 * 获取服务地址
	 *
	 * @return String
	 */
	public String getEndpoint() {
		if (StringUtil.isBlank(ossProperties.getTransformEndpoint())) {
			return ossProperties.getEndpoint();
		}
		return ossProperties.getTransformEndpoint();
	}

}
