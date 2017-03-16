package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ListSizeMonitor;

public class FirstSamplesListAllocationContext<E> implements ListAllocationContext<E> {

	private static final int ARRAY_THRESHOLD = 30;

	private static final int SAMPLES = 10;
	
	// Volatile
	private volatile int initialCapacity = 10;
	private volatile int count = 0;

	// Monitored data
	private int[] sizes = new int[SAMPLES];

	private CollectionTypeEnum collectionType;

	public FirstSamplesListAllocationContext(CollectionTypeEnum collectionType) {
		super();
		this.collectionType = collectionType;
	}

	public List<E> createList() {

		switch (collectionType) {
		case ARRAY:
			return isOnline() ? new ArrayListOperationsMonitor<E>(initialCapacity, this)
					: new ArrayList<E>(initialCapacity);
		case LINKED:
			return isOnline() ? new LinkedListOperationsMonitor<E>(this) : new LinkedList<E>();
		case HASH:
			// At this point the algorithm won't be online anymore
			return new HashArrayList<E>(initialCapacity);
		}

		return null;
	}

	@Override
	public List<E> createList(int initialCapacity) {
		
		if(isOnline()) {
			this.initialCapacity = initialCapacity;
		}
		return createList();

	}

	@Override
	public List<E> createList(Collection<? extends E> c) {

		switch (collectionType) {
		case ARRAY:
			return isOnline() ? new ListSizeMonitor<E>(new ArrayList<E>(c), this) : new ArrayList<E>(c);
		case LINKED:
			return isOnline() ? new ListSizeMonitor<E>(new LinkedList<E>(c), this) : new LinkedList<E>();
		case HASH:
			// At this point the algorithm won't be online anymore
			return new HashArrayList<E>(c);
		}
		return null;
	}

	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {

		// FIXME: This needs to be thread-safe
		int copyCount = count++;
		this.sizes[copyCount] = size;

		if (copyCount >= SAMPLES) {
			updateContext();
		}

	}

	private synchronized void updateContext() {
		int summedSize = 0;
		for (int i = 0; i < SAMPLES; i++) {
			summedSize += this.sizes[i];
		}
		// This needs to be synchronized?
		this.initialCapacity = summedSize / SAMPLES;
		
		// FIXME: This is too arbitrary
		if(this.initialCapacity < ARRAY_THRESHOLD) {
			collectionType = CollectionTypeEnum.ARRAY;
		}
		
	}

	public boolean isOnline() {
		return count < SAMPLES;
	}

	@Override
	public void updateSize(int size) {

		// FIXME: This needs to be thread-safe
		int copyCount = count++;
		this.sizes[copyCount] = size;
		
		if (copyCount >= SAMPLES) {
			updateContext();
		}

	}

}
