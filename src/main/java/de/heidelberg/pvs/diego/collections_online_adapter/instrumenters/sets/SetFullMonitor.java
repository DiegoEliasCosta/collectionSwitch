package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class SetFullMonitor<E> implements Set<E> {
	
	private Set<E> set;
	private int containsOp;
	private int iterateOp;
	private SetAllocationOptimizer context;

	public SetFullMonitor(Set<E> set, SetAllocationOptimizer context) {
		super();
		this.set = set;
		this.context = context;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.context.updateOperationsAndSize(containsOp, iterateOp, size());
	}
	
	public boolean contains(Object o) {
		this.containsOp++;
		return set.contains(o);
	}

	public Iterator<E> iterator() {
		this.iterateOp++;
		return set.iterator();
	}

	public boolean containsAll(Collection<?> c) {
		this.containsOp++;
		return set.containsAll(c);
	}
	
	
	/* --------------------------------------------------------- */
	/* -------------------- DELEGATE METHODS --------------------*/
	/* --------------------------------------------------------- */

	public int size() {
		return set.size();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}


	
	public Object[] toArray() {
		return set.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	public boolean add(E e) {
		return set.add(e);
	}

	public boolean remove(Object o) {
		return set.remove(o);
	}


	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	public void clear() {
		set.clear();
	}

	public boolean equals(Object o) {
		return set.equals(o);
	}

	public int hashCode() {
		return set.hashCode();
	}

	
	
	

}
