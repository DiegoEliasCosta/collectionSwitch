package de.heidelberg.pvs.diego.collections_online_adapter.custom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import org.eclipse.collections.impl.bag.mutable.HashBag;


public class HashArrayList<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 20170101L;
	
	private HashBag<E> bag;

	public HashArrayList() {
		super();
		bag = new HashBag();
	}
	
	public HashArrayList(int initialCapacity) {
		super(initialCapacity);
		bag = new HashBag(initialCapacity);
	}
	

	public HashArrayList(Collection<? extends E> c) {
		super();
		bag = new HashBag();
		bag.addAll(c);
	}
	
	
	/* ---------------------------------------------------------- */
	/* ------------------------   CONTAINS----------------------- */
	/* ---------------------------------------------------------- */
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return bag.containsAll(c);
	}
	
	@Override
	public boolean contains(Object o) {
		return bag.contains(o);
	}
	
	/* ---------------------------------------------------------- */
	/* ------------------------   ADD   ------------------------- */
	/* ---------------------------------------------------------- */
	
	@Override
	public boolean add(E e) {
		bag.add(e);
		return super.add(e);
	}
	
	
	@Override
	public void add(int index, E element) {
		bag.add(element);
		super.add(index, element);
	}

	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		bag.addAll(bag);
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		bag.addAll(c);
		return super.addAll(index, c);
	}
	
	@Override
	public E set(int index, E element) {
		E obj = super.set(index, element);
		bag.remove(obj);
		bag.add(element);
		return obj;
	}
	
	/* ---------------------------------------------------------- */
	/* ------------------------  REMOVE  ------------------------ */
	/* ---------------------------------------------------------- */
	
	@Override
	public boolean remove(Object o) {
		bag.remove(o);
		return super.remove(o);
	}

	
	@Override
	public E remove(int index) {
		bag.remove(super.get(index));
		return super.remove(index);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		bag.remove(c);
		return super.removeAll(c);
	}
	
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		bag.removeIf(filter);
		return super.removeIf(filter);
	}
	
	
	@Override
	public void clear() {
		bag.clear();
		super.clear();
	}
	
}
