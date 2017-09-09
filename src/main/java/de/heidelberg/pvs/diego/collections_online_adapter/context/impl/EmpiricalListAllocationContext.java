package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class EmpiricalListAllocationContext implements ListAllocationContext {

	@Override
	public void updateCollectionInitialCapacity(int size) {
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
	public void updateCollectionType(CollectionTypeEnum type) {
		// TODO Auto-generated method stub
		
	}

}
