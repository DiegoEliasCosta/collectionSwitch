package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextBase;

public interface SetAllocationOptimizer<E> extends SetAllocationContextBase<E> {
	
	// Window size
	static final int WINDOW_SIZE = 10;
	static final int CONVERGENCE_RATE = 7;
	
	
}
