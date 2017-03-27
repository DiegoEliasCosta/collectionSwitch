package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class HashSetOperationMonitor<E> extends HashSet<E> {

	private static final long serialVersionUID = 20170101L;
	
	SetAllocationContext<E> context;

	private int containsOp;
	private int iterateOp;
	
	public HashSetOperationMonitor(int initialCapacity, SetAllocationContext<E> context) {
		super(initialCapacity);
		this.context = context;
	}
	
	public HashSetOperationMonitor(Collection<? extends E> set, SetAllocationContext<E> context) {
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
