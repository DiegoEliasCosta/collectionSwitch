package de.heidelberg.pvs.diego.collectionswitch.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collectionswitch.context.ListAllocationContextInfo;
import de.heidelberg.pvs.diego.collectionswitch.context.ListCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.lists.ListAllocationOptimizer;

public class EmpiricalListAllocationContext implements ListAllocationContextInfo {

	private ListCollectionType type;

	private int instancesCount;
	private int windowSize;

	private ListAllocationOptimizer optimizer;
	
	
	public EmpiricalListAllocationContext(ListCollectionType defaultCollectionType, 
			ListAllocationOptimizer optimizer, int windowSize) {
		super();
		this.type = defaultCollectionType;
		this.windowSize = windowSize;
		this.optimizer = optimizer;
		instancesCount = 0;
	}

	@Override
	public <E> List<E> createList() {
		List<E> list = type.createList();
		
		if(instancesCount++ < windowSize) {
			return optimizer.createMonitor(list);
		}
		
		return list;
		
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


}
