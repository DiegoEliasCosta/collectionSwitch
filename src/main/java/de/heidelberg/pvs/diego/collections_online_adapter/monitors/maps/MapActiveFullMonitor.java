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
		this.state.updateContainsOp(1);
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
	
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		state.updateIteration(1);
		return map.entrySet();
	}
	
	public void putAll(Map<? extends K, ? extends V> m) {
		state.updateSize(m.size());
		map.putAll(m);
	}

	public Set<K> keySet() {
		state.updateIteration(1);
		return map.keySet();
	}

	public Collection<V> values() {
		state.updateIteration(1);
		return map.values();
	}
	
	public V remove(Object key) {
		state.updateSize(-1);
		return map.remove(key);
	}
	
	// --------------------------
	
	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public void clear() {
		state.updateSize(-map.size());
		map.clear();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}

}
