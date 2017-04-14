package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.MapFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class AdaptiveMapAllocationContext extends AbstractAdaptiveAllocationContext implements MapAllocationContext {

	private static final float LOADFACTOR_GAP = 1.3f;

	private MapAllocationOptimizer optimizer;
	
	private int practicalInitialCapacity;
	
	public AdaptiveMapAllocationContext(CollectionTypeEnum collectionType, MapAllocationOptimizer optimizer, int windowSize, int fullAnalysisThreshold,
			int sleepingFrequency, int convergencyRate, int divergentRoundsThreshold) {
		super(collectionType, windowSize, fullAnalysisThreshold, sleepingFrequency, convergencyRate, divergentRoundsThreshold);

		this.optimizer = optimizer;
	}
	

	public <K, V> Map<K, V> createMap() {
		return this.createMap(this.practicalInitialCapacity, this.analyzedLoadFactor);

	}
	
	@Override
	public void optimizeCollectionType(CollectionTypeEnum collection, int mode, int medianInitialCapacity) {
		super.optimizeCollectionType(collection, mode, medianInitialCapacity);
		this.practicalInitialCapacity = (int) (this.analyzedInitialCapacity * LOADFACTOR_GAP);
	}
	
	@Override
	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		super.noCollectionTypeConvergence(mode, medianInitialCapacity);
		this.practicalInitialCapacity = (int) (this.analyzedInitialCapacity * LOADFACTOR_GAP);
	}
	
	
	
	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		return this.createMap(initialCapacity, this.analyzedLoadFactor);
	}


	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {

		switch (state) {

		case INACTIVE:
			return MapFactory.createNormalMap(defaultCollectionType, initialCapacity, loadFactor);

		case OPTIMIZED:
			return MapFactory.createNormalMap(championCollectionType, initialCapacity, loadFactor);

		case SLEEPING_MEMORY:

			if (shouldMonitor()) {
				return MapFactory.createSizeMonitor(championCollectionType, optimizer, initialCapacity, loadFactor);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity, loadFactor);
			}

		case SLEEPING_FULL:

			if (shouldMonitor()) {
				return MapFactory.createFullMonitor(championCollectionType, optimizer, initialCapacity, loadFactor);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity, loadFactor);
			}

		case ACTIVE_MEMORY:
			return MapFactory.createSizeMonitor(defaultCollectionType, optimizer, initialCapacity, loadFactor);

		case ACTIVE_FULL:
			return MapFactory.createFullMonitor(defaultCollectionType, optimizer, initialCapacity, loadFactor);

		}

		return null;
	}

	public <K, V> Map<K, V> createMap(Map<K, V> map) {
		switch (state) {

		case INACTIVE:
			return MapFactory.createNormalMap(defaultCollectionType, map);

		case OPTIMIZED:
			return MapFactory.createNormalMap(championCollectionType, map);

		case SLEEPING_MEMORY:

			if (shouldMonitor()) {
				return MapFactory.createSizeMonitor(championCollectionType, optimizer, map);
			} else {
				return MapFactory.createNormalMap(championCollectionType, map);
			}

		case SLEEPING_FULL:

			if (shouldMonitor()) {
				return MapFactory.createFullMonitor(championCollectionType, optimizer, map);
			} else {
				return MapFactory.createNormalMap(championCollectionType, map);
			}

		case ACTIVE_MEMORY:
			return MapFactory.createSizeMonitor(defaultCollectionType, optimizer, map);

		case ACTIVE_FULL:
			return MapFactory.createFullMonitor(defaultCollectionType, optimizer, map);

		}

		return null;
	}


}
