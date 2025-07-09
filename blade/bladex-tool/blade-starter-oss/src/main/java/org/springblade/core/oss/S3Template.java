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

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.core.oss.enums.PolicyType;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.oss.model.OssFile;
import org.springblade.core.oss.props.OssProperties;
import org.springblade.core.oss.rule.OssRule;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * S3Template
 *
 * @author fanjia2
 * @version 1.0.0
 * @ClassName S3Template.java
 * @Description Aws {@link S3Template 的代码实现}
 * @createTime 2022年07月04日 11:17:00
 */
@AllArgsConstructor
public class S3Template implements OssTemplate {


	/**
	 * S3客户端
	 */
	private final AmazonS3 client;

	/**
	 * 存储桶命名规则
	 */
	private final OssRule ossRule;

	/**
	 * 配置类
	 */
	private final OssProperties ossProperties;

	/**
	 * 创建 存储桶
	 *
	 * @param bucketName 存储桶名称
	 */
	@Override
	public void makeBucket(String bucketName) {
		if (
			!client.doesBucketExistV2(
				getBucketName(bucketName)
			)
		) {
			client.createBucket(
				getBucketName(bucketName));
			client.setBucketPolicy(new SetBucketPolicyRequest(getBucketName(bucketName), getPolicyType(getBucketName(bucketName), PolicyType.READ)));
		}
	}

	@SneakyThrows
	public Bucket getBucket() {
		return getBucket(getBucketName());
	}

	@SneakyThrows
	public Bucket getBucket(String bucketName) {
		Optional<Bucket> bucketOptional = client.listBuckets().stream().filter(bucket -> bucket.getName().equals(getBucketName(bucketName))).findFirst();
		return bucketOptional.orElse(null);
	}

	@SneakyThrows
	public List<Bucket> listBuckets() {
		return client.listBuckets();
	}

	/**
	 * 删除 存储桶
	 *
	 * @param bucketName 存储桶名称
	 */
	@Override
	public void removeBucket(String bucketName) {
		client.deleteBucket(new DeleteBucketRequest(getBucketName(bucketName)));
	}

	/**
	 * 存储桶是否存在
	 *
	 * @param bucketName 存储桶名称
	 * @return boolean
	 */
	@Override
	public boolean bucketExists(String bucketName) {
		return client.doesBucketExistV2(
			getBucketName(bucketName));
	}

	/**
	 * 拷贝文件
	 *
	 * @param bucketName     存储桶名称
	 * @param fileName       存储桶文件名称
	 * @param destBucketName 目标存储桶名称
	 */
	@Override
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		copyFile(bucketName, fileName, destBucketName, fileName);

	}

	/**
	 * 拷贝文件
	 *
	 * @param bucketName     存储桶名称
	 * @param fileName       存储桶文件名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶文件名称
	 */
	@Override
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {

		client.copyObject(new CopyObjectRequest(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName));

	}

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶文件名称
	 * @return InputStream
	 */
	@Override
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}

	/**
	 * 获取文件信息
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶文件名称
	 * @return InputStream
	 */
	@Override
	public OssFile statFile(String bucketName, String fileName) {
		S3Object stat = client.getObject(new GetObjectRequest(getBucketName(bucketName), fileName));
		OssFile ossFile = new OssFile();
		ossFile.setName(Func.isEmpty(stat.getKey()) ? fileName : stat.getKey());
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(String.valueOf(stat.hashCode()));
		ossFile.setLength(stat.getObjectMetadata().getContentLength());
		ossFile.setPutTime(stat.getObjectMetadata().getLastModified());
		ossFile.setContentType(stat.getObjectMetadata().getContentType());
		return ossFile;
	}

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶文件名称
	 * @return InputStream
	 */
	@Override
	public InputStream statFileStream(String fileName) {
		return statFileStream(getBucketName(), fileName);
	}

	/**
	 * 获取文件信息
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶文件名称
	 * @return InputStream
	 */
	@Override
	public InputStream statFileStream(String bucketName, String fileName) {
		return client.getObject(new GetObjectRequest(getBucketName(bucketName), fileName)).getObjectContent();
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@Override
	public String filePath(String fileName) {
		return getBucketName().concat(StringPool.SLASH).concat(fileName);
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @return String
	 */
	@Override
	public String filePath(String bucketName, String fileName) {
		return getBucketName(bucketName).concat(StringPool.SLASH).concat(fileName);
	}

	/**
	 * 获取文件地址
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@Override
	@SneakyThrows
	public String fileLink(String fileName) {
		return getEndpoint().concat(StringPool.SLASH).concat(getBucketName()).concat(StringPool.SLASH).concat(fileName);
	}

	/**
	 * 获取文件地址
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @return String
	 */
	@Override
	@SneakyThrows
	public String fileLink(String bucketName, String fileName) {
		return getEndpoint().concat(StringPool.SLASH).concat(getBucketName(bucketName)).concat(StringPool.SLASH).concat(fileName);
	}

	/**
	 * 上传文件
	 *
	 * @param file 上传文件类
	 * @return BladeFile
	 */
	@Override
	@SneakyThrows
	public BladeFile putFile(MultipartFile file) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file);
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 上传文件名
	 * @param file     上传文件类
	 * @return BladeFile
	 */
	@Override
	@SneakyThrows
	public BladeFile putFile(String fileName, MultipartFile file) {
		return putFile(ossProperties.getBucketName(), fileName, file);
	}

	/**
	 * 上传文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   上传文件名
	 * @param file       上传文件类
	 * @return BladeFile
	 */
	@Override
	@SneakyThrows
	public BladeFile putFile(String bucketName, String fileName, MultipartFile file) {
		return putFile(bucketName, file.getOriginalFilename(), file.getInputStream());
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param stream   文件流
	 * @return BladeFile
	 */
	@Override
	public BladeFile putFile(String fileName, InputStream stream) {
		return putFile(ossProperties.getBucketName(), fileName, stream);
	}

	/**
	 * 上传文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 * @param stream     文件流
	 * @return BladeFile
	 */
	@Override
	public BladeFile putFile(String bucketName, String fileName, InputStream stream) {
		return putFile(bucketName, fileName, stream, "application/octet-stream");

	}

	@SneakyThrows
	public BladeFile putFile(String bucketName, String fileName, InputStream stream, String contentType) {
		makeBucket(bucketName);
		String originalName = fileName;
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		fileName = getFileName(fileName);
		client.putObject(new PutObjectRequest(getBucketName(bucketName), fileName, stream, objectMetadata));
		BladeFile file = new BladeFile();
		file.setOriginalName(originalName);
		file.setName(fileName);
		file.setDomain(getOssHost(bucketName));
		file.setLink(fileLink(bucketName, fileName));
		return file;
	}

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 */
	@Override
	public void removeFile(String fileName) {

		removeFile(ossProperties.getBucketName(), fileName);
	}

	/**
	 * 删除文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileName   存储桶对象名称
	 */
	@Override
	public void removeFile(String bucketName, String fileName) {
		client.deleteObject(bucketName, fileName);
	}

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 */
	@Override
	public void removeFiles(List<String> fileNames) {

		removeFiles(ossProperties.getBucketName(), fileNames);
	}

	/**
	 * 批量删除文件
	 *
	 * @param bucketName 存储桶名称
	 * @param fileNames  存储桶对象名称集合
	 */
	@Override
	public void removeFiles(String bucketName, List<String> fileNames) {
		List<DeleteObjectsRequest.KeyVersion> keyVersions = fileNames.stream().map(DeleteObjectsRequest.KeyVersion::new).collect(Collectors.toList());
		DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(getBucketName());
		client.deleteObjects(deleteObjectsRequest.withKeys(keyVersions));
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
		return ossRule.bucketName(bucketName);
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
	 * 获取存储桶策略
	 *
	 * @param bucketName 存储桶名称
	 * @param policyType 策略枚举
	 * @return String
	 */
	public static String getPolicyType(String bucketName, PolicyType policyType) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n");
		builder.append("    \"Statement\": [\n");
		builder.append("        {\n");
		builder.append("            \"Action\": [\n");

		switch (policyType) {
			case WRITE:
				builder.append("                \"s3:GetBucketLocation\",\n");
				builder.append("                \"s3:ListBucketMultipartUploads\"\n");
				break;
			case READ_WRITE:
				builder.append("                \"s3:GetBucketLocation\",\n");
				builder.append("                \"s3:ListBucket\",\n");
				builder.append("                \"s3:ListBucketMultipartUploads\"\n");
				break;
			default:
				builder.append("                \"s3:GetBucketLocation\"\n");
				break;
		}

		builder.append("            ],\n");
		builder.append("            \"Effect\": \"Allow\",\n");
		builder.append("            \"Principal\": \"*\",\n");
		builder.append("            \"Resource\": \"arn:aws:s3:::");
		builder.append(bucketName);
		builder.append("\"\n");
		builder.append("        },\n");
		if (PolicyType.READ.equals(policyType)) {
			builder.append("        {\n");
			builder.append("            \"Action\": [\n");
			builder.append("                \"s3:ListBucket\"\n");
			builder.append("            ],\n");
			builder.append("            \"Effect\": \"Deny\",\n");
			builder.append("            \"Principal\": \"*\",\n");
			builder.append("            \"Resource\": \"arn:aws:s3:::");
			builder.append(bucketName);
			builder.append("\"\n");
			builder.append("        },\n");

		}
		builder.append("        {\n");
		builder.append("            \"Action\": ");

		switch (policyType) {
			case WRITE:
				builder.append("[\n");
				builder.append("                \"s3:AbortMultipartUpload\",\n");
				builder.append("                \"s3:DeleteObject\",\n");
				builder.append("                \"s3:ListMultipartUploadParts\",\n");
				builder.append("                \"s3:PutObject\"\n");
				builder.append("            ],\n");
				break;
			case READ_WRITE:
				builder.append("[\n");
				builder.append("                \"s3:AbortMultipartUpload\",\n");
				builder.append("                \"s3:DeleteObject\",\n");
				builder.append("                \"s3:GetObject\",\n");
				builder.append("                \"s3:ListMultipartUploadParts\",\n");
				builder.append("                \"s3:PutObject\"\n");
				builder.append("            ],\n");
				break;
			default:
				builder.append("\"s3:GetObject\",\n");
				break;
		}

		builder.append("            \"Effect\": \"Allow\",\n");
		builder.append("            \"Principal\": \"*\",\n");
		builder.append("            \"Resource\": \"arn:aws:s3:::");
		builder.append(bucketName);
		builder.append("/*\"\n");
		builder.append("        }\n");
		builder.append("    ],\n");
		builder.append("    \"Version\": \"2012-10-17\"\n");
		builder.append("}\n");
		return builder.toString();
	}

	/**
	 * 获取域名
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	public String getOssHost(String bucketName) {
		return getEndpoint() + StringPool.SLASH + getBucketName(bucketName);
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
