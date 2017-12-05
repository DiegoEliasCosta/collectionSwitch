package de.heidelberg.pvs.diego.collectionswitch.optimizers.sets;

import java.util.Set;

import de.heidelberg.pvs.diego.collectionswitch.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.AllocationOptimizer;

public interface SetAllocationOptimizer extends AllocationOptimizer {
	
	public <E> Set<E> createMonitor(Set<E> set);
	
	public void analyzeAndOptimize();
	
	public void setContext(SetAllocationContext optimizer);

}
