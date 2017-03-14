package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists;

import java.util.Collection;
import java.util.LinkedList;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class LinkedListSizeMonitor<E> extends LinkedList<E> {

	private static final long serialVersionUID = 20170101L;
	
	// Monitor
	private ListAllocationContext<E> context;
	
	public LinkedListSizeMonitor(ListAllocationContext<E> context) {
		super();
		this.context = context;
	}

	public LinkedListSizeMonitor(Collection<? extends E> c, ListAllocationContext<E> context) {
		super(c);
		this.context = context;
	}

	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(size());
	}
}
