package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface MapAllocationContextBase<K, V> {
	
	void updateOperationsAndSize(int containsOp, int iterationOp, int size);
	
	void updateSize(int size);

}
