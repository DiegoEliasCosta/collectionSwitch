package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public interface ListAllocationOptimizer extends AllocationOptimizer {
	
	public <E> List<E> createMonitor(List<E> monitor);
	
	public void setContext(ListAllocationContext context);

}
