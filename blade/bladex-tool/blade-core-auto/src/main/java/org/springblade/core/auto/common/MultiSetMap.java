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
package org.springblade.core.auto.common;

import java.util.*;

/**
 * MultiSetMap
 *
 * @author L.cm
 */
public class MultiSetMap<K, V> {
	private transient final Map<K, Set<V>> map;

	public MultiSetMap() {
		map = new HashMap<>();
	}

	private Set<V> createSet() {
		return new HashSet<>();
	}

	/**
	 * put to MultiSetMap
	 *
	 * @param key   键
	 * @param value 值
	 * @return boolean
	 */
	public boolean put(K key, V value) {
		Set<V> set = map.get(key);
		if (set == null) {
			set = createSet();
			if (set.add(value)) {
				map.put(key, set);
				return true;
			} else {
				throw new AssertionError("New set violated the set spec");
			}
		} else if (set.add(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否包含某个key
	 *
	 * @param key key
	 * @return 结果
	 */
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	/**
	 * 是否包含 value 中的某个值
	 *
	 * @param value value
	 * @return 是否包含
	 */
	public boolean containsVal(V value) {
		Collection<Set<V>> values = map.values();
		return values.stream().anyMatch(vs -> vs.contains(value));
	}

	/**
	 * key 集合
	 *
	 * @return keys
	 */
	public Set<K> keySet() {
		return map.keySet();
	}

	/**
	 * put list to MultiSetMap
	 *
	 * @param key 键
	 * @param set 值列表
	 * @return boolean
	 */
	public boolean putAll(K key, Set<V> set) {
		if (set == null) {
			return false;
		} else {
			map.put(key, set);
			return true;
		}
	}

	/**
	 * put MultiSetMap to MultiSetMap
	 *
	 * @param data MultiSetMap
	 * @return boolean
	 */
	public boolean putAll(MultiSetMap<K, V> data) {
		if (data == null || data.isEmpty()) {
			return false;
		} else {
			for (K k : data.keySet()) {
				this.putAll(k, data.get(k));
			}
			return true;
		}
	}

	/**
	 * get List by key
	 *
	 * @param key 键
	 * @return List
	 */
	public Set<V> get(K key) {
		return map.get(key);
	}

	/**
	 * clear MultiSetMap
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * isEmpty
	 *
	 * @return isEmpty
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}
}
