package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.ListsFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class FastListAllocationContext implements ListAllocationContext {

	int sample;
	int count;
	
	ListAllocationOptimizer optimizer;
	
	CollectionTypeEnum championCollection;
	CollectionTypeEnum defaultCollection;
	
	AllocationContextState state;
	private int analyzedInitialCapacity;
	
	
	public FastListAllocationContext(CollectionTypeEnum defaultCollections, int sample, ListAllocationOptimizer optimizer) {
		super();
		this.sample = sample;
		this.optimizer = optimizer;
		this.defaultCollection = defaultCollections;
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
		
		switch(state) {
		
			case INACTIVE:
				return ListsFactory.createNormalList(defaultCollection);
				
			case ACTIVE_FULL:
				break;
				
		
		}
		
		return null;
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllocationContextState getAllocationContextState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollectionTypeEnum getChampion() {
		// TODO Auto-generated method stub
		return null;
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
