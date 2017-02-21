package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters;

import java.util.ArrayList;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class ArrayListMonitor<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 20170101L;
	private ListAllocationContext context;
	private int indexOp;
	private int midListOp;
	

	public ArrayListMonitor(int initialCapacity, ListAllocationContext context) {
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
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateOperations(indexOp, midListOp, size());
	}

}
