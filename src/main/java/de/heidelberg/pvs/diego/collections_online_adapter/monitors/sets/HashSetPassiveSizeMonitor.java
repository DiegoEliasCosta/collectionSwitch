package de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets;

import java.util.Collection;
import java.util.HashSet;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class HashSetPassiveSizeMonitor<E> extends HashSet<E> {
	
	private static final long serialVersionUID = 20170101L;
	private AllocationOptimizer context;
	
	public HashSetPassiveSizeMonitor(AllocationOptimizer context) {
		super();
		this.context = context;
	}

	public HashSetPassiveSizeMonitor(Collection<? extends E> c, AllocationOptimizer context, int index) {
		super(c);
		this.context = context;
	}

	public HashSetPassiveSizeMonitor(int initialCapacity, float loadFactor, AllocationOptimizer context, int index) {
		super(initialCapacity, loadFactor);
		this.context = context;
	}

	public HashSetPassiveSizeMonitor(int initialCapacity, AllocationOptimizer context, int index) {
		super(initialCapacity);
		this.context = context;
		
	}


	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(0, size());
	}

}
