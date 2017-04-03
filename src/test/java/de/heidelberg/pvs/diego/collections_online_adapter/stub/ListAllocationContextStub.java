package de.heidelberg.pvs.diego.collections_online_adapter.stub;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ListAllocationContextStub implements ListAllocationContext {


	@Override
	public void optimizeCollectionType(CollectionTypeEnum collecton, int medianInitialCapacity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noCollectionTypeConvergence(int medianInitialCapacity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <E> List<E> createList() {
		// TODO Auto-generated method stub
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


}
