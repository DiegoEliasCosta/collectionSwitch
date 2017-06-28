package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;


import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public interface MapAllocationOptimizer extends AllocationOptimizer {
	
	public <K, V> Map<K, V> createMonitor(Map<K, V> map);
	
}
