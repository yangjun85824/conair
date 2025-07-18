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

package org.springblade.core.launch.utils;

import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * INet 相关工具
 *
 * @author L.cm
 */
public class INetUtil {
	public static final String LOCAL_HOST = "127.0.0.1";

	/**
	 * 获取 服务器 hostname
	 *
	 * @return hostname
	 */
	public static String getHostName() {
		String hostname;
		try {
			InetAddress address = InetAddress.getLocalHost();
			// force a best effort reverse DNS lookup
			hostname = address.getHostName();
			if (ObjectUtils.isEmpty(hostname)) {
				hostname = address.toString();
			}
		} catch (UnknownHostException ignore) {
			hostname = LOCAL_HOST;
		}
		return hostname;
	}

	/**
	 * 获取 服务器 HostIp
	 *
	 * @return HostIp
	 */
	public static String getHostIp() {
		String hostAddress;
		try {
			InetAddress address = INetUtil.getLocalHostLANAddress();
			// force a best effort reverse DNS lookup
			hostAddress = address.getHostAddress();
			if (ObjectUtils.isEmpty(hostAddress)) {
				hostAddress = address.toString();
			}
		} catch (UnknownHostException ignore) {
			hostAddress = LOCAL_HOST;
		}
		return hostAddress;
	}

	/**
	 * https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
	 *
	 * <p>
	 * Returns an <code>InetAddress</code> object encapsulating what is most likely the machine's LAN IP address.
	 * <p/>
	 * This method is intended for use as a replacement of JDK method <code>InetAddress.getLocalHost</code>, because
	 * that method is ambiguous on Linux systems. Linux systems enumerate the loopback network interface the same
	 * way as regular LAN network interfaces, but the JDK <code>InetAddress.getLocalHost</code> method does not
	 * specify the algorithm used to select the address returned under such circumstances, and will often return the
	 * loopback address, which is not valid for network communication. Details
	 * <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4665037">here</a>.
	 * <p/>
	 * This method will scan all IP addresses on all network interfaces on the host machine to determine the IP address
	 * most likely to be the machine's LAN address. If the machine has multiple IP addresses, this method will prefer
	 * a site-local IP address (e.g. 192.168.x.x or 10.10.x.x, usually IPv4) if the machine has one (and will return the
	 * first site-local address if the machine has more than one), but if the machine does not hold a site-local
	 * address, this method will return simply the first non-loopback address found (IPv4 or IPv6).
	 * <p/>
	 * If this method cannot find a non-loopback address using this selection algorithm, it will fall back to
	 * calling and returning the result of JDK method <code>InetAddress.getLocalHost</code>.
	 * <p/>
	 *
	 * @throws UnknownHostException If the LAN address of the machine cannot be found.
	 */
	private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
		try {
			InetAddress candidateAddress = null;
			// Iterate all NICs (network interface cards)...
			for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
				NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
				// Iterate all IP addresses assigned to each card...
				for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {

						if (inetAddr.isSiteLocalAddress()) {
							// Found non-loopback site-local address. Return it immediately...
							return inetAddr;
						} else if (candidateAddress == null) {
							// Found non-loopback address, but not necessarily site-local.
							// Store it as a candidate to be returned if site-local address is not subsequently found...
							candidateAddress = inetAddr;
							// Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
							// only the first. For subsequent iterations, candidate will be non-null.
						}
					}
				}
			}
			if (candidateAddress != null) {
				// We did not find a site-local address, but we found some other non-loopback address.
				// Server might have a non-site-local address assigned to its NIC (or it might be running
				// IPv6 which deprecates the "site-local" concept).
				// Return this non-loopback candidate address...
				return candidateAddress;
			}
			// At this point, we did not find a non-loopback address.
			// Fall back to returning whatever InetAddress.getLocalHost() returns...
			InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
			if (jdkSuppliedAddress == null) {
				throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
			}
			return jdkSuppliedAddress;
		} catch (Exception e) {
			UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
			unknownHostException.initCause(e);
			throw unknownHostException;
		}
	}

	/**
	 * 尝试端口时候被占用
	 *
	 * @param port 端口号
	 * @return 没有被占用：true,被占用：false
	 */
	public static boolean tryPort(int port) {
		try (ServerSocket ignore = new ServerSocket(port)) {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将 ip 转成 InetAddress
	 *
	 * @param ip ip
	 * @return InetAddress
	 */
	public static InetAddress getInetAddress(String ip) {
		try {
			return InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			return null;
		}
	}

	/**
	 * 判断是否内网 ip
	 *
	 * @param ip ip
	 * @return boolean
	 */
	public static boolean isInternalIp(String ip) {
		return isInternalIp(getInetAddress(ip));
	}

	/**
	 * 判断是否内网 ip
	 *
	 * @param address InetAddress
	 * @return boolean
	 */
	public static boolean isInternalIp(InetAddress address) {
		if (isLocalIp(address)) {
			return true;
		}
		return isInternalIp(address.getAddress());
	}

	/**
	 * 判断是否本地 ip
	 *
	 * @param address InetAddress
	 * @return boolean
	 */
	public static boolean isLocalIp(InetAddress address) {
		return address.isAnyLocalAddress()
			|| address.isLoopbackAddress()
			|| address.isSiteLocalAddress();
	}

	/**
	 * 判断是否内网 ip
	 *
	 * @param addr ip
	 * @return boolean
	 */
	public static boolean isInternalIp(byte[] addr) {
		final byte b0 = addr[0];
		final byte b1 = addr[1];
		//10.x.x.x/8
		final byte section1 = 0x0A;
		//172.16.x.x/12
		final byte section2 = (byte) 0xAC;
		final byte section3 = (byte) 0x10;
		final byte section4 = (byte) 0x1F;
		//192.168.x.x/16
		final byte section5 = (byte) 0xC0;
		final byte section6 = (byte) 0xA8;
		switch (b0) {
			case section1:
				return true;
			case section2:
				if (b1 >= section3 && b1 <= section4) {
					return true;
				}
			case section5:
				if (b1 == section6) {
					return true;
				}
			default:
				return false;
		}
	}


}
