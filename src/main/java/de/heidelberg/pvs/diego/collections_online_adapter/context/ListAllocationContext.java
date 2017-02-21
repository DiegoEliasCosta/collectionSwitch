package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.List;

public interface ListAllocationContext {

	List<?> createList();
	
	void updateOperations(int indexOp, int midListOp, int size);
	
	boolean isOnline();
}
