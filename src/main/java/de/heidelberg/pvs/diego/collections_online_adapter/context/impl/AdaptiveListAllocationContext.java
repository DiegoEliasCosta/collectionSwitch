package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.ListsFactory;
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
public class AdaptiveListAllocationContext<E> implements ListAllocationContext<E>, AllocationContextUpdatable {

	private static final int FULL_ANALYSIS_THRESHOLD = 100;

	private static final int SLEEPING_FREQ = 10;

	AllocationContextState state;

	ListAllocationOptimizer<E> optimizer;

	private CollectionTypeEnum championCollectionType;
	private CollectionTypeEnum defaultCollectionType;

	private int analyzedInitialCapacity;

	private int sleepingMonitoringCount;

	public AdaptiveListAllocationContext(CollectionTypeEnum collectionType) {
		super();
		this.championCollectionType = collectionType;
		this.defaultCollectionType = collectionType;
		
		// First State
		this.state = AllocationContextState.ACTIVE_MEMORY;
		analyzedInitialCapacity = 10;
	}

	public List<E> createList() {
		return this.createList(this.analyzedInitialCapacity);
	}

	@Override
	public List<E> createList(int initialCapacity) {

		int n_alloc;
		switch (state) {

		case INACTIVE:
			return ListsFactory.createNormalLists(defaultCollectionType, initialCapacity);

		case OPTIMIZED:
			return ListsFactory.createNormalLists(championCollectionType, initialCapacity);
			
		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % SLEEPING_FREQ == 0)
				return ListsFactory.createSizeMonitor(championCollectionType, this, initialCapacity);
			else
				return ListsFactory.createNormalLists(championCollectionType, initialCapacity);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % SLEEPING_FREQ == 0)
				return ListsFactory.createFullMonitor(championCollectionType, this, initialCapacity);
			else
				return ListsFactory.createNormalLists(championCollectionType, initialCapacity);

		case ACTIVE_MEMORY:
			return ListsFactory.createSizeMonitor(defaultCollectionType, this, initialCapacity);

		case ACTIVE_FULL:
			return ListsFactory.createFullMonitor(defaultCollectionType, this, initialCapacity);
		}
		return null;
	}

	@Override
	public List<E> createList(Collection<? extends E> list) {
		
		int n_alloc;
		switch (state) {

		case INACTIVE:
			return ListsFactory.createNormalLists(defaultCollectionType, list);

		case OPTIMIZED:
			return ListsFactory.createNormalLists(championCollectionType, list);
			
		case SLEEPING_MEMORY:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % SLEEPING_FREQ == 0)
				return ListsFactory.createSizeMonitor(championCollectionType, this, list);
			else
				return ListsFactory.createNormalLists(championCollectionType, list);

		case SLEEPING_FULL:
			// Only creates with monitor in certain frequencies
			n_alloc = sleepingMonitoringCount++;
			if (n_alloc % SLEEPING_FREQ == 0)
				return ListsFactory.createFullMonitor(championCollectionType, this, list);
			else
				return ListsFactory.createNormalLists(championCollectionType, list);

		case ACTIVE_MEMORY:
			return ListsFactory.createSizeMonitor(defaultCollectionType, this, list);

		case ACTIVE_FULL:
			return ListsFactory.createFullMonitor(defaultCollectionType, this, list);
		}
		return null;
	}

	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {
		optimizer.updateOperationsAndSize(indexOp, midListOp, containsOp, size);
	}

	public void updateSize(int size) {
		optimizer.updateSize(size);
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
	public void optimizeCollectionType(CollectionTypeEnum collection) {
		this.championCollectionType = collection;
		this.state = AllocationContextState.SLEEPING_FULL;
		
	}

	@Override
	public void noCollectionTypeConvergence() {
		// TODO Auto-generated method stub
		
	}

}
