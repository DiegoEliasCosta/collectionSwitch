package de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class MapActiveFullMonitor<K, V> implements Map<K, V> {
	
	private Map<K, V> map;
	private MapMetrics state;
	
	public MapActiveFullMonitor(Map<K, V> map, MapMetrics state) {
		super();
		this.map = map;
		this.state = state;
		this.state.updateSize(map.size()); // first record
	}
	
	public boolean containsKey(Object key) {
		this.state.updateContainsOp(1);
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		this.state.updateIteration(1);
		return map.containsValue(value);
	}
	
	public V get(Object key) {
		this.state.updateContainsOp(1);
		return map.get(key);
	}

	public V put(K key, V value) {
		this.state.updateSize(1);
		return map.put(key, value);
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

	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}

	public void clear() {
		map.clear();
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

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}

}
