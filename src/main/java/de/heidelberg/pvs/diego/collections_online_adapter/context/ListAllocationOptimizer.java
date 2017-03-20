package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.List;

public interface ListAllocationOptimizer<E> extends ListAllocationContextBase<E> {
	
	boolean isOnline();
	
	boolean isSleeping();
	
	List<E> createListMonitor(List<? extends E> list);
	
	List<E> createListMonitor(int inicialCapacity);
	
	
}
