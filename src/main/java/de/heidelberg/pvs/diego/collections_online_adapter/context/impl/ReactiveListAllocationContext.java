package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.ListsFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ReactiveListAllocationContext extends AbstractAdaptiveAllocationContext
		implements ListAllocationContext, AllocationContextUpdatable {

	ListAllocationOptimizer optimizer;
	
	public ReactiveListAllocationContext(CollectionTypeEnum collectionType, ListAllocationOptimizer optimizer, int windowSize, int fullAnalysisThreshold,
			int sleepingFrequency, int convergencyRate, int divergentRoundsThreshold) {
		super(collectionType, windowSize, fullAnalysisThreshold, sleepingFrequency, convergencyRate,
				divergentRoundsThreshold);
		
		this.optimizer = optimizer;

	}
	
	public <E> List<E> createList() {
		return this.createList(this.analyzedInitialCapacity);
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {

		switch (state) {

		case INACTIVE:
			return ListsFactory.createNormalList(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return ListsFactory.createNormalList(championCollectionType, initialCapacity);

		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			if (shouldMonitor()) {
				return ListsFactory.createSizeMonitor(championCollectionType, optimizer, initialCapacity);
			}
			return ListsFactory.createNormalList(championCollectionType, initialCapacity);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			if (shouldMonitor()) {
				return ListsFactory.createFullMonitor(championCollectionType, optimizer, initialCapacity);
			}
			return ListsFactory.createNormalList(championCollectionType, initialCapacity);

		case ACTIVE_MEMORY:
			return ListsFactory.createSizeMonitor(defaultCollectionType, optimizer, initialCapacity);

		case ACTIVE_FULL:
			return ListsFactory.createFullMonitor(defaultCollectionType, optimizer, initialCapacity);
		}

		return null;

	}

	@Override
	public <E> List<E> createList(Collection<? extends E> list) {

		switch (state) {

		case INACTIVE:
			return ListsFactory.createNormalList(defaultCollectionType, list);

		case OPTIMIZED:
			return ListsFactory.createNormalList(championCollectionType, list);

		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			if (shouldMonitor())
				return ListsFactory.createSizeMonitor(championCollectionType, optimizer, list);
			else
				return ListsFactory.createNormalList(championCollectionType, list);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			if (shouldMonitor())
				return ListsFactory.createFullMonitor(championCollectionType, optimizer, list);
			else
				return ListsFactory.createNormalList(championCollectionType, list);

		case ACTIVE_MEMORY:
			return ListsFactory.createSizeMonitor(defaultCollectionType, optimizer, list);

		case ACTIVE_FULL:
			return ListsFactory.createFullMonitor(defaultCollectionType, optimizer, list);
		}
		return null;
	}
	
}
