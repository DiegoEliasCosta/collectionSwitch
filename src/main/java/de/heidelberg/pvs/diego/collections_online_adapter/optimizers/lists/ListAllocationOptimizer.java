package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public interface ListAllocationOptimizer {
	
	int getMonitoringIndex();

	void updateOperationsAndSize(int index, int indexOp, int midListOp, int contiansOp, int size);

	void updateSize(int index, int size);

	void setContext(ListAllocationContext context);

}
