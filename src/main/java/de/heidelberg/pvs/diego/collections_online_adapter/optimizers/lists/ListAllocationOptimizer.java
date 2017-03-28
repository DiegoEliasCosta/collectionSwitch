package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextBase;

public interface ListAllocationOptimizer<E> extends ListAllocationContextBase<E> {
	
	// Window size
	static final int WINDOW_SIZE = 10;
	static final int CONVERGENCE_RATE = 7;
	
	
}
