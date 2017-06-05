package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveMap;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapPassiveSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class ListAllocationContextImpl implements ListAllocationContext {

	private int sampleRate = 1;
	
	private int analyzedCollectionSize;
	
	private AllocationContextState state;
	private AllocationOptimizer optimizer;
	
	private int count;
	
	public ListAllocationContextImpl(AllocationOptimizer optimizer) {
		super();
		this.optimizer = optimizer;
		this.state = AllocationContextState.WARMUP;
	}

	@Override
	public void updateCollectionSize(int size) {
		this.analyzedCollectionSize = size;
		
	}

	@Override
	public void noSizeConvergence() {
		// TODO To implement this behavior
		
	}

	@Override
	public <E> List<E> createList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return null;
	}

}
