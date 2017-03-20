package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface ListAllocationContextBase<E> {
	
	void updateOperationsAndSize(int indexOp, int midListOp, int contiansOp, int size);
	
	void updateSize(int size);

}
