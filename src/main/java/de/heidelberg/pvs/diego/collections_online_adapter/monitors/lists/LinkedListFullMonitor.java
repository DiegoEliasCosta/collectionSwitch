package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.util.Collection;
import java.util.LinkedList;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class LinkedListFullMonitor<E> extends LinkedList<E> {

	private static final long serialVersionUID = 20170101L;
	
	// Monitor
	private ListAllocationOptimizer context;
	private int indexOp;
	private int midListOp;
	private int containsOp;

	private int index;
	
	public LinkedListFullMonitor(ListAllocationOptimizer context, int index) {
		super();
		this.context = context;
		this.index = index;
	}

	public LinkedListFullMonitor(Collection<? extends E> c, ListAllocationOptimizer context, int index) {
		super(c);
		this.context = context;
		this.index = index;
	}

	@Override
	public E get(int index) {
		indexOp++;
		return super.get(index);
	}
	
	@Override
	public E remove(int index) {
		midListOp++;
		return super.remove(index);
	}
	
	@Override
	public void add(int index, E element) {
		midListOp++;
		super.add(index, element);
	}
	
	@Override
	public boolean contains(Object o) {
		containsOp++;
		return super.contains(o);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateOperationsAndSize(index, indexOp, midListOp, containsOp, size());
	}
}
