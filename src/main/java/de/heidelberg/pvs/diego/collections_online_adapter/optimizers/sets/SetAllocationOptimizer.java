package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public interface SetAllocationOptimizer {

	void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size);

	void updateSize(int index, int size);
	
	int getMonitoringIndex();

	void setContext(SetAllocationContext context);
	
	void addReference(Set<?> set);
	
	void checkFinalizedAnalysis();


}
