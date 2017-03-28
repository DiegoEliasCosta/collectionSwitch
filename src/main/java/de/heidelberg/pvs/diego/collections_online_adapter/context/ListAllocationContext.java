package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.List;

public interface ListAllocationContext<E> extends ListAllocationContextBase, AllocationContextUpdatable {
	
	List<E> createList();
	
	List<E> createList(int initialCapacity);
	
	List<E> createList(Collection<? extends E> c);
	
}
