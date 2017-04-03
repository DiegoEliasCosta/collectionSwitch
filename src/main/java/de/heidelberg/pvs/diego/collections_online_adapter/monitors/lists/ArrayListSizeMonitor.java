package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.util.ArrayList;
import java.util.Collection;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ArrayListSizeMonitor<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 20170101L;
	private ListAllocationOptimizer context;
	
	int index;
	
	public ArrayListSizeMonitor(ListAllocationOptimizer context, int index) {
		super();
		this.context = context;
		this.index = index;
	}

	public ArrayListSizeMonitor(int initialCapacity, ListAllocationOptimizer context, int index) {
		super(initialCapacity);
		this.context = context;
		this.index = index;

	}

	public ArrayListSizeMonitor(Collection<? extends E> c, ListAllocationOptimizer context, int index) {
		super(c);
		this.context = context;
		this.index = index;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(index, size());
	}

}
