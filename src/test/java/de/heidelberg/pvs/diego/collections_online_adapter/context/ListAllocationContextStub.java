package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.List;

public class ListAllocationContextStub<E> implements ListAllocationContext<E> {

	@SuppressWarnings("rawtypes")
	public static ListAllocationContextStub INSTANCE = new ListAllocationContextStub<>();
	
	@Override
	public List<E> createList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> createList(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> createList(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOperationsAndSize(int indexOp, int midListOp, int contiansOp, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSize(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void optimizeAllocationContext(CollectionTypeEnum championCollectionTypeEnum, int analyzedInitialCapacity) {
		// TODO Auto-generated method stub
		
	}



}
