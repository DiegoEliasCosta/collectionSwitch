package de.heidelberg.pvs.diego.collectionswitch.optimizers.maps;


import java.util.Map;

import de.heidelberg.pvs.diego.collectionswitch.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.AllocationOptimizer;

public interface MapAllocationOptimizer extends AllocationOptimizer {
	
	public <K, V> Map<K, V> createMonitor(Map<K, V> map);
	
	void setContext(MapAllocationContext context);
	
}
