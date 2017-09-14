package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class InitialCapacityListAllocationContext implements ListAllocationContextInfo {

	private int analyzedInitialCapacity = 10;
	
	private int windowSize;
	private int instancesCount;

	private ListAllocationOptimizer optimizer;
	
	public InitialCapacityListAllocationContext(ListAllocationOptimizer optimizer, int windowSize, int sampleSize) {
		super();
		this.windowSize = windowSize;
		this.optimizer = optimizer;
		this.instancesCount = 0;
		
	}

	@Override
	public void updateCollectionInitialCapacity(int size) {
		this.analyzedInitialCapacity = size;
		instancesCount = 0; // reset  
		
	}

	@Override
	public <E> List<E> createList() {
		
		if(instancesCount++ < windowSize) {
			return optimizer.createMonitor(new ArrayList<E>(analyzedInitialCapacity));
		}
		
		return new ArrayList<E>(analyzedInitialCapacity);
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
	public ListCollectionType getCurrentCollectionType() {
		return ListCollectionType.JDK_ARRAYLIST; // default
	}

	@Override
	public int getAnalyzedInitialCapacity() {
		return analyzedInitialCapacity;
	}

	@Override
	public void updateCollectionType(ListCollectionType type) {
		// Do nothing
		
	}

}
