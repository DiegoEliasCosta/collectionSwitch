package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class EmpiricalSetAllocationContext  implements SetAllocationContextInfo {

	private int analyzedInitialCapacity = 1 << 4;
	
	private int windowSize;
	private int instancesCount;

	private SetAllocationOptimizer optimizer;
	
	private SetCollectionType type;
	
	public EmpiricalSetAllocationContext(SetCollectionType type, SetAllocationOptimizer optimizer, int windowSize) {
		super();
		this.type = type;
		this.optimizer = optimizer;
		this.windowSize = windowSize;
		this.instancesCount = 0;
	}

	@Override
	public void updateCollectionInitialCapacity(int size) {
		analyzedInitialCapacity = size;
		instancesCount = 0; // reset
		
	}

	@Override
	public <E> Set<E> createSet() {
		return createSet(analyzedInitialCapacity);
		
	}

	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		
		Set<E> set = type.createSet(initialCapacity);
				
		if(instancesCount++ < windowSize) {
			return this.optimizer.createMonitor(set);
		}
		
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
		return type.toString();
	}

	@Override
	public void updateCollectionType(SetCollectionType type) {
		this.type = type;
	}

}
