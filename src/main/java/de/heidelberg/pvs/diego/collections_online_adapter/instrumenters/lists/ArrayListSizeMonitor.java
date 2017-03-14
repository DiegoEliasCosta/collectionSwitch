package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists;

import java.util.ArrayList;
import java.util.Collection;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class ArrayListSizeMonitor<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 20170101L;
	private ListAllocationContext<E> context;
	
	public ArrayListSizeMonitor(ListAllocationContext<E> context) {
		super();
		this.context = context;
	}

	public ArrayListSizeMonitor(int initialCapacity, ListAllocationContext<E> context) {
		super(initialCapacity);
		this.context = context;
	}

	public ArrayListSizeMonitor(Collection<? extends E> c, ListAllocationContext<E> context) {
		super(c);
		this.context = context;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(size());
	}

}
