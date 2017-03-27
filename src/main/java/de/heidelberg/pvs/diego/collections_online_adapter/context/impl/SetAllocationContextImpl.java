package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.SetsFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.AdaptiveSetMemoryOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class SetAllocationContextImpl<E> implements SetAllocationContext<E> {

	private static final int SLEEPING_FREQ = 50;

	private SetAllocationOptimizer<E> optimizer;

	private AllocationContextState state;

	private CollectionTypeEnum championCollectionType;
	private CollectionTypeEnum defaultCollectionType;

	private int sleepingMonitoringCount;

	private int analyzedInitialCapacity;

	public SetAllocationContextImpl(CollectionTypeEnum collectionType) {
		super();
		this.championCollectionType = collectionType;
		this.optimizer = new AdaptiveSetMemoryOptimizer<>(this);
	}

	public Set<E> createSet() {

		return this.createSet(this.analyzedInitialCapacity);
	}


	public void updateSize(int size) {
		optimizer.updateSize(size);
	}

	public void updateOperationsAndSize(int iterations, int contains, int size) {
		optimizer.updateOperationsAndSize(iterations, contains, size);
	}

	public Set<E> createSet(int initialCapacity) {
		
		int n_alloc;
		switch (state) {

		case INACTIVE:
			return SetsFactory.createNormalSet(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return SetsFactory.createNormalSet(championCollectionType, initialCapacity);
			
		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % SLEEPING_FREQ == 0)
				return SetsFactory.createSizeMonitor(championCollectionType, this, initialCapacity);
			else
				return SetsFactory.createNormalSet(championCollectionType, initialCapacity);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % SLEEPING_FREQ == 0)
				return SetsFactory.createFullMonitor(championCollectionType, this, initialCapacity);
			else
				return SetsFactory.createNormalSet(championCollectionType, initialCapacity);

		case ACTIVE_MEMORY:
			return SetsFactory.createSizeMonitor(defaultCollectionType, this, initialCapacity);

		case ACTIVE_FULL:
			return SetsFactory.createFullMonitor(defaultCollectionType, this, initialCapacity);
		}
		return null;
			
	}

	public Set<E> createSet(Collection<? extends E> set) {
		
		int n_alloc;
		switch (state) {

		case INACTIVE:
			return SetsFactory.createNormalSet(defaultCollectionType, set);

		case OPTIMIZED:
			return SetsFactory.createNormalSet(championCollectionType, set);
			
		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % SLEEPING_FREQ == 0)
				return SetsFactory.createSizeMonitor(championCollectionType, this, set);
			else
				return SetsFactory.createNormalSet(championCollectionType, set);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % SLEEPING_FREQ == 0)
				return SetsFactory.createFullMonitor(championCollectionType, this, set);
			else
				return SetsFactory.createNormalSet(championCollectionType, set);

		case ACTIVE_MEMORY:
			return SetsFactory.createSizeMonitor(defaultCollectionType, this, set);

		case ACTIVE_FULL:
			return SetsFactory.createFullMonitor(defaultCollectionType, this, set);
		}
		return null;
	}


}
