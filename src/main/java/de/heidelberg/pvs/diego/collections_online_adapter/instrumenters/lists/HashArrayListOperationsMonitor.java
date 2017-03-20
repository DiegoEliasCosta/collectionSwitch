package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;

public class HashArrayListOperationsMonitor<E> extends HashArrayList<E>{
	
	private static final long serialVersionUID = 20170101L;
	private ListAllocationContext<E> context;
	private int indexOp;
	private int midListOp;
	private int containsOp;

	public HashArrayListOperationsMonitor(int initialCapacity, ListAllocationContext<E> context) {
		super(initialCapacity);
		this.context = context;
	}
	
	public HashArrayListOperationsMonitor(List<? extends E> lists, ListAllocationContext<E> context) {
		super(lists);
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
		context.updateOperationsAndSize(indexOp, midListOp, containsOp, size());
	}


}
