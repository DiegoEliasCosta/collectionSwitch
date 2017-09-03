package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public interface SetAllocationOptimizer {
	
	public <E> Set<E> createMonitor(Set<E> set);
	
	public void analyzeAndOptimize();
	
	public void setContext(SetAllocationContext optimizer);

}
