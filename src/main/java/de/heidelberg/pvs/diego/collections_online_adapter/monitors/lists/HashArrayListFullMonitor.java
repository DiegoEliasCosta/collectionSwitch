package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.util.Collection;

import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class HashArrayListFullMonitor<E> extends HashArrayList<E>{
	
	private static final long serialVersionUID = 20170101L;
	private ListAllocationOptimizer context;
	private int indexOp;
	private int midListOp;
	private int containsOp;
	private int index;
	
	public HashArrayListFullMonitor(ListAllocationOptimizer context, int index) {
		this.context = context;
		this.index = index;
	}

	public HashArrayListFullMonitor(int initialCapacity, ListAllocationOptimizer context, int index) {
		super(initialCapacity);
		this.context = context;
		this.index = index;
	}
	
	public HashArrayListFullMonitor(Collection<? extends E> lists, ListAllocationOptimizer context, int index) {
		super(lists);
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
