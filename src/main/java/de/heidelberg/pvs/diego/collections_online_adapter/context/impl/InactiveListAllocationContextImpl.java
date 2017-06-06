package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveMap;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapPassiveSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class InactiveListAllocationContextImpl implements ListAllocationContext {

	public InactiveListAllocationContextImpl(AllocationOptimizer optimizer) {
		super();
	}

	@Override
	public void updateCollectionSize(int size) {
	}

	@Override
	public void noSizeConvergence() {
	}

	@Override
	public <E> List<E> createList() {
		return new ArrayList<>();
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {
		return new ArrayList<>(initialCapacity);
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		return new ArrayList<>(c);
	}

}
