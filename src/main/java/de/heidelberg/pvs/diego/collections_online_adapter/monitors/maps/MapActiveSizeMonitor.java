package de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class MapActiveSizeMonitor<K, V> implements Map<K, V> {
	
	private Map<K, V> map;
	
	private AllocationOptimizer context;

	private final int index;

	public MapActiveSizeMonitor(Map<K, V> map, AllocationOptimizer context, int index) {
		super();
		this.map = map;
		this.context = context;
		this.index = index;
		this.context.updateSize(index, size());
	}
	
	// PUT
	public V put(K key, V value) {
		V put = map.put(key, value);
		this.context.updateSize(index, size());
		return put;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
		this.context.updateSize(index, size());
	}
	
	// -------------------------------------------------------------------
	// ------------------------- DELEGATE METHODS ------------------------
	// -------------------------------------------------------------------
	
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	
	public V get(Object key) {
		return map.get(key);
	}
	
	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<V> values() {
		return map.values();
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public V remove(Object key) {
		return map.remove(key);
	}


	public void clear() {
		map.clear();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}


}
