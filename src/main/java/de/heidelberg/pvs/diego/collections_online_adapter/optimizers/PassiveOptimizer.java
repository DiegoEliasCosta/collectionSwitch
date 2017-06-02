package de.heidelberg.pvs.diego.collections_online_adapter.optimizers;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class PassiveOptimizer implements AllocationOptimizer {

	private static final float ALPHA = 0.9f;
	private ListAllocationContext context;
	private int finalizedCount;
	private int analyzedInitialCapacity;
	
	public PassiveOptimizer(ListAllocationContext context) {
		super();
		this.context = context;
	}

	@Override
	public void updateSize(int index, int size) {
		
		int finalizedCopy = finalizedCount;
		
		if (finalizedCopy == 0) {
			analyzedInitialCapacity = size;
			finalizedCount++;

		} else {
			analyzedInitialCapacity = (int) (analyzedInitialCapacity * ALPHA + (1 - ALPHA) * size);
			finalizedCount++;
		}
		
		if(finalizedCopy == 10) {
			this.context.setAllocationContextState(AllocationContextState.ACTIVE);
		}	
		
	}

	@Override
	public void setContext(AllocationContextUpdatable context) {
		// TODO Auto-generated method stub
		
	}


}
