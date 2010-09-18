package com.yong.rest;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;

/**
 *	����һ��Mapʵ�֣�����ͨ����(key)�õ�ֵ(value);����ͨ��ֵ(value)�õ���ֵ��(values)����
 *	��Ϊ������Ҫһ�����Ե���ת��Map����Ԫ�أ�Apache Commons collections �� Google Collections ���ṩ��BidiMapʵ�֣����޷�������ת������һ�Զ��Map
 *  Ϊ��ʹ���������ܵ��٣�ֻ���ع�һ�����̳�HashMap����������ƽ�����÷��������һ��reverse���������з�ת.
 *	�е�װ����ģʽ��ζ�����̳�ֻ�ã����������µ�ְ��
 **/
public class DualHashBidiMap<K,V> implements Map<K,V> {
	private Map<K,V> map = null;
	private Map<V,Set<K>> bidiMap = null;

	public DualHashBidiMap(){
		map = new HashMap<K,V>();

		bidiMap = new HashMap<V,Set<K>>();
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

    public boolean containsValue(Object value){
    	return map.containsValue(value);
    }

	public V get(Object key) {
		return map.get(key);
	}

	public V put(K key, V value) {
		Set<K> coll = bidiMap.get(value);
        if (coll == null) {
            coll = createCollection(null);
            bidiMap.put(value, coll);
        }
        coll.add(key);

		return map.put(key, value);
	}

	public V remove(Object key) {
		V value = map.remove(key);

		bidiMap.remove(value);

        return value;
    }

	public void putAll(Map<? extends K, ? extends V> m){
		if(m == null || m.isEmpty()){
			return;
		}

		for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i.hasNext(); ) {
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

	public Set<Map.Entry<K,V>> entrySet() {
		return map.entrySet();
	}

	public Map<V,Set<K>> reverse(){
		return bidiMap;
	}

	private Set<K> createCollection(Set<K> coll) {
        if (coll == null) {
            return new HashSet<K>();
        } else {
            return new HashSet<K>(coll);
        }
    }

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode() + bidiMap.hashCode();
	}
}