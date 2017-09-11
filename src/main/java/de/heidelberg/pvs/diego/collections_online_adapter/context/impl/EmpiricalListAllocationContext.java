package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.lists.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListEmpiricalOptimizer;

public class EmpiricalListAllocationContext implements ListAllocationContext {

	// Default value used for most list implementations
	private int analyzedInitialCapacity = 10;
	
	private ListCollectionType collectionType;

	private int instancesCount;
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

		List<E> list = collectionType.createList(initialCapacity);
		
		if(instancesCount++ < windowSize) {
			return optimizer.createMonitor(list);
		}
		
		return list;
		
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		
		List<E> list = collectionType.createList(c);
		
		if(instancesCount++ < windowSize) {
			return optimizer.createMonitor(list);
		}
		
		return list;
	}



	@Override
	public void updateCollectionType(ListCollectionType type) {
		this.collectionType = type;
		this.instancesCount = 0;
	}

}
