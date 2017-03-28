package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContextBase;

public interface MapAllocationOptimizer<K, V> extends MapAllocationContextBase<K, V> {
	
	// Window size
	static final int WINDOW_SIZE = 10;
	static final int CONVERGENCE_RATE = 7;
	

}
