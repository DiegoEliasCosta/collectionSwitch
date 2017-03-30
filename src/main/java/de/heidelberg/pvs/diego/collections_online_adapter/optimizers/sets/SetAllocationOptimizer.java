package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

public interface SetAllocationOptimizer {

	void updateOperationsAndSize(int containsOp, int iterationOp, int size);

	void updateSize(int size);

}
