package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class ListActiveSizeMonitor<E> implements List<E> {

	private AllocationOptimizer optimizer;
	
	private List<E> list;

	private final int monitorIndex;
	
	public ListActiveSizeMonitor(List<E> list, AllocationOptimizer optimizer, int index) {
		super();
		this.optimizer = optimizer;
		this.list = list;
		this.monitorIndex = index;
	}
	
	// --------------------------------------------------------------------
	// --------------------------- ADD METHODS ----------------------------
	// --------------------------------------------------------------------

	public boolean add(E e) {
		boolean add = list.add(e);
		// UPDATE
		this.optimizer.updateSize(monitorIndex, size());
		return add;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean addAll = list.addAll(c);
		// UPDATE
		this.optimizer.updateSize(monitorIndex, size());
		return addAll;
	}
	
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean addAll = list.addAll(index, c);
		// UPDATE
		this.optimizer.updateSize(monitorIndex, size());;
		return addAll;
	}
	
	public void add(int index, E element) {
		list.add(index, element);
		// UPDATE
		this.optimizer.updateSize(monitorIndex, size());
	}
	
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
