package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.SetsFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class AdaptiveSetAllocationContext extends AbstractAdaptiveAllocationContext implements SetAllocationContext {

	private SetAllocationOptimizer optimizer;

	public AdaptiveSetAllocationContext(CollectionTypeEnum collectionType, SetAllocationOptimizer optimizer, int windowSize, int fullAnalysisThreshold,
			int sleepingFrequency, int convergencyRate, int divergentRoundsThreshold) {
		super(collectionType, windowSize, fullAnalysisThreshold, sleepingFrequency, convergencyRate, divergentRoundsThreshold);

		this.optimizer = optimizer;
		
	}

	public <E> Set<E> createSet() {
		return this.createSet(this.analyzedInitialCapacity);
	}

	public <E> Set<E> createSet(int initialCapacity) {

		switch (state) {

		case INACTIVE:
			return SetsFactory.createNormalSet(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return SetsFactory.createNormalSet(championCollectionType, initialCapacity);

		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			if (shouldMonitor())
				return SetsFactory.createSizeMonitor(championCollectionType, optimizer, initialCapacity);
			else
				return SetsFactory.createNormalSet(championCollectionType, initialCapacity);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			if (shouldMonitor())
				return SetsFactory.createFullMonitor(championCollectionType, optimizer, initialCapacity);
			else
				return SetsFactory.createNormalSet(championCollectionType, initialCapacity);

		case ACTIVE_MEMORY:
			return SetsFactory.createSizeMonitor(defaultCollectionType, optimizer, initialCapacity);

		case ACTIVE_FULL:
			return SetsFactory.createFullMonitor(defaultCollectionType, optimizer, initialCapacity);
		}
		return null;

	}

	public <E> Set<E> createSet(Collection<? extends E> set) {

		switch (state) {

		case INACTIVE:
			return SetsFactory.createNormalSet(defaultCollectionType, set);

		case OPTIMIZED:
			return SetsFactory.createNormalSet(championCollectionType, set);

		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			if (shouldMonitor())
				return SetsFactory.createSizeMonitor(championCollectionType, optimizer, set);
			else
				return SetsFactory.createNormalSet(championCollectionType, set);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			if (shouldMonitor())
				return SetsFactory.createFullMonitor(championCollectionType, optimizer, set);
			else
				return SetsFactory.createNormalSet(championCollectionType, set);

		case ACTIVE_MEMORY:
			return SetsFactory.createSizeMonitor(defaultCollectionType, optimizer, set);

		case ACTIVE_FULL:
			return SetsFactory.createFullMonitor(defaultCollectionType, optimizer, set);
		}
		return null;
	}

}
