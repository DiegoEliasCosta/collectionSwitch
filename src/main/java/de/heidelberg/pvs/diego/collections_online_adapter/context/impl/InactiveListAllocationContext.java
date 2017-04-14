package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.ListsFactory;

public class InactiveListAllocationContext implements ListAllocationContext {
	
	private CollectionTypeEnum type;
	
	public InactiveListAllocationContext(CollectionTypeEnum type) {
		super();
		this.type = type;
	}

	@Override
	public void optimizeCollectionType(CollectionTypeEnum type, int mode, int median) {
		// TODO
	}

	@Override
	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		// TODO Auto-generated method stub

	}

	@Override
	public <E> List<E> createList() {
		return ListsFactory.createNormalList(type, 10);
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {
		return ListsFactory.createNormalList(type, initialCapacity);
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		return ListsFactory.createNormalList(type, c);
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
		// TODO Auto-generated method stub
		return 0;
	}

}
