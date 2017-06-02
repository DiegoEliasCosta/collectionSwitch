package de.heidelberg.pvs.diego.collections_online_adapter.optimizers;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;

public interface AllocationOptimizer {
	
	void updateSize(int index, int size);

	void setContext(AllocationContextUpdatable context);
	
}
