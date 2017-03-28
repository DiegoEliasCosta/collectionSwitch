package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;

public class MapFullMonitor<K, V> implements Map<K, V> {
	
	private Map<K, V> map;
	
	private MapAllocationContext<K, V> context;

	private int containsOp = 0;
	private int iterateOp = 0;

	public MapFullMonitor(Map<K, V> map, MapAllocationContext<K, V> context) {
		super();
		this.map = map;
		this.context = context;
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.context.updateOperationsAndSize(containsOp, iterateOp, size());
		super.finalize();
	}
	
	// CONTAINS
	public boolean containsKey(Object key) {
		containsOp ++;
		return map.containsKey(key);
	}
	
	public boolean containsValue(Object value) {
		containsOp++;
		return map.containsValue(value);
	}
	
	public V get(Object key) {
		containsOp++;
		return map.get(key);
	}
	
	// ITERATE
	public Set<K> keySet() {
		iterateOp++;
		return map.keySet();
	}

	public Collection<V> values() {
		iterateOp++;
		return map.values();
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		iterateOp ++;
		return map.entrySet();
	}

	// -------------------------------------------------------------------
	// ------------------------- DELEGATE METHODS ------------------------
	// -------------------------------------------------------------------
	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}


	public V put(K key, V value) {
		return map.put(key, value);
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



	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}


}
