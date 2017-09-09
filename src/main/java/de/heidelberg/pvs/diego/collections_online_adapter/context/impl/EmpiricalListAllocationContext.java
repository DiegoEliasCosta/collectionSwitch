package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.lists.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListEmpiricalOptimizer;

public class EmpiricalListAllocationContext implements ListAllocationContext {

	// Default value used for most list implementations
	private int analyzedInitialCapacity = 10;
	
	private CollectionTypeEnum collectionType;

	private int count;
	private int windowSize;

	private ListEmpiricalOptimizer optimizer;
	
	@Override
	public void updateCollectionInitialCapacity(int size) {
		this.analyzedInitialCapacity = size;
	}
	

	@Override
	public <E> List<E> createList() {
		
		return this.createList(analyzedInitialCapacity);
		
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {

		List<E> list;
		
		switch(collectionType) {
		
		case ARRAY:
			list = new ArrayList<>(initialCapacity);
			
		case HASH:
			list = new HashArrayList<>(initialCapacity);
			
		case LINKED:
			list = new LinkedList<>();
			
		default:
			// Thread-safe case: Use the default implementation just in case
			list = new ArrayList<>(initialCapacity);
		}
		
		if(count++ < windowSize) {
			return optimizer.createMonitor(list);
		}
		
		return list;
		
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		
		List<E> list;
		
		switch(collectionType) {
		
		case ARRAY:
			list = new ArrayList<>(c);
			
		case HASH:
			list = new HashArrayList<>(c);
			
		case LINKED:
			list = new LinkedList<>();
			
		default:
			// Thread-safe case: Use the default implementation just in case
			list = new ArrayList<>(c);
		}
		
		if(count++ < windowSize) {
			return optimizer.createMonitor(list);
		}
		
		return list;
	}



	@Override
	public void updateCollectionType(CollectionTypeEnum type) {
		this.collectionType = type;
		this.count = 0;
	}

}
