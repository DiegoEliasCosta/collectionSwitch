package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists;

import java.util.Collection;
import java.util.LinkedList;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class LinkedListSizeMonitor<E> extends LinkedList<E> {

	private static final long serialVersionUID = 20170101L;
	
	// Monitor
	private ListAllocationOptimizer context;
	
	public LinkedListSizeMonitor(ListAllocationOptimizer context) {
		super();
		this.context = context;
	}

	public LinkedListSizeMonitor(Collection<? extends E> c, ListAllocationOptimizer context) {
		super(c);
		this.context = context;
	}

	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(size());
	}
}
