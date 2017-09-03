package de.heidelberg.pvs.diego.collections_online_adapter.custom.sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LazyHashSet<E> implements Set<E>{
	
	Set<E> hashSet;

	private void checkAndLazilyCreate() {
		if(hashSet == null) {
			hashSet = new HashSet<E>();
		}
	}

	
	public boolean add(E e) {
		checkAndLazilyCreate();
		return hashSet.add(e);
	}

	public boolean addAll(Collection<? extends E> c) {
		checkAndLazilyCreate();
		return hashSet.addAll(c);
	}

	public void clear() {
		checkAndLazilyCreate();
		hashSet.clear();
	}

	public boolean contains(Object o) {
		checkAndLazilyCreate();
		return hashSet.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		checkAndLazilyCreate();
		return hashSet.containsAll(c);
	}

	public boolean equals(Object o) {
		checkAndLazilyCreate();
		return hashSet.equals(o);
	}

	public int hashCode() {
		checkAndLazilyCreate();
		return hashSet.hashCode();
	}

	public boolean isEmpty() {
		checkAndLazilyCreate();
		return hashSet.isEmpty();
	}

	public Iterator<E> iterator() {
		checkAndLazilyCreate();
		return hashSet.iterator();
	}

	public boolean remove(Object o) {
		checkAndLazilyCreate();
		return hashSet.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		checkAndLazilyCreate();
		return hashSet.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		checkAndLazilyCreate();
		return hashSet.retainAll(c);
	}

	public int size() {
		checkAndLazilyCreate();
		return hashSet.size();
	}

	public Object[] toArray() {
		checkAndLazilyCreate();
		return hashSet.toArray();
	}

	public <T> T[] toArray(T[] a) {
		checkAndLazilyCreate();
		return hashSet.toArray(a);
	}
	
	

}
