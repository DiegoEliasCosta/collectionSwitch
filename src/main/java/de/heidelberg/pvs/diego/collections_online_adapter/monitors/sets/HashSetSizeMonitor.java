package de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets;

import java.util.Collection;
import java.util.HashSet;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class HashSetSizeMonitor<E> extends HashSet<E> {
	
	private static final long serialVersionUID = 20170101L;
	private AllocationOptimizer context;
	private int index;
	
	public HashSetSizeMonitor(AllocationOptimizer context, int index) {
		super();
		this.context = context;
		this.index = index;
	}

	public HashSetSizeMonitor(Collection<? extends E> c, AllocationOptimizer context, int index) {
		super(c);
		this.context = context;
		this.index = index;
	}

	public HashSetSizeMonitor(int initialCapacity, float loadFactor, AllocationOptimizer context, int index) {
		super(initialCapacity, loadFactor);
		this.context = context;
		this.index = index;
	}

	public HashSetSizeMonitor(int initialCapacity, AllocationOptimizer context, int index) {
		super(initialCapacity);
		this.context = context;
		this.index = index;
		
	}


	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(index, size());
	}

}
