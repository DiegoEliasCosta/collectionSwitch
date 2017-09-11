package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class EmpiricalMapAllocationContext implements MapAllocationContextInfo {

	private int analyzedInitialCapacity = 1 << 4;

	private int windowSize;
	private int instancesCount;

	private MapAllocationOptimizer optimizer;

	private MapCollectionType type;
	
	public EmpiricalMapAllocationContext(MapCollectionType type, MapAllocationOptimizer optimizer, int windowSize) {
		super();
		this.type = type;
		this.optimizer = optimizer;
		this.windowSize = windowSize;
		this.instancesCount = 0;
	}
	

	@Override
	public void updateCollectionInitialCapacity(int size) {
		this.analyzedInitialCapacity = size;
		instancesCount = 0; // reset

	}

	@Override
	public void updateCollectionType(MapCollectionType type) {
		this.type = type;

	}

	@Override
	public <K, V> Map<K, V> createMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentCollectionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAnalyzedInitialCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
