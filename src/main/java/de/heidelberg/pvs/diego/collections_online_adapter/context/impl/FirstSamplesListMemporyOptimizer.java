package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ListSizeMonitor;

/**
 * Simple optimizer that uses the first samples of the allocation-site to determine the best collection.
 * Used only for memory optimizations.
 * 
 * @author Diego
 *
 * @param <E>
 */
public class FirstSamplesListMemporyOptimizer<E> implements ListAllocationOptimizer<E> {

	private static final int ARRAY_THRESHOLD = 30;

	private static final int SAMPLES = 10;

	// Volatile
	private volatile int initialCapacity = 10;
	private volatile int count = 0;

	// Monitored data
	private int[] sizes = new int[SAMPLES];

	private CollectionTypeEnum championCollectionType;

	private ListAllocationContext<E> context;

	public FirstSamplesListMemporyOptimizer(ListAllocationContext<E> context, CollectionTypeEnum collectionType) {
		super();
		this.championCollectionType = collectionType;
		this.context = context;
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
		if (this.initialCapacity < ARRAY_THRESHOLD) {
			championCollectionType = CollectionTypeEnum.ARRAY;
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

	@Override
	public boolean isSleeping() {
		// No sleeping behavior
		return false;
	}

	@Override
	public List<E> createListMonitor(List<? extends E> list) {

		switch (championCollectionType) {
		case ARRAY:
			return new ArrayListSizeMonitor<>(list, this.context);
		case LINKED:
			return new LinkedListSizeMonitor<>(list, this.context);
		case HASH:
			return new ListSizeMonitor<>(new HashArrayList<>(list), this.context);
		default:
			break;
		}
		
		return null;
	}

	@Override
	public List<E> createListMonitor(int inicialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

}
