package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.ListsFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.RuleBasedListOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

/**
 * Facade created add flexibility to the {@link ListAllocationContext} creation.
 * In this way we can change only the facade instantiation to inject a new
 * context behavior without exposing this change to the app client.
 * 
 * @author Diego
 *
 * @param <E>
 */
public class AdaptiveListAllocationContext extends AbstractAdaptiveAllocationContext implements ListAllocationContext, AllocationContextUpdatable {

	ListAllocationOptimizer optimizer;

	public AdaptiveListAllocationContext(CollectionTypeEnum collectionType, int windowSize, int fullAnalysisThreshold, int sleepingFrequency, int convergencyRate, int divergentRoundsThreshold) {
		super(collectionType, convergencyRate, convergencyRate, convergencyRate, convergencyRate, divergentRoundsThreshold);
		
		// Create the Optimizer
		this.optimizer = new RuleBasedListOptimizer(this, windowSize, convergencyRate);

	}

	public <E> List<E> createList() {
		return this.createList(this.analyzedInitialCapacity);
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {

		int n_alloc;
		switch (state) {

		case INACTIVE:
			return ListsFactory.createNormalLists(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return ListsFactory.createNormalLists(championCollectionType, initialCapacity);

		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			if(shouldMonitor())
				return ListsFactory.createSizeMonitor(championCollectionType, optimizer, initialCapacity);
			else
				return ListsFactory.createNormalLists(championCollectionType, initialCapacity);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % sleepingFrequency == 0)
				return ListsFactory.createFullMonitor(championCollectionType, optimizer, initialCapacity);
			else
				return ListsFactory.createNormalLists(championCollectionType, initialCapacity);

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
			return ListsFactory.createNormalLists(defaultCollectionType, list);

		case OPTIMIZED:
			return ListsFactory.createNormalLists(championCollectionType, list);

		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			if(shouldMonitor())
				return ListsFactory.createSizeMonitor(championCollectionType, optimizer, list);
			else
				return ListsFactory.createNormalLists(championCollectionType, list);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			if(shouldMonitor())
				return ListsFactory.createFullMonitor(championCollectionType, optimizer, list);
			else
				return ListsFactory.createNormalLists(championCollectionType, list);

		case ACTIVE_MEMORY:
			return ListsFactory.createSizeMonitor(defaultCollectionType, optimizer, list);

		case ACTIVE_FULL:
			return ListsFactory.createFullMonitor(defaultCollectionType, optimizer, list);
		}
		return null;
	}

	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {
		optimizer.updateOperationsAndSize(indexOp, midListOp, containsOp, size);
	}

	public void updateSize(int size) {
		optimizer.updateSize(size);
	}
	

}
