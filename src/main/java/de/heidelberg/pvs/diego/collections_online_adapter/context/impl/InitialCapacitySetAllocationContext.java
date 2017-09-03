package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class InitialCapacitySetAllocationContext implements SetAllocationContext {

	private int analyzedInitialCapacity = 1 << 4;
	
	@Override
	public void updateCollectionInitialCapacity(int size) {
		analyzedInitialCapacity = size;
		
	}

	@Override
	public <E> Set<E> createSet() {
		return new HashSet<E>(analyzedInitialCapacity);
	}

	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		return new HashSet<E>(initialCapacity);
	}

	@Override
	public <E> Set<E> createSet(Collection<? extends E> set) {
		return new HashSet<E>(set);
	}

}
