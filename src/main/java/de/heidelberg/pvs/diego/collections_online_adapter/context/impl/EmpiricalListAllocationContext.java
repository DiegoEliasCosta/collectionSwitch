package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class EmpiricalListAllocationContext implements ListAllocationContextInfo {

	// Default value used for most list implementations
	private int analyzedInitialCapacity = 10;
	
	private ListCollectionType type;

	private int instancesCount;
	private int windowSize;

	private ListAllocationOptimizer optimizer;
	
	
	public EmpiricalListAllocationContext(ListCollectionType defaultCollectionType, ListAllocationOptimizer optimizer, int windowSize) {
		super();
		this.type = defaultCollectionType;
		this.windowSize = windowSize;
		this.optimizer = optimizer;
		instancesCount = 0;
	}


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

		List<E> list = type.createList(initialCapacity);
		
		if(instancesCount++ < windowSize) {
			return optimizer.createMonitor(list);
		}
		
		return list;
		
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		
		List<E> list = type.createList(c);
		
		if(instancesCount++ < windowSize) {
			return optimizer.createMonitor(list);
		}
		
		return list;
	}



	@Override
	public void updateCollectionType(ListCollectionType type) {
		this.type = type;
		this.instancesCount = 0; // reset
	}


	@Override
	public ListCollectionType getCurrentCollectionType() {
		return type;
	}


	@Override
	public int getAnalyzedInitialCapacity() {
		return 0;
	}

}
