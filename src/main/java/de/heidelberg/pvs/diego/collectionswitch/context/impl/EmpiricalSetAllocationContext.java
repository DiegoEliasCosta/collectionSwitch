package de.heidelberg.pvs.diego.collectionswitch.context.impl;

import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collectionswitch.context.SetAllocationContextInfo;
import de.heidelberg.pvs.diego.collectionswitch.context.SetCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.sets.SetAllocationOptimizer;

public class EmpiricalSetAllocationContext  implements SetAllocationContextInfo {

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
	public <E> Set<E> createSet() {
		Set<E> set = type.createSet();
		
		if(instancesCount++ < windowSize) {
			return this.optimizer.createMonitor(set);
		}
		
		return set;
		
	}

	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		
		Set<E> set = type.createSet(initialCapacity);
				
		if(instancesCount++ < windowSize) {
			return this.optimizer.createMonitor(set);
		}
		
		return set;
	}

	@Override
	public <E> Set<E> createSet(Collection<? extends E> setToCopy) {
		
		Set<E> set = type.createSet(setToCopy);
		if(instancesCount++ < windowSize) {
			return this.optimizer.createMonitor(set);
		}
		
		return set;
	}


	@Override
	public String getCurrentCollectionType() {
		return type.toString();
	}

	@Override
	public void updateCollectionType(SetCollectionType type) {
		this.type = type;
		this.instancesCount = 0; // reset
	}

}
