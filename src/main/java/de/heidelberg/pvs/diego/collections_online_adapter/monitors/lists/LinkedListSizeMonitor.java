package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.util.Collection;
import java.util.LinkedList;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class LinkedListSizeMonitor<E> extends LinkedList<E> {

	private static final long serialVersionUID = 20170101L;
	
	// Monitor
	private AllocationOptimizer context;

	private int index;
	
	public LinkedListSizeMonitor(AllocationOptimizer context, int index) {
		super();
		this.context = context;
		this.index = index;
	}

	public LinkedListSizeMonitor(Collection<? extends E> c, AllocationOptimizer context, int index) {
		super(c);
		this.context = context;
		this.index = index;
	}

	
	@Override
	protected void finalize() throws Throwable {
		context.updateSize(index, size());
	}
}
