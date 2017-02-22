package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;

public class HashArrayListMonitor<E> extends HashArrayList<E>{
	
	private static final long serialVersionUID = 20170101L;
	private ListAllocationContext<E> context;
	private int indexOp;
	private int midListOp;
	private int containsOp;

	public HashArrayListMonitor(int initialCapacity, ListAllocationContext<E> context) {
		super(initialCapacity);
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
