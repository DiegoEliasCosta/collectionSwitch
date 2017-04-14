package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.MapFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class ProactiveMapAllocationContext extends AbstractAdaptiveAllocationContext implements MapAllocationContext {

	private static final float LOADFACTOR_GAP = 1.3f;
	private MapAllocationOptimizer optimizer;
	private int practicalInitialCapacity;

	public ProactiveMapAllocationContext(CollectionTypeEnum collectionType, MapAllocationOptimizer optimizer, int windowSize, int fullAnalysisThreshold,
			int sleepingFrequency, int convergencyRate, int divergentRoundsThreshold) {
		super(collectionType, windowSize, fullAnalysisThreshold, sleepingFrequency, convergencyRate, divergentRoundsThreshold);

		this.optimizer = optimizer;
		practicalInitialCapacity = (int) (this.analyzedInitialCapacity * LOADFACTOR_GAP);
	}

	public <K, V> Map<K, V> createMap() {
		return this.createMap(this.practicalInitialCapacity);
		
	}
	
	
	@Override
	public void optimizeCollectionType(CollectionTypeEnum collection, int mode, int medianInitialCapacity) {
		super.optimizeCollectionType(collection, mode, medianInitialCapacity);
		practicalInitialCapacity = (int) (this.analyzedInitialCapacity * LOADFACTOR_GAP);
	}
	
	@Override
	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		super.noCollectionTypeConvergence(mode, medianInitialCapacity);
		practicalInitialCapacity = (int) (this.analyzedInitialCapacity * LOADFACTOR_GAP);
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		return this.createMap(initialCapacity, analyzedLoadFactor);
	}

	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {

		switch (state) {

		case INACTIVE:
			return MapFactory.createNormalMap(defaultCollectionType, initialCapacity, loadFactor);

		case OPTIMIZED:
			return MapFactory.createNormalMap(championCollectionType, initialCapacity, loadFactor);

		case SLEEPING_MEMORY:

			if (shouldMonitor()) {
				return MapFactory.createProactiveSizeMonitor(championCollectionType, optimizer, initialCapacity, loadFactor);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity, loadFactor);
			}

		case SLEEPING_FULL:

			if (shouldMonitor()) {
				return MapFactory.createProactiveFullMonitor(championCollectionType, optimizer, initialCapacity, loadFactor);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity, loadFactor);
			}

		case ACTIVE_MEMORY:
			return MapFactory.createProactiveSizeMonitor(defaultCollectionType, optimizer, initialCapacity, loadFactor);

		case ACTIVE_FULL:
			return MapFactory.createProactiveFullMonitor(defaultCollectionType, optimizer, initialCapacity, loadFactor);

		}

		return null;
	}

	public <K, V> Map<K, V> createMap(Map<K, V> initialCapacity) {
		switch (state) {

		case INACTIVE:
			return MapFactory.createNormalMap(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return MapFactory.createNormalMap(championCollectionType, initialCapacity);

		case SLEEPING_MEMORY:

			if (shouldMonitor()) {
				return MapFactory.createProactiveSizeMonitor(championCollectionType, optimizer, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case SLEEPING_FULL:

			if (shouldMonitor()) {
				return MapFactory.createProactiveFullMonitor(championCollectionType, optimizer, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case ACTIVE_MEMORY:
			return MapFactory.createProactiveSizeMonitor(defaultCollectionType, optimizer, initialCapacity);

		case ACTIVE_FULL:
			return MapFactory.createProactiveFullMonitor(defaultCollectionType, optimizer, initialCapacity);

		}

		return null;
	}


}
