/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2024, https://bladex.cn. All rights reserved.
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
package org.springblade.modules.visual.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.modules.visual.factory.enums.MessageType;
import org.springblade.modules.visual.mapper.VisualPushChannelMapper;
import org.springblade.modules.visual.pojo.dto.VisualPushChannelDTO;
import org.springblade.modules.visual.pojo.entity.VisualPushChannel;
import org.springblade.modules.visual.service.IVisualPushChannelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springblade.modules.visual.cache.PushCache.CHANNEL_ID;
import static org.springblade.modules.visual.cache.PushCache.PUSH_CACHE;


/**
 * 消息推送渠道表 服务实现类
 *
 * @author Blade
 */
@Service
public class VisualPushChannelServiceImpl extends ServiceImpl<VisualPushChannelMapper, VisualPushChannel> implements IVisualPushChannelService {

	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public boolean submit(VisualPushChannel pushChannel) {
		if (isFieldDuplicate(VisualPushChannel::getPushName, pushChannel.getPushName(), pushChannel.getId())) {
			throw new ServiceException("[渠道名称] 不可重复");
		}
		VisualPushChannelDTO param = new VisualPushChannelDTO();
		param.setPushName(pushChannel.getPushName());
		param.setPushType(pushChannel.getPushType());
		if (pushChannel.getPushType() == MessageType.WECHAT_WORK.getType()) {
			param.setTypeName(MessageType.WECHAT_WORK.getName());
			param.setWebhook(pushChannel.getWebhook());
		} else if (pushChannel.getPushType() == MessageType.DING_TALK.getType()) {
			param.setTypeName(MessageType.DING_TALK.getName());
			param.setWebhook(pushChannel.getWebhook());
			param.setRobotSign(pushChannel.getRobotSign());
		} else if (pushChannel.getPushType() == MessageType.EMAIL.getType()) {
			param.setTypeName(MessageType.EMAIL.getName());
			param.setSenderEmail(pushChannel.getSenderEmail());
			param.setEmailHost(pushChannel.getEmailHost());
			param.setEmailPort(pushChannel.getEmailPort());
			param.setUsername(pushChannel.getUsername());
			param.setPassword(pushChannel.getPassword());
			param.setRecipientEmail(pushChannel.getRecipientEmail());
		} else if (pushChannel.getPushType() == MessageType.ALI_SMS.getType()) {
			param.setTypeName(MessageType.ALI_SMS.getName());
			param.setAccessKey(pushChannel.getAccessKey());
			param.setSecretKey(pushChannel.getSecretKey());
			param.setSmsSign(pushChannel.getSmsSign());
		} else if (pushChannel.getPushType() == MessageType.TENCENT_SMS.getType()) {
			param.setTypeName(MessageType.TENCENT_SMS.getName());
			param.setAppId(pushChannel.getAppId());
			param.setAppKey(pushChannel.getAppKey());
			param.setSmsSign(pushChannel.getSmsSign());
		}
		pushChannel.setPushParam(JsonUtil.toJson(param));
		pushChannel.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		CacheUtil.evict(PUSH_CACHE, CHANNEL_ID, pushChannel.getId());
		return this.saveOrUpdate(pushChannel);
	}

	public boolean isFieldDuplicate(SFunction<VisualPushChannel, ?> field, Object value, Long excludedId) {
		LambdaQueryWrapper<VisualPushChannel> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(field, value);
		if (excludedId != null) {
			queryWrapper.ne(VisualPushChannel::getId, excludedId);
		}
		return baseMapper.selectCount(queryWrapper) > 0;
	}
}
