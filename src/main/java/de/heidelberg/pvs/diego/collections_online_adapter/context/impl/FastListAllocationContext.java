package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.ListsFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class FastListAllocationContext implements ListAllocationContext {

	private static final float ALPHA = 0.9f;
	int sample;

	int count;
	int finalizedCount;

	CollectionTypeEnum championCollection;
	CollectionTypeEnum defaultCollection;

	AllocationContextState state;
	private int analyzedInitialCapacity;
	private int contains;
	private int analyzedContains;
	
	private ListAllocationOptimizer optimizer;
	
	
	public FastListAllocationContext(CollectionTypeEnum defaultCollections, ListAllocationOptimizer optimizer, int sample) {
		super();
		this.sample = sample;
		this.defaultCollection = defaultCollections;
		this.championCollection = defaultCollections;
		this.analyzedInitialCapacity = 10;
		this.optimizer = optimizer;
		state = AllocationContextState.WARMUP;
	}

	@Override
	public void optimizeCollectionType(CollectionTypeEnum collecton, int mode, int median) {
		// TODO Auto-generated method stub

	}

	@Override
	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		// TODO Auto-generated method stub

	}

	@Override
	public <E> List<E> createList() {
		
		int countCopy = count++;

		switch (state) {

		case ACTIVE:
			if (countCopy % sample == 0) {
				return ListsFactory.createFullMonitor(championCollection, optimizer, this.analyzedInitialCapacity);
			} else {
				return ListsFactory.createNormalList(championCollection, this.analyzedInitialCapacity);
			}

		case INACTIVE:
			return ListsFactory.createNormalList(defaultCollection);

		case WARMUP:
			if (countCopy % sample == 0) {
				return ListsFactory.createFullMonitor(defaultCollection, optimizer);
			} else {
				return ListsFactory.createNormalList(defaultCollection);
			}

		default:
			return null;

		}
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {
		
		int countCopy = count++;
		
		switch (state) {

		case ACTIVE:
			if (countCopy % sample == 0) {
				return ListsFactory.createFullMonitor(championCollection, optimizer, this.analyzedInitialCapacity);
			} else {
				return ListsFactory.createNormalList(championCollection, this.analyzedInitialCapacity);
			}

		case INACTIVE:
			return ListsFactory.createNormalList(defaultCollection);

		case WARMUP:
			if (countCopy % sample == 0) {
				return ListsFactory.createFullMonitor(defaultCollection, optimizer);
			} else {
				return ListsFactory.createNormalList(defaultCollection);
			}

		default:
			return null;

		}
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> list) {
		
		int countCopy = count++;
		
		// FIXME: Analyze only the operations in this case
		switch (state) {
		case ACTIVE:
			if (countCopy % sample == 0) {
				return ListsFactory.createSizeMonitor(championCollection, optimizer, list);
			} else {
				return ListsFactory.createNormalList(championCollection, list);
			}

		case INACTIVE:
			return ListsFactory.createNormalList(defaultCollection, list);

		case WARMUP:
			if (countCopy % sample == 0) {
				return ListsFactory.createSizeMonitor(defaultCollection, optimizer, list);
			} else {
				return ListsFactory.createNormalList(defaultCollection, list);
			}

		default:
			return null;

		}
		
		
	}

	@Override
	public AllocationContextState getAllocationContextState() {
		return this.state;
	}

	@Override
	public CollectionTypeEnum getChampion() {
		return this.championCollection;
	}

	@Override
	public void setAllocationContextState(AllocationContextState inactive) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getInitialCapacity() {
		return this.analyzedInitialCapacity;
	}


}
