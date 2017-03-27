package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class AdaptiveSetMemoryOptimizer<E> implements SetAllocationOptimizer<E> {

	int sizes[] = new int[WINDOW_SIZE];
	
	SetAllocationContext<E> context;
	
	
	public AdaptiveSetMemoryOptimizer(SetAllocationContext<E> context) {
		super();
		this.context = context;
	}

	@Override
	public void updateOperationsAndSize(int containsOp, int iterationOp, int size) {
		
		
	}

	@Override
	public void updateSize(int size) {
		
	}

	

}
