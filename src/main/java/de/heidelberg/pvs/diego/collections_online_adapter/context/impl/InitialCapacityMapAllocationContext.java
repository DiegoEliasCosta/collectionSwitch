package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.HashMap;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;

public class InitialCapacityMapAllocationContext implements MapAllocationContext {

	private int analyzedInitialCapacity = 1 << 4;
	
	public InitialCapacityMapAllocationContext() {
		super();
	}

	@Override
	public void updateCollectionInitialCapacity(int size) {
		analyzedInitialCapacity = size;
	}

	@Override
	public <K, V> Map<K, V> createMap() {
		return new HashMap<K, V>(analyzedInitialCapacity);
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		return new HashMap<K, V>(initialCapacity);
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {
		return new HashMap<K, V>(initialCapacity, loadFactor);
	}

	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> map) {
		return new HashMap<K, V>(map);
	}

}
