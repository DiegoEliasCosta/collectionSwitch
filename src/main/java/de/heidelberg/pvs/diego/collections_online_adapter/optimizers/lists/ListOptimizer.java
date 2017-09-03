package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;

public interface ListOptimizer {
	
	<E> List<E> createMonitor(List<E> monitor);
	
	void analyzeAndOptimizeContext();

}
