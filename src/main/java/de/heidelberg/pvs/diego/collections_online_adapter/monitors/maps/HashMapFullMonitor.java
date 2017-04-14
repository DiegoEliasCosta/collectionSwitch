package de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class HashMapFullMonitor<K, V> extends HashMap<K, V> implements Map<K, V> {

	private static final long serialVersionUID = 20170101;

	private MapAllocationOptimizer context;

	private int containsOp;

	private int iterateOp;

	private int index;

	public HashMapFullMonitor(int initialCapacity, float loadFactor, MapAllocationOptimizer context, int index) {
		super(initialCapacity);
		this.context = context;
		this.index = index;
	}

	public HashMapFullMonitor(Map<? extends K, ? extends V> map, MapAllocationOptimizer context, int index) {
		super(map);
		this.context = context;
		this.index = index;
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.context.updateOperationsAndSize(index, containsOp, iterateOp, size());
		super.finalize();
	}

	// CONTAINS
	public boolean containsKey(Object key) {
		containsOp++;
		return super.containsKey(key);
	}

	public boolean containsValue(Object value) {
		containsOp++;
		return super.containsValue(value);
	}

	public V get(Object key) {
		containsOp++;
		return super.get(key);
	}

	// ITERATE
	public Set<K> keySet() {
		iterateOp++;
		return super.keySet();
	}

	public Collection<V> values() {
		iterateOp++;
		return super.values();
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		iterateOp++;
		return super.entrySet();
	}

}
