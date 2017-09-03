package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;

public interface ListOptimizer {
	
	List<?> createMonitor(List<?> monitor);
	
	void analyzeAndOptimizeContext();

}
