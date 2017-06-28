package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public interface SetAllocationOptimizer extends AllocationOptimizer {
	
	public <E> Set<E> createMonitor(Set<E> set);

}
