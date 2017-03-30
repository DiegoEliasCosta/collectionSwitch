package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.MapFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.RuleBasedMapOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class AdaptiveMapAllocationContext extends AbstractAdaptiveAllocationContext implements MapAllocationContext {

	private MapAllocationOptimizer optimizer;

	public AdaptiveMapAllocationContext(CollectionTypeEnum collectionType, int windowSize, int fullAnalysisThreshold,
			int sleepingFrequency, int convergencyRate, int divergentRoundsThreshold) {
		super(collectionType, windowSize, fullAnalysisThreshold, sleepingFrequency, convergencyRate, divergentRoundsThreshold);

		this.optimizer = new RuleBasedMapOptimizer(this, windowSize, convergencyRate);
	}

	public <K, V> Map<K, V> createMap() {
		return this.createMap(this.analyzedInitialCapacity);

	}

	public <K, V> Map<K, V> createMap(int initialCapacity) {

		switch (state) {

		case INACTIVE:
			return MapFactory.createNormalMap(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return MapFactory.createNormalMap(championCollectionType, initialCapacity);

		case SLEEPING_MEMORY:

			if (shouldMonitor()) {
				return MapFactory.createSizeMonitor(championCollectionType, optimizer, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case SLEEPING_FULL:

			if (shouldMonitor()) {
				return MapFactory.createFullMonitor(championCollectionType, optimizer, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case ACTIVE_MEMORY:
			return MapFactory.createSizeMonitor(defaultCollectionType, optimizer, initialCapacity);

		case ACTIVE_FULL:
			return MapFactory.createFullMonitor(defaultCollectionType, optimizer, initialCapacity);

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
				return MapFactory.createSizeMonitor(championCollectionType, optimizer, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case SLEEPING_FULL:

			if (shouldMonitor()) {
				return MapFactory.createFullMonitor(championCollectionType, optimizer, initialCapacity);
			} else {
				return MapFactory.createNormalMap(championCollectionType, initialCapacity);
			}

		case ACTIVE_MEMORY:
			return MapFactory.createSizeMonitor(defaultCollectionType, optimizer, initialCapacity);

		case ACTIVE_FULL:
			return MapFactory.createFullMonitor(defaultCollectionType, optimizer, initialCapacity);

		}

		return null;
	}
	

}
