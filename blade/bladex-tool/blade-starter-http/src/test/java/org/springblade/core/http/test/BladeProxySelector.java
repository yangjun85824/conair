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
package org.springblade.core.http.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理设置
 *
 * @author L.cm
 */
@Slf4j
public class BladeProxySelector extends ProxySelector {

	@Override
	public List<Proxy> select(URI uri) {
		// 注意代理都不可用
		List<Proxy> proxyList = new ArrayList<>();
		proxyList.add(getProxy("127.0.0.1", 8080));
		proxyList.add(getProxy("127.0.0.1", 8081));
		proxyList.add(getProxy("127.0.0.1", 8082));
		proxyList.add(getProxy("127.0.0.1", 3128));
		return proxyList;
	}

	@Override
	public void connectFailed(URI uri, SocketAddress address, IOException ioe) {
		// 注意：经过测试，此处不会触发
		log.error("ConnectFailed uri:{}, address:{}, ioe:{}", uri, address, ioe);
	}

	/**
	 * 构造 Proxy
	 *
	 * @param host host
	 * @param port 端口
	 * @return Proxy 对象
	 */
	public static Proxy getProxy(String host, int port) {
		return new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, port));
	}
}
