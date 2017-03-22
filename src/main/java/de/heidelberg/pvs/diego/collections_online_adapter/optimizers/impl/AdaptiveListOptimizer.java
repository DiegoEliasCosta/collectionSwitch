package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.impl;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.HashArrayListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.ListAllocationOptimizer;

public class AdaptiveListOptimizer<E> implements ListAllocationOptimizer<E> {

	private static final int WINDOW_SIZE = 5;

	private static final int SIZE_SAMPLE = 0;

	private static final int OPERATION_THRESHOLD = 0;

	private static final int ARRAY_THRESHOLD = 0;

	// Monitoring data
	private CollectionTypeEnum collectionTypes[] = new CollectionTypeEnum[WINDOW_SIZE];

	private int sizes[] = new int[WINDOW_SIZE];
	private int count = 0;
	private CollectionTypeEnum championCollectionType;

	ListAllocationContext<E> context;

	// Control flags
	private boolean isOnline = true;
	private boolean isSleeping = false;

	private int initialCapacity = 10;

	private boolean fullAnalysis;


	public AdaptiveListOptimizer(ListAllocationContext<E> context, CollectionTypeEnum collectionTypeEnum,
			int initialCapacity) {
		super();
		this.championCollectionType = collectionTypeEnum;
		this.initialCapacity = initialCapacity;
		this.context = context;
	}

	@Override
	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {
		
		int copyCount = count++;
		
		sizes[copyCount] = size;
		
		// Implement here the decision-making algorithm based on the threshold
		// TODO

	}

	@Override
	public void updateSize(int size) {

		int copyCount = count++;
		sizes[copyCount] = size;

		if (copyCount >= WINDOW_SIZE) {
			analyzeSize();
		}

	}

	private synchronized void analyzeSize() {

		int summedSize = 0;
		for (int i = 0; i < WINDOW_SIZE; i++) {
			summedSize += this.sizes[i];
		}
		this.initialCapacity = summedSize / WINDOW_SIZE;

		if (this.initialCapacity > OPERATION_THRESHOLD) {
			this.fullAnalysis = true;
		} else if (this.initialCapacity < ARRAY_THRESHOLD) {
			championCollectionType = CollectionTypeEnum.ARRAY;
			this.context.updateCollectionType(CollectionTypeEnum.ARRAY);
			this.isSleeping = true;
		}

	}

	@Override
	public boolean isOnline() {
		return isOnline;
	}

	public boolean isSleeping() {
		return isSleeping;
	}

	@Override
	public List<E> createListMonitor(List<? extends E> list) {

		if (!this.fullAnalysis) {

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

		} else {
			
			switch (championCollectionType) {
			case ARRAY:
				return new ArrayListOperationsMonitor<>(list, context);
			case LINKED:
				return new LinkedListOperationsMonitor<>(list, this.context);
			case HASH:
				return new HashArrayListOperationsMonitor<>(list, this.context);
			default:
				break;
			}
		}

		return null;
	}

	@Override
	public List<E> createListMonitor(int inicialCapacity) {

		switch (championCollectionType) {
		case ARRAY:
			return new ArrayListSizeMonitor<E>(initialCapacity, this.context);
		case LINKED:
			return new LinkedListSizeMonitor<>(this.context);
		case HASH:
			return new ListSizeMonitor<>(new HashArrayList<>(initialCapacity), this.context);
		default:
			break;
		}

		return null;
	}

}
