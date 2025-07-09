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
package org.springblade.core.oss.model;

import io.minio.messages.Item;
import io.minio.messages.Owner;
import lombok.Data;
import org.springblade.core.tool.utils.DateUtil;

import java.util.Date;

/**
 * MinioItem
 *
 * @author Chill
 */
@Data
public class MinioItem {

	private String objectName;
	private Date lastModified;
	private String etag;
	private Long size;
	private String storageClass;
	private Owner owner;
	private boolean isDir;
	private String category;

	public MinioItem(Item item) {
		this.objectName = item.objectName();
		this.lastModified = DateUtil.toDate(item.lastModified().toLocalDateTime());
		this.etag = item.etag();
		this.size = item.size();
		this.storageClass = item.storageClass();
		this.owner = item.owner();
		this.isDir = item.isDir();
		this.category = isDir ? "dir" : "file";
	}

}
