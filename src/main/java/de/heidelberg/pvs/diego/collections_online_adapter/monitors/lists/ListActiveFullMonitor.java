package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListActiveFullMonitor<E> implements List<E>{

	private List<E> list;
	private ListMetrics state;

	public ListActiveFullMonitor(List<E> list, ListMetrics optimizer) {
		super();
		this.list = list;
		this.state = optimizer;
		state.updateSize(list.size()); // First Record
	}

	/**
	 * MONITORED OPERATIONS
	 */

	public boolean contains(Object o) {
		this.state.updateContainsOp(1);
		return list.contains(o);
	}

	public Iterator<E> iterator() {
		this.state.updateIteratorOp(1);
		return list.iterator();
	}

	public boolean add(E e) {
		this.state.updateSize(1);
		return list.add(e);
	}
	
	public boolean containsAll(Collection<?> c) {
		this.state.updateContainsOp(c.size());
		return list.containsAll(c);
	}
	
	public boolean addAll(Collection<? extends E> c) {
		this.state.updateSize(c.size());
		return list.addAll(c);
	}
	
	public boolean addAll(int index, Collection<? extends E> c) {
		this.state.updateSize(c.size());
		return list.addAll(index, c);
	}
	
	public boolean retainAll(Collection<?> c) {
		this.state.updateContainsOp(c.size());
		return list.retainAll(c);
	}
	
	public E get(int index) {
		this.state.updateIndexOp(1);
		return list.get(index);
	}
	
	public void add(int index, E element) {
		this.state.updateIndexOp(1);
		this.state.updateSize(1);
		list.add(index, element);
	}
	
	public int indexOf(Object o) {
		this.state.updateContainsOp(1);
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		this.state.updateContainsOp(1);
		return list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		this.state.updateIteratorOp(1);
		return list.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		this.state.updateIteratorOp(1);
		return list.listIterator(index);
	}
	
	public E set(int index, E element) {
		state.updateIndexOp(1);
		return list.set(index, element);
	}
	
	/**
	 * NON_MONITORED OPERATIONS
	 */

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
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

	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
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

	public E remove(int index) {
		return list.remove(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	
}
