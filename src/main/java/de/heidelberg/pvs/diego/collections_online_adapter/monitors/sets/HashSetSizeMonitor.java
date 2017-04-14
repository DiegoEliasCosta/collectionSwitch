package de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets;

import java.util.Collection;
import java.util.HashSet;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class HashSetSizeMonitor<E> extends HashSet<E> {
	
	private static final long serialVersionUID = 20170101L;
	private SetAllocationOptimizer context;
	private int index;
	
	public HashSetSizeMonitor(SetAllocationOptimizer context, int index) {
		super();
		this.context = context;
		this.index = index;
	}

	public HashSetSizeMonitor(Collection<? extends E> c, SetAllocationOptimizer context, int index) {
		super(c);
		this.context = context;
		this.index = index;
	}

	public HashSetSizeMonitor(int initialCapacity, float loadFactor, SetAllocationOptimizer context, int index) {
		super(initialCapacity, loadFactor);
		this.context = context;
		this.index = index;
	}

	public HashSetSizeMonitor(int initialCapacity, SetAllocationOptimizer context, int index) {
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
