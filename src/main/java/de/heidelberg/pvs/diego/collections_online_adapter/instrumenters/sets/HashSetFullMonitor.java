package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class HashSetFullMonitor<E> extends HashSet<E> {

	private static final long serialVersionUID = 20170101L;
	
	SetAllocationOptimizer context;

	private int containsOp;
	private int iterateOp;
	
	public HashSetFullMonitor(int initialCapacity, SetAllocationOptimizer context) {
		super(initialCapacity);
		this.context = context;
	}
	
	public HashSetFullMonitor(Collection<? extends E> set, SetAllocationOptimizer context) {
		super(set);
		this.context = context;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.context.updateOperationsAndSize(containsOp, iterateOp, size());
	}
	
	public boolean contains(Object o) {
		this.containsOp++;
		return super.contains(o);
	}

	public Iterator<E> iterator() {
		this.iterateOp++;
		return super.iterator();
	}

	public boolean containsAll(Collection<?> c) {
		this.containsOp++;
		return super.containsAll(c);
	}

}
