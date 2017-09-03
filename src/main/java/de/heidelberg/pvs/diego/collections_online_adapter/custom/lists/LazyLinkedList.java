package de.heidelberg.pvs.diego.collections_online_adapter.custom.lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LazyLinkedList<E> implements List<E> {
	
	List<E> linkedList;

	public void add(int index, E element) {
		checkAndLazilyCreate();
		linkedList.add(index, element);
	}

	private void checkAndLazilyCreate() {
		if(linkedList == null) {
			linkedList = new LinkedList<E>();
		}
		
	}

	public boolean add(E e) {
		checkAndLazilyCreate();
		return linkedList.add(e);
	}

	public boolean addAll(Collection<? extends E> c) {
		checkAndLazilyCreate();
		return linkedList.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		checkAndLazilyCreate();
		return linkedList.addAll(index, c);
	}

	public void clear() {
		checkAndLazilyCreate();
		linkedList.clear();
	}

	public boolean contains(Object o) {
		checkAndLazilyCreate();
		return linkedList.contains(o);
	}

	public boolean containsAll(Collection c) {
		checkAndLazilyCreate();
		return linkedList.containsAll(c);
	}

	public boolean equals(Object o) {
		checkAndLazilyCreate();
		return linkedList.equals(o);
	}

	public E get(int index) {
		checkAndLazilyCreate();
		return linkedList.get(index);
	}

	public int hashCode() {
		checkAndLazilyCreate();
		return linkedList.hashCode();
	}

	public int indexOf(Object o) {
		checkAndLazilyCreate();
		return linkedList.indexOf(o);
	}

	public boolean isEmpty() {
		checkAndLazilyCreate();
		return linkedList.isEmpty();
	}

	public Iterator iterator() {
		checkAndLazilyCreate();
		return linkedList.iterator();
	}

	public int lastIndexOf(Object o) {
		checkAndLazilyCreate();
		return linkedList.lastIndexOf(o);
	}

	public ListIterator listIterator() {
		checkAndLazilyCreate();
		return linkedList.listIterator();
	}

	public ListIterator listIterator(int index) {
		checkAndLazilyCreate();
		return linkedList.listIterator(index);
	}

	public E remove(int index) {
		checkAndLazilyCreate();
		return linkedList.remove(index);
	}

	public boolean remove(Object o) {
		checkAndLazilyCreate();
		return linkedList.remove(o);
	}

	public boolean removeAll(Collection c) {
		checkAndLazilyCreate();
		return linkedList.removeAll(c);
	}

	public boolean retainAll(Collection c) {
		checkAndLazilyCreate();
		return linkedList.retainAll(c);
	}

	public E set(int index, E element) {
		checkAndLazilyCreate();
		return linkedList.set(index, element);
	}

	public int size() {
		checkAndLazilyCreate();
		return linkedList.size();
	}

	public List subList(int fromIndex, int toIndex) {
		checkAndLazilyCreate();
		return linkedList.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		checkAndLazilyCreate();
		return linkedList.toArray();
	}

	public Object[] toArray(Object[] a) {
		return linkedList.toArray(a);
	}
	
	
	

}
