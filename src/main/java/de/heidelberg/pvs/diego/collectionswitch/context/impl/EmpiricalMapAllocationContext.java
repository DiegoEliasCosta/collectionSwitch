package de.heidelberg.pvs.diego.collectionswitch.context.impl;

import java.util.Map;

import de.heidelberg.pvs.diego.collectionswitch.context.MapAllocationContextInfo;
import de.heidelberg.pvs.diego.collectionswitch.context.MapCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.maps.MapAllocationOptimizer;

public class EmpiricalMapAllocationContext implements MapAllocationContextInfo {

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
	public void updateCollectionType(MapCollectionType type) {
		this.type = type;
		instancesCount = 0; // reset

	}

	@Override
	public <K, V> Map<K, V> createMap() {
		
		Map<K, V> map = type.createMap();

		if (instancesCount++ < windowSize) {
			return this.optimizer.createMonitor(map);
		}

		return map;
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {

		Map<K, V> map = type.createMap(initialCapacity);

		if (instancesCount++ < windowSize) {
			return this.optimizer.createMonitor(map);
		}

		return map;
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {
		Map<K, V> map = type.createMap(initialCapacity);

		if (instancesCount++ < windowSize) {
			return this.optimizer.createMonitor(map);
		}

		return map;
	}

	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> mapToCopy) {
		Map<K, V> map = type.createMap(mapToCopy);

		if (instancesCount++ < windowSize) {
			return this.optimizer.createMonitor(map);
		}

		return map;
	}

	@Override
	public MapCollectionType getCurrentCollectionType() {
		return type;
	}

}
