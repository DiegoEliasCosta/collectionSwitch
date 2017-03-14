package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.List;

public interface ListAllocationContext<E> {
	
	List<E> createList();
	
	List<E> createList(int initialCapacity);
	
	List<E> createList(Collection<? extends E> c);
	
	void updateOperationsAndSize(int indexOp, int midListOp, int contiansOp, int size);
	
	void updateSize(int size);
	
	boolean isOnline();
	
}
