package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class InitialCapacitySetAllocationContext implements SetAllocationContextInfo {

	private int analyzedInitialCapacity = 1 << 4;
	
	private int windowSize;
	private int instancesCount;

	private SetAllocationOptimizer optimizer;
	
	
	
	
	public InitialCapacitySetAllocationContext(SetAllocationOptimizer optimizer, int windowSize) {
		super();
		this.optimizer = optimizer;
		this.windowSize = windowSize;
	}

	@Override
	public void updateCollectionInitialCapacity(int size) {
		analyzedInitialCapacity = size;
		instancesCount = 0; // reset
		
	}

	@Override
	public <E> Set<E> createSet() {
		
		if(instancesCount++ < windowSize) {
			return optimizer.createMonitor(new HashSet<E>(analyzedInitialCapacity));
		}
		
		return new HashSet<E>(analyzedInitialCapacity);
		
	}

	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		return new HashSet<E>(initialCapacity);
	}

	@Override
	public <E> Set<E> createSet(Collection<? extends E> set) {
		return new HashSet<E>(set);
	}

	@Override
	public int getAnalyzedInitialCapacity() {
		return analyzedInitialCapacity; 
				
	}

	@Override
	public String getCurrentCollectionType() {
		return "Hash";
	}

}
