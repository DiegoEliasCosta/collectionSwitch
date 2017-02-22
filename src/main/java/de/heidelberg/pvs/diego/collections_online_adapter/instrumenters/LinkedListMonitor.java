package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters;

import java.util.Collection;
import java.util.LinkedList;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class LinkedListMonitor<E> extends LinkedList<E> {

	private static final long serialVersionUID = 20170101L;
	
	// Monitor
	private ListAllocationContext<E> context;
	private int indexOp;
	private int midListOp;
	private int containsOp;
	
	public LinkedListMonitor(ListAllocationContext<E> context) {
		super();
		this.context = context;
	}

	public LinkedListMonitor(Collection<? extends E> c, ListAllocationContext<E> context) {
		super(c);
		this.context = context;
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
		context.updateOperations(indexOp, midListOp, size());
	}
}
