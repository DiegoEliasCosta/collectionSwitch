package de.heidelberg.pvs.diego.collections_online_adapter.adaptive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.heidelberg.pvs.diego.collections_online_adapter.custom.lists.HashArrayList;

public class AdaptiveList<E> implements List<E> {

	private List<E> list;

	private boolean transformed;

	private static final int TURNING_POINT = 80;

	public AdaptiveList() {
		list = new ArrayList<E>();
	}

	public AdaptiveList(int initialCapacity) {

		if (initialCapacity <= TURNING_POINT) {
			list = new ArrayList<E>(initialCapacity);
		} else {
			list = new HashArrayList<E>(initialCapacity);
			transformed = true;
		}
	}

	public AdaptiveList(Collection<? extends E> c) {

		if (c.size() < TURNING_POINT) {
			list = new ArrayList<E>(c);
		} else {
			list = new HashArrayList<>(c);
			transformed = true;
		}
	}
	
	private void manageImplementation(int delta) {
		int newSize = delta + size();
		if(!transformed && newSize > TURNING_POINT) {
			list = new HashArrayList<>(list);
			transformed = true;
		}
	}
	
	// -------------------------------------------------
	// 						ADD
	// -------------------------------------------------

	
	public boolean add(E e) {
		if(!transformed) {
			manageImplementation(1);
		}
		return list.add(e);
	}

	public boolean addAll(Collection<? extends E> c) {
		if(!transformed) {
			manageImplementation(c.size());
		}
		return list.addAll(c);
	}
	
	public boolean addAll(int index, Collection<? extends E> c) {
		if(!transformed) {
			manageImplementation(c.size());
		}
		return list.addAll(index, c);
	}
	
	public void add(int index, E element) {
		if(!transformed) {
			manageImplementation(1);
		}
		list.add(index, element);
	}
	
	// ----------------------------------------
	
	
	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Iterator<E> iterator() {
		return list.iterator();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}
	
	public boolean remove(Object o) {
		return list.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public void clear() {
		list.clear();
	}

	public boolean equals(Object o) {
		return list.equals(o);
	}

	public int hashCode() {
		return list.hashCode();
	}

	public E get(int index) {
		return list.get(index);
	}

	public E set(int index, E element) {
		return list.set(index, element);
	}

	public E remove(int index) {
		return list.remove(index);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return list.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}
