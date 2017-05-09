package de.heidelberg.pvs.diego.collections_online_adapter.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LazyArrayList<E> implements List<E> {
	
	List<E> arrayList;

	private void checkAndLazilyCreate() {
		if(arrayList == null) {
			arrayList = new ArrayList<E>();
		}
		
	}
	
	public boolean add(E e) {
		checkAndLazilyCreate();
		return arrayList.add(e);
	}

	public void add(int index, E element) {
		checkAndLazilyCreate();
		arrayList.add(index, element);
	}

	public boolean addAll(Collection<? extends E> c) {
		checkAndLazilyCreate();
		return arrayList.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		checkAndLazilyCreate();
		return arrayList.addAll(index, c);
	}

	public void clear() {
		checkAndLazilyCreate();
		arrayList.clear();
	}

	public boolean contains(Object o) {
		checkAndLazilyCreate();
		return arrayList.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		checkAndLazilyCreate();
		return arrayList.containsAll(c);
	}

	public boolean equals(Object o) {
		checkAndLazilyCreate();
		return arrayList.equals(o);
	}

	public E get(int index) {
		checkAndLazilyCreate();
		return arrayList.get(index);
	}

	public int hashCode() {
		checkAndLazilyCreate();
		return arrayList.hashCode();
	}

	public int indexOf(Object o) {
		checkAndLazilyCreate();
		return arrayList.indexOf(o);
	}

	public boolean isEmpty() {
		checkAndLazilyCreate();
		return arrayList.isEmpty();
	}

	public Iterator<E> iterator() {
		checkAndLazilyCreate();
		return arrayList.iterator();
	}

	public int lastIndexOf(Object o) {
		checkAndLazilyCreate();
		return arrayList.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		checkAndLazilyCreate();
		return arrayList.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		checkAndLazilyCreate();
		return arrayList.listIterator(index);
	}

	public E remove(int index) {
		checkAndLazilyCreate();
		return arrayList.remove(index);
	}

	public boolean remove(Object o) {
		checkAndLazilyCreate();
		return arrayList.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		checkAndLazilyCreate();
		return arrayList.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		checkAndLazilyCreate();
		return arrayList.retainAll(c);
	}

	public E set(int index, E element) {
		checkAndLazilyCreate();
		return arrayList.set(index, element);
	}

	

	public int size() {
		checkAndLazilyCreate();
		return arrayList.size();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		checkAndLazilyCreate();
		return arrayList.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		checkAndLazilyCreate();
		return arrayList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		checkAndLazilyCreate();
		return arrayList.toArray(a);
	}
	
	

}
