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
package org.springblade.core.tool.node;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springblade.core.tool.utils.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 森林管理类
 *
 * @author smallchill
 */
public class ForestNodeManager<T extends INode<T>> {

	/**
	 * 森林的所有节点
	 */
	private final ImmutableMap<Long, T> nodeMap;

	/**
	 * 森林的父节点ID
	 */
	private final Map<Long, Object> parentIdMap = Maps.newHashMap();

	public ForestNodeManager(List<T> nodes) {
		nodeMap = Maps.uniqueIndex(nodes, INode::getId);
	}

	/**
	 * 根据节点ID获取一个节点
	 *
	 * @param id 节点ID
	 * @return 对应的节点对象
	 */
	public INode<T> getTreeNodeAt(Long id) {
		if (nodeMap.containsKey(id)) {
			return nodeMap.get(id);
		}
		return null;
	}

	/**
	 * 增加父节点ID
	 *
	 * @param parentId 父节点ID
	 */
	public void addParentId(Long parentId) {
		parentIdMap.put(parentId, StringPool.EMPTY);
	}

	/**
	 * 获取树的根节点(一个森林对应多颗树)
	 *
	 * @return 树的根节点集合
	 */
	public List<T> getRoot() {
		List<T> roots = new ArrayList<>();
		nodeMap.forEach((key, node) -> {
			if (node.getParentId() == 0 || parentIdMap.containsKey(node.getId())) {
				roots.add(node);
			}
		});
		return roots;
	}

}
