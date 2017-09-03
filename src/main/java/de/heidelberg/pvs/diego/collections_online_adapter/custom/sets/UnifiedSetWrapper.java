package de.heidelberg.pvs.diego.collections_online_adapter.custom.sets;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class UnifiedSetWrapper<E> implements Set<E>{
	
	Set<E> set;

	public UnifiedSetWrapper() {
		super();
		set = new UnifiedSet();
	}

	public boolean add(E e) {
		return set.add(e);
	}

	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	public void clear() {
		set.clear();
	}

	public boolean contains(Object o) {
		return set.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public boolean equals(Object o) {
		return set.equals(o);
	}

	public int hashCode() {
		return set.hashCode();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public Iterator<E> iterator() {
		return set.iterator();
	}

	public boolean remove(Object o) {
		return set.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	public int size() {
		return set.size();
	}

	public Object[] toArray() {
		return set.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

		

}
