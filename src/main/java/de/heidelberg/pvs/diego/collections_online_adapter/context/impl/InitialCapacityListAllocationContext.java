package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class InitialCapacityListAllocationContext implements ListAllocationContext {

	private static final int DEFAULT_INITIAL_CAPACITY = 10;
	
	private int analyzedInitialCapacity;
	
	public InitialCapacityListAllocationContext() {
		super();
		this.analyzedInitialCapacity = DEFAULT_INITIAL_CAPACITY;
	}

	@Override
	public void updateCollectionInitialCapacity(int size) {
		this.analyzedInitialCapacity = size;
		
	}

	@Override
	public <E> List<E> createList() {
		return new ArrayList<>(analyzedInitialCapacity);
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {
		return new ArrayList<E>(initialCapacity);
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		return new ArrayList<>(c);
	}

}
