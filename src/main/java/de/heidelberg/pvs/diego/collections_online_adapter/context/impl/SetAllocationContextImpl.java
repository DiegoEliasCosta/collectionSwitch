package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class SetAllocationContextImpl implements SetAllocationContext {

	private int analyzedCollectionSize = 0;
	
	
	
	
	@Override
	public void updateCollectionSize(int size) {
		
		analyzedCollectionSize = size;
		
		
	}

	@Override
	public void noSizeConvergence() {
		// TODO Auto-generated method stub
		
	}

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
	public AllocationContextState getAllocationContextState() {
		// TODO Auto-generated method stub
		return null;
	}

}
