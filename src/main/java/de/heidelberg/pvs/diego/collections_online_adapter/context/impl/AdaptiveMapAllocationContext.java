package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.MapFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.AdaptiveMapOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class AdaptiveMapAllocationContext<K, V> implements MapAllocationContext<K, V> {

	private static final int FULL_ANALYSIS_THRESHOLD = 100;
	private static final int SLEEPING_FREQ = 50;

	private MapAllocationOptimizer<K, V> optimizer;
	private AllocationContextState state;

	private CollectionTypeEnum championCollectionType;
	private CollectionTypeEnum defaultCollectionType;

	private int analyzedInitialCapacity;
	private int sleepingMonitoringCount;

	public AdaptiveMapAllocationContext(CollectionTypeEnum collectionType) {
		super();

		this.defaultCollectionType = collectionType;
		this.championCollectionType = collectionType;
		this.optimizer = new AdaptiveMapOptimizer<>(this);

		// First State
		this.state = AllocationContextState.ACTIVE_MEMORY;
		this.analyzedInitialCapacity = 10;
		this.sleepingMonitoringCount = 0;
	}

	public Map<K, V> createMap() {
		return this.createMap(this.analyzedInitialCapacity);

	}

	public Map<K, V> createMap(int initialCapacity) {

		switch (state) {

		case INACTIVE:
			return MapFactory.createNormalMap(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return MapFactory.createNormalMap(championCollectionType, initialCapacity);

		case SLEEPING_MEMORY:

			if (shouldMonitor()) {
				return MapFactory.createSizeMonitor(championCollectionType, this, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case SLEEPING_FULL:

			if (shouldMonitor()) {
				return MapFactory.createFullMonitor(championCollectionType, this, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case ACTIVE_MEMORY:
			return MapFactory.createSizeMonitor(defaultCollectionType, this, initialCapacity);

		case ACTIVE_FULL:
			return MapFactory.createFullMonitor(defaultCollectionType, this, initialCapacity);

		}

		return null;
	}

	private boolean shouldMonitor() {
		// FIXME: This is NOT thread-safe
		return sleepingMonitoringCount++ % SLEEPING_FREQ == 0;
	}

	public Map<K, V> createMap(Map<K, V> initialCapacity) {
		switch (state) {

		case INACTIVE:
			return MapFactory.createNormalMap(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return MapFactory.createNormalMap(championCollectionType, initialCapacity);

		case SLEEPING_MEMORY:

			if (shouldMonitor()) {
				return MapFactory.createSizeMonitor(championCollectionType, this, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case SLEEPING_FULL:

			if (shouldMonitor()) {
				return MapFactory.createFullMonitor(championCollectionType, this, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case ACTIVE_MEMORY:
			return MapFactory.createSizeMonitor(defaultCollectionType, this, initialCapacity);

		case ACTIVE_FULL:
			return MapFactory.createFullMonitor(defaultCollectionType, this, initialCapacity);

		}

		return null;
	}

	@Override
	public void updateSize(int size) {
		this.optimizer.updateSize(size);
	}

	@Override
	public void updateOperationsAndSize(int containsOp, int iterationOp, int size) {
		this.optimizer.updateOperationsAndSize(containsOp, iterationOp, size);
	}

	@Override
	public void optimizeInitialCapacity(int analyzedInitialCapacity) {
		this.analyzedInitialCapacity = analyzedInitialCapacity;
		
		if(this.analyzedInitialCapacity > FULL_ANALYSIS_THRESHOLD) {
			this.state = AllocationContextState.ACTIVE_FULL;
		} else {
			this.state = AllocationContextState.SLEEPING_MEMORY;
		}
	

	}

	@Override
	public void noInitialCapacityConvergence() {
		// TODO Auto-generated method stub

	}

	@Override
	public void optimizeCollectionType(CollectionTypeEnum collecton) {
		this.championCollectionType = collecton;

	}

	@Override
	public void noCollectionTypeConvergence() {
		// TODO Auto-generated method stub

	}

}
