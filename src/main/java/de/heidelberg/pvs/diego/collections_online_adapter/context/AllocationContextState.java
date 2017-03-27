package de.heidelberg.pvs.diego.collections_online_adapter.context;

public enum AllocationContextState {
	
	/**
	 * The context is currently analyzing the instances through optimizers
	 */
	ACTIVE_MEMORY,
	ACTIVE_FULL,
	
	SLEEPING_MEMORY,
	SLEEPING_FULL,
	
	OPTIMIZED,
	
	INACTIVE; 
	
}
