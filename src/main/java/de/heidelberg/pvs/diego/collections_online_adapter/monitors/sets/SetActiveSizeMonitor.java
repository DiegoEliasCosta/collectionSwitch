package de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class SetActiveSizeMonitor<E> implements Set<E> {
	
	private Set<E> set;
	private AllocationOptimizer context;
	private final int index;

	public SetActiveSizeMonitor(Set<E> set, AllocationOptimizer context, int index) {
		super();
		this.set = set;
		this.context = context;
		this.index = index;
	}
	
	public boolean add(E e) {
		boolean add = set.add(e);
		this.context.updateSize(index, size());
		return add;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean addAll = set.addAll(c);
		this.context.updateSize(index, size());
		return addAll;
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

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}
	
	public Object[] toArray() {
		return set.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}


	
	public boolean remove(Object o) {
		return set.remove(o);
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
