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

package org.springblade.modules.visual.log;

import org.springblade.core.log.constant.EventConstant;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.UrlUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.modules.visual.pojo.entity.VisualLog;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 大屏日志信息事件发送
 *
 * @author Chill
 */
public class VisualLogPublisher {

	public static void publishEvent(String methodName, String methodClass, String title, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		VisualLog log = new VisualLog();
		log.setTitle(title);
		log.setTime(String.valueOf(time));
		log.setMethodClass(methodClass);
		log.setMethodName(methodName);
		log.setRemoteIp(WebUtil.getIP(request));
		log.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
		log.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
		log.setMethod(request.getMethod());
		log.setParams(WebUtil.getRequestContent(request));
		log.setCreateTime(DateUtil.now());
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, log);
		SpringUtil.publishEvent(new VisualLogEvent(event));
	}

}
