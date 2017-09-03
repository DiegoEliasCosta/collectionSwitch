package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.Set;

public interface SetAllocationOptimizer {
	
	public <E> Set<E> createMonitor(Set<E> set);
	
	public void analyzeAndOptimize();

}
