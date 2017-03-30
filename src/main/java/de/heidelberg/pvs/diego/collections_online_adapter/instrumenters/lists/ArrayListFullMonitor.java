package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists;

import java.util.ArrayList;
import java.util.Collection;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ArrayListFullMonitor<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 20170101L;
	private ListAllocationOptimizer context;
	private int indexOp;
	private int midListOp;
	private int containsOp;
	

	public ArrayListFullMonitor(int initialCapacity, ListAllocationOptimizer context) {
		super(initialCapacity);
		this.context = context;
	}

	public ArrayListFullMonitor(Collection<? extends E> c, ListAllocationOptimizer context) {
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
		context.updateOperationsAndSize(indexOp, midListOp, containsOp, size());
	}

}
