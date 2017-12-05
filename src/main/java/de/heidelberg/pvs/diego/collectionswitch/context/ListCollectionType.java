package de.heidelberg.pvs.diego.collectionswitch.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collectionswitch.adaptive.AdaptiveList;
import de.heidelberg.pvs.diego.collectionswitch.custom.lists.HashArrayList;

public enum ListCollectionType {

	JDK_ARRAYLIST, 
	JDK_LINKEDLIST, 
	ONLINEADAPTER_HASHARRAYLIST, 
	ONLINEADAPTER_ADAPTIVELIST;

	public <E> List<E> createList(int initialCapacity) {

		switch (this) {

		case JDK_ARRAYLIST:
			return new ArrayList<E>(initialCapacity);

		case ONLINEADAPTER_ADAPTIVELIST:
			return new AdaptiveList<E>(initialCapacity);
		
		case ONLINEADAPTER_HASHARRAYLIST:
			return new HashArrayList<E>(initialCapacity);

		case JDK_LINKEDLIST:
			return new LinkedList<E>();

		default:
			return new ArrayList<E>(initialCapacity);
		}

	}

	public <E> List<E> createList() {

		switch (this) {

		case JDK_ARRAYLIST:
			return new ArrayList<E>();

		case ONLINEADAPTER_HASHARRAYLIST:
			return new HashArrayList<E>();
			
		case ONLINEADAPTER_ADAPTIVELIST:
			return new AdaptiveList<E>();

		case JDK_LINKEDLIST:
			return new LinkedList<E>();

		default:
			return new ArrayList<E>();
			
		}

	}

	public <E> List<E> createList(Collection<? extends E> c) {
		
		switch (this) {

		case JDK_ARRAYLIST:
			return new ArrayList<E>(c);

		case ONLINEADAPTER_HASHARRAYLIST:
			return new HashArrayList<E>(c);
			
		case ONLINEADAPTER_ADAPTIVELIST:
			return new AdaptiveList<E>(c);

		case JDK_LINKEDLIST:
			return new LinkedList<E>(c);

		default:
			return new ArrayList<E>(c);
		}

		
	}

}
