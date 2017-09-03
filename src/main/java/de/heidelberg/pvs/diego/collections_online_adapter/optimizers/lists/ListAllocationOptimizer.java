package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public interface ListAllocationOptimizer {
	
	public <E> List<E> createMonitor(List<E> monitor);
	
	public void analyzeAndOptimize();
	
	public void setContext(ListAllocationContext context);

}
