package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;


public interface MapAllocationOptimizer {

	void updateOperationsAndSize(int containsOp, int iterationOp, int size);

	void updateSize(int size);

}
