package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.List;

public interface ListAllocationContext<E> {

	List<E> createList();
	
	void updateOperations(int indexOp, int midListOp, int size);
	
	void updateOperations(int getOp, int containsOp, int removeOp, int size);
	
	boolean isOnline();
}
