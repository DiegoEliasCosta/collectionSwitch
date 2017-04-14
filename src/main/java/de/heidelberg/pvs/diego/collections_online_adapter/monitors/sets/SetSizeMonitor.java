package de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class SetSizeMonitor<E> implements Set<E> {
	
	private SetAllocationOptimizer context;
	
	private Set<E> set;

	private int index;
	
	public SetSizeMonitor(Set<E> set, SetAllocationOptimizer context, int index) {
		super();
		this.context = context;
		this.set = set;
		this.index = index;
	}


	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(index, size());
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


	public boolean contains(Object o) {
		return set.contains(o);
	}


	public Iterator<E> iterator() {
		return set.iterator();
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


	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
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
