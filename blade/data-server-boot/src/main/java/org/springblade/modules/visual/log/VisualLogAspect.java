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

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.ObjectUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.modules.visual.pojo.entity.VisualLog;
import org.springblade.modules.visual.service.IVisualLogService;

import java.util.List;

/**
 * 大屏日志切面
 *
 * @author Chill
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class VisualLogAspect {

	/**
	 * 日志服务类
	 */
	private final IVisualLogService logService;
	/**
	 * 日志配置类
	 */
	private final VisualLogProperties properties;
	/**
	 * 每天限流次数
	 */
	private final static long MAX_COUNT = 800L;
	/**
	 * 限流提醒文本
	 */
	private final static String LIMIT_MESSAGE = "访问过于频繁,请稍后再试";

	@Around("@annotation(dataLog)")
	public Object around(ProceedingJoinPoint point, DataLog dataLog) throws Throwable {
		//获取类名
		String className = point.getTarget().getClass().getName();
		//获取方法
		String methodName = point.getSignature().getName();
		//获取请求IP
		String remoteIp = WebUtil.getIP();
		//获取白名单
		List<String> whiteList = properties.getWhiteList();
		//非白名单则判断限流次数
		if (ObjectUtil.isNotEmpty(whiteList) && !CollectionUtil.contains(whiteList.listIterator(), remoteIp)) {
			long count = logService.count(
				Wrappers.<VisualLog>lambdaQuery()
					.eq(VisualLog::getRemoteIp, remoteIp)
					.gt(VisualLog::getCreateTime, DateUtil.minusDays(DateUtil.now(), 1L))
			);
			if (count > MAX_COUNT) {
				VisualLogPublisher.publishEvent(methodName, className, LIMIT_MESSAGE, 0L);
				throw new ServiceException(LIMIT_MESSAGE);
			}
		}
		//发送异步日志事件
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//记录日志
		VisualLogPublisher.publishEvent(methodName, className, dataLog.value(), time);
		return result;
	}

}
