package de.heidelberg.pvs.diego.collections_online_adapter.stub;

import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class SetAllocationContextStub implements SetAllocationContext {

	@Override
	public <E> Set<E> createSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> Set<E> createSet(Collection<? extends E> set) {
		// TODO Auto-generated method stub
		return null;
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
