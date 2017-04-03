package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;

public interface MapAllocationOptimizer {

	void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size);

	void updateSize(int index, int size);

	int getMonitoringIndex();

	void setContext(AllocationContextUpdatable context);

}
