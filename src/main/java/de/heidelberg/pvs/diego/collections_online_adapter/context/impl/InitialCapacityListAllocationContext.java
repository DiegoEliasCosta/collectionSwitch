package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListOptimizer;

public class InitialCapacityListAllocationContext implements ListAllocationContext {

	private int analyzedInitialCapacity = 10;
	
	private int windowSize;
	private int instancesCount;

	private ListOptimizer optimizer;
	
	public InitialCapacityListAllocationContext(ListOptimizer optimizer, int windowSize, int sampleSize) {
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
		return new ArrayList<>(c);
	}

}
