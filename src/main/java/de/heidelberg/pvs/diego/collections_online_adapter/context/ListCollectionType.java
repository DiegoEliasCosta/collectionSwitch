package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.custom.lists.AdaptiveList;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.lists.HashArrayList;

public enum ListCollectionType {

	JDK_ARRAYLIST, JDK_LINKEDLIST, SWITCH_HASHARRAYLIST, SWITCH_ADAPTIVELIST;

	public <E> List<E> createList(int initialCapacity) {

		List<E> list;

		switch (this) {

		case JDK_ARRAYLIST:
			list = new ArrayList<E>(initialCapacity);

		case SWITCH_ADAPTIVELIST:
			list = new AdaptiveList<E>(initialCapacity);
		
		case SWITCH_HASHARRAYLIST:
			list = new HashArrayList<E>(initialCapacity);

		case JDK_LINKEDLIST:
			list = new LinkedList<E>();

		default:
			list = new ArrayList<E>(initialCapacity);
		}

		return list;
	}

	public <E> List<E> createList() {

		List<E> list;

		switch (this) {

		case JDK_ARRAYLIST:
			list = new ArrayList<E>();

		case SWITCH_HASHARRAYLIST:
			list = new HashArrayList<E>();

		case JDK_LINKEDLIST:
			list = new LinkedList<E>();

		default:
			list = new ArrayList<E>();
		}

		return list;
	}

	public <E> List<E> createList(Collection<? extends E> c) {
		
		List<E> list;

		switch (this) {

		case JDK_ARRAYLIST:
			list = new ArrayList<E>(c);

		case SWITCH_HASHARRAYLIST:
			list = new HashArrayList<E>(c);

		case JDK_LINKEDLIST:
			list = new LinkedList<E>(c);

		default:
			list = new ArrayList<E>(c);
		}

		return list;
		
	}

}
