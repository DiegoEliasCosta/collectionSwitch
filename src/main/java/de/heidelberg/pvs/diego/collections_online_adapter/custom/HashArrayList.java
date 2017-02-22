package de.heidelberg.pvs.diego.collections_online_adapter.custom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class HashArrayList<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 20170101;
	
	private HashMap<E, Integer> map;

	public HashArrayList() {
		map = new HashMap<E, Integer>();
	}

	public HashArrayList(Collection<? extends E> c) {
		map = new HashMap<E, Integer>(c.size());
		for (E e : c) {
			add(e);
		}
	}

	public HashArrayList(int initialCapacity) {
		super(initialCapacity);
		new HashMap<E, Integer>(initialCapacity);
	}

	@Override
	public void ensureCapacity(int minCapacity) {
		super.ensureCapacity(minCapacity);
		HashMap<E, Integer> oldMap = map;
		map = new HashMap<E, Integer>(minCapacity * 10 / 7);
		map.putAll(oldMap);
	}

	@Override
	public boolean add(E e) {
		map.put(e, size());
		return super.add(e);
	}

	@Override
	public E set(int index, E element) {
		map.remove(get(index));
		map.put(element, index);
		return super.set(index, element);
	}

	@Override
	public int indexOf(Object o) {
		Integer index = map.get(o);
		if (index == null) {
			return -1;
		}

		return index;
	}

	@Override
	public boolean contains(Object o) {
		return map.containsKey(o);
	}
	
}
