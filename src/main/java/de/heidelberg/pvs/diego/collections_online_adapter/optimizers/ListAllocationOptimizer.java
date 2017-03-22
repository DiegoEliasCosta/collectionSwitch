package de.heidelberg.pvs.diego.collections_online_adapter.optimizers;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextBase;

public interface ListAllocationOptimizer<E> extends ListAllocationContextBase<E> {
	
	boolean isOnline();
	
	boolean isSleeping();
	
	List<E> createListMonitor(List<? extends E> list);
	
	List<E> createListMonitor(int inicialCapacity);
	
	static final int SIZE_THRESHOLD = 3;
	static final int STABILITY_THRESHOLD = 3; 
	
	static final int WINDOW_SIZE = 10;
	
	
	static final int ARRAY_THRESHOLD = 10;
	static final int UNIFIED_THRESHOLD = 1000;
	
}
