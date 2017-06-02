package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.util.ArrayList;
import java.util.Collection;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class ArrayListSizeMonitor<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 20170101L;
	private AllocationOptimizer context;
	
	int index;
	
	public ArrayListSizeMonitor(AllocationOptimizer context, int index) {
		super();
		this.context = context;
		this.index = index;
	}
	
	public ArrayListSizeMonitor(int initialCapacity, AllocationOptimizer context, int index) {
		super(initialCapacity);
		this.context = context;
		this.index = index;

	}

	public ArrayListSizeMonitor(Collection<? extends E> c, AllocationOptimizer context, int index) {
		super(c);
		this.context = context;
		this.index = index;
	}

	@Override
	protected void finalize() throws Throwable {
		context.updateSize(index, size());
	}
	
	@Override
	public boolean add(E e) {
		return super.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return super.addAll(c);
	}
	
	public void setOptimizer(AllocationOptimizer optimizer) {
		this.context = optimizer;
	}


}
