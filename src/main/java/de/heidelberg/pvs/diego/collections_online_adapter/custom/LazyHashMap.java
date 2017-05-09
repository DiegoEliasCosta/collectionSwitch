package de.heidelberg.pvs.diego.collections_online_adapter.custom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LazyHashMap<K, V> implements Map<K, V> {
	
	Map<K, V> hashMap;
	
	private void checkAndLazilyCreate() {
		if(hashMap == null) {
			hashMap = new HashMap<K, V>();
		}
	}

	public void clear() {
		checkAndLazilyCreate();
		hashMap.clear();
	}

	public boolean containsKey(Object key) {
		checkAndLazilyCreate();
		return hashMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		checkAndLazilyCreate();
		return hashMap.containsValue(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		checkAndLazilyCreate();
		return hashMap.entrySet();
	}

	public boolean equals(Object o) {
		checkAndLazilyCreate();
		return hashMap.equals(o);
	}

	public V get(Object key) {
		checkAndLazilyCreate();
		return hashMap.get(key);
	}

	public int hashCode() {
		checkAndLazilyCreate();
		return hashMap.hashCode();
	}

	public boolean isEmpty() {
		checkAndLazilyCreate();
		return hashMap.isEmpty();
	}

	public Set<K> keySet() {
		checkAndLazilyCreate();
		return hashMap.keySet();
	}

	public V put(K key, V value) {
		checkAndLazilyCreate();
		return hashMap.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		checkAndLazilyCreate();
		hashMap.putAll(m);
	}

	public V remove(Object key) {
		checkAndLazilyCreate();
		return hashMap.remove(key);
	}

	public int size() {
		checkAndLazilyCreate();
		return hashMap.size();
	}

	public Collection<V> values() {
		checkAndLazilyCreate();
		return hashMap.values();
	}

		
	
}
