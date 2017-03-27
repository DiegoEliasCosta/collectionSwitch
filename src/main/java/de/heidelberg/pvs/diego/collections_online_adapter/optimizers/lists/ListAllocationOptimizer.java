package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextBase;

public interface ListAllocationOptimizer<E> extends ListAllocationContextBase<E> {
	
	List<E> createListMonitor(Collection<? extends E> list, CollectionTypeEnum collectionType);
	
	List<E> createListMonitor(int inicialCapacity, CollectionTypeEnum collectionType);
	
	// Window size
	static final int WINDOW_SIZE = 10;
	static final int CONVERGENCE_RATE = 7;
	
	
}
