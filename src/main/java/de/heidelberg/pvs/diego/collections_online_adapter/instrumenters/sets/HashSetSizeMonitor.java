package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets;

import java.util.Collection;
import java.util.HashSet;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class HashSetSizeMonitor<E> extends HashSet<E> {
	
	private static final long serialVersionUID = 20170101L;
	private SetAllocationContext<E> context;
	
	public HashSetSizeMonitor(SetAllocationContext<E> context) {
		super();
		this.context = context;
	}

	public HashSetSizeMonitor(Collection<? extends E> c, SetAllocationContext<E> context) {
		super(c);
		this.context = context;
	}

	public HashSetSizeMonitor(int initialCapacity, float loadFactor, SetAllocationContext<E> context) {
		super(initialCapacity, loadFactor);
		this.context = context;
	}

	public HashSetSizeMonitor(int initialCapacity, SetAllocationContext<E> context) {
		super(initialCapacity);
		this.context = context;
	}


	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(size());
	}

}
