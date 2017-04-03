package de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets;

import java.util.Collection;
import java.util.HashSet;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class HashSetSizeMonitor<E> extends HashSet<E> {
	
	private static final long serialVersionUID = 20170101L;
	private SetAllocationOptimizer context;
	
	public HashSetSizeMonitor(SetAllocationOptimizer context) {
		super();
		this.context = context;
	}

	public HashSetSizeMonitor(Collection<? extends E> c, SetAllocationOptimizer context) {
		super(c);
		this.context = context;
	}

	public HashSetSizeMonitor(int initialCapacity, float loadFactor, SetAllocationOptimizer context) {
		super(initialCapacity, loadFactor);
		this.context = context;
	}

	public HashSetSizeMonitor(int initialCapacity, SetAllocationOptimizer context) {
		super(initialCapacity);
		this.context = context;
	}


	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(size());
	}

}
