package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

public interface ListAllocationOptimizer {

	void updateOperationsAndSize(int indexOp, int midListOp, int contiansOp, int size);

	void updateSize(int size);

}
