package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;

public class InactiveListAllocationContextImpl implements ListAllocationContext {

	@Override
	public void updateCollectionInitialCapacity(int size) {
	}

	@Override
	public <E> List<E> createList() {
		return new ArrayList<E>();
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {
		return new ArrayList<E>(initialCapacity);
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		return new ArrayList<E>(c);
	}

	@Override
	public void updateCollectionType(ListCollectionType type) {
		
	}

}
