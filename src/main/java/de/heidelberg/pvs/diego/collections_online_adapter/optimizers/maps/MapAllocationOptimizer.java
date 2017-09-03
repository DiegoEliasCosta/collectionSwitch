package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;


import java.util.Map;

public interface MapAllocationOptimizer {
	
	public <K, V> Map<K, V> createMonitor(Map<K, V> map);
	
	void analyzeAndOptimizeContext();
	
}
