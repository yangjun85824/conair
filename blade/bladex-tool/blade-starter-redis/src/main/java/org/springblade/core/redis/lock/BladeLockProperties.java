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
 * Author: DreamLu (596392912@qq.com)
 */

package org.springblade.core.redis.lock;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 分布式锁配置
 *
 * @author L.cm
 */
@Getter
@Setter
@ConfigurationProperties(BladeLockProperties.PREFIX)
public class BladeLockProperties {
	public static final String PREFIX = "blade.lock";

	/**
	 * 是否开启：默认为：false，便于生成配置提示。
	 */
	private Boolean enabled = Boolean.FALSE;
	/**
	 * 单机配置：redis 服务地址
	 */
	private String address = "redis://127.0.0.1:6379";
	/**
	 * 密码配置
	 */
	private String password;
	/**
	 * db
	 */
	private Integer database = 0;
	/**
	 * 连接池大小
	 */
	private Integer poolSize = 20;
	/**
	 * 最小空闲连接数
	 */
	private Integer idleSize = 5;
	/**
	 * 连接空闲超时，单位：毫秒
	 */
	private Integer idleTimeout = 60000;
	/**
	 * 连接超时，单位：毫秒
	 */
	private Integer connectionTimeout = 3000;
	/**
	 * 命令等待超时，单位：毫秒
	 */
	private Integer timeout = 10000;
	/**
	 * 集群模式，单机：single，主从：master，哨兵模式：sentinel，集群模式：cluster
	 */
	private Mode mode = Mode.single;
	/**
	 * 主从模式，主地址
	 */
	private String masterAddress;
	/**
	 * 主从模式，从地址
	 */
	private String[] slaveAddress;
	/**
	 * 哨兵模式：主名称
	 */
	private String masterName;
	/**
	 * 哨兵模式地址
	 */
	private String[] sentinelAddress;
	/**
	 * 集群模式节点地址
	 */
	private String[] nodeAddress;

	public enum Mode {
		/**
		 * 集群模式，单机：single，主从：master，哨兵模式：sentinel，集群模式：cluster
		 */
		single,
		master,
		sentinel,
		cluster
	}
}
