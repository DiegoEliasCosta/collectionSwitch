package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists;

import java.util.ArrayList;
import java.util.Collection;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ArrayListSizeMonitor<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 20170101L;
	private ListAllocationOptimizer context;
	
	public ArrayListSizeMonitor(ListAllocationOptimizer context) {
		super();
		this.context = context;
	}

	public ArrayListSizeMonitor(int initialCapacity, ListAllocationOptimizer context) {
		super(initialCapacity);
		this.context = context;
	}

	public ArrayListSizeMonitor(Collection<? extends E> c, ListAllocationOptimizer context) {
		super(c);
		this.context = context;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(size());
	}

}
