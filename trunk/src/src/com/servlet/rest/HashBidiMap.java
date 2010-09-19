package com.servlet.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ����һ��Mapʵ�֣�����ͨ����(key)�õ�ֵ(value);����ͨ��ֵ(value)�õ���ֵ��(values)����
 * ��Ϊ������Ҫһ�����Ե���ת��Map����Ԫ�أ�Apache Commons collections �� Google Collections
 * ���ṩ��BidiMapʵ�֣����޷�������ת������һ�Զ��Map
 * Ϊ��ʹ���������ܵ��٣�ֻ���ع�һ�����̳�HashMap����������ƽ�����÷��������һ��reverse���������з�ת.
 * �е�������ģʽ��ζ�����̳�ֻ�ã����������µ�ְ��
 * 
 * @author yong
 * @date 2010-9-19
 * @version 1.0
 */
public class HashBidiMap<K, V> implements Map<K, V> {
	private Map<K, V> map = null;
	private Map<V, K> bidiMap = null;

	public HashBidiMap() {
		map = new HashMap<K, V>();

		bidiMap = new HashMap<V, K>();
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public V get(Object key) {
		return map.get(key);
	}

	public V put(K key, V value) {
		bidiMap.put(value, key);

		return map.put(key, value);
	}

	public V remove(Object key) {
		V value = map.remove(key);

		bidiMap.remove(value);

		return value;
	}

	public void removeValue(Object value) {
		K key = bidiMap.remove(value);

		map.remove(key);
	}

	public void removeValueByClassPath(String valueClassPath) {
		Iterator<Entry<V, K>> iter = bidiMap.entrySet().iterator();

		while (iter.hasNext()) {
			Entry<V, K> entry = iter.next();

			if (entry.getKey().getClass().getName().equals(valueClassPath)) {
				iter.remove();

				map.remove(entry.getValue());
				
				continue;
			}
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		if (m == null || m.isEmpty()) {
			return;
		}

		for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m
				.entrySet().iterator(); i.hasNext();) {
			Map.Entry<? extends K, ? extends V> e = i.next();
			this.put(e.getKey(), e.getValue());
		}
	}

	public void clear() {
		map.clear();

		bidiMap.clear();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<V> values() {
		return map.values();
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	public Map<V, K> reverse() {
		return bidiMap;
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode() + bidiMap.hashCode();
	}
}