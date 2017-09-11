package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.HashMap;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class InitialCapacityMapAllocationContext implements MapAllocationContextInfo {

	private int analyzedInitialCapacity = 1 << 4;
	
	private int windowSize;
	private int instancesCount;

	private MapAllocationOptimizer optimizer;
	
	public InitialCapacityMapAllocationContext(MapAllocationOptimizer optimizer, int windowSize) {
		super();
		this.optimizer = optimizer;
		this.windowSize = windowSize;
	}

	@Override
	public void updateCollectionInitialCapacity(int size) {
		analyzedInitialCapacity = size;
	}

	@Override
	public <K, V> Map<K, V> createMap() {
		
		if(instancesCount++ < windowSize) {
			return optimizer.createMonitor(new HashMap<K, V>(analyzedInitialCapacity));
		}
		
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

	@Override
	public String getCurrentCollectionType() {
		return "Hash";
	}

	@Override
	public int getAnalyzedInitialCapacity() {
		return analyzedInitialCapacity;
	}

	@Override
	public void updateCollectionType(MapCollectionType type) {
		// TODO Auto-generated method stub
		
	}

}
