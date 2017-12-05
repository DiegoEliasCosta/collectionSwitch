package de.heidelberg.pvs.diego.collectionswitch.optimizers.lists;

import java.util.List;

import de.heidelberg.pvs.diego.collectionswitch.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.AllocationOptimizer;

public interface ListAllocationOptimizer extends AllocationOptimizer {
	
	public <E> List<E> createMonitor(List<E> monitor);
	
	public void setContext(ListAllocationContext context);

}
