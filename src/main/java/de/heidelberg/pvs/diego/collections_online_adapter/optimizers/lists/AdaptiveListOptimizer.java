package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.HashArrayListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class AdaptiveListOptimizer<E> implements ListAllocationOptimizer<E> {

	private static final int CONTAINS_THRESHOLD = 16;

	private ObjectIntHashMap<CollectionTypeEnum> votedCollections;
	
	private int sizes[] = new int[WINDOW_SIZE];
	private transient AtomicInteger count = new AtomicInteger(0);

	ListAllocationContext<E> context;

	public AdaptiveListOptimizer(ListAllocationContext<E> context) {
		super();
		this.context = context;
		this.votedCollections = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0, 
				CollectionTypeEnum.HASH, 0, 
				CollectionTypeEnum.LINKED, 0);
	}

	@Override
	public void updateSize(int size) {
		
		// This is our only thread-safe control
		int copyCount = count.getAndIncrement();
		
		if(copyCount < WINDOW_SIZE) {
			sizes[copyCount] = size;
			
			// Always vote for array list before analyzing the operations
			this.votedCollections.addToValue(CollectionTypeEnum.ARRAY, 1);
			
			if(copyCount == WINDOW_SIZE) {
				
				this.updateContext();
			}
		}
			
	}


	private void updateContext() {

		int medianSizes = IntArrayUtils.calculateMedian(sizes);
		
		if(votedCollections.get(CollectionTypeEnum.ARRAY) > CONVERGENCE_RATE) {
			this.context.optimizeAllocationContext(CollectionTypeEnum.ARRAY, medianSizes);
		}

		else if(votedCollections.get(CollectionTypeEnum.LINKED) > CONVERGENCE_RATE) {
			this.context.optimizeAllocationContext(CollectionTypeEnum.LINKED, medianSizes);
		}
		 
		else if(votedCollections.get(CollectionTypeEnum.HASH) > CONVERGENCE_RATE) {
			this.context.optimizeAllocationContext(CollectionTypeEnum.HASH, medianSizes);
		}
		
		else {
			this.context.optimizeAllocationContext(CollectionTypeEnum.DEFAULT, medianSizes);
		}
		
	}

	@Override
	public List<E> createListMonitor(Collection<? extends E> list, CollectionTypeEnum collectionType) {

		switch (collectionType) {
		case ARRAY:
			return new ArrayListOperationsMonitor<>(list, this.context);
		case LINKED:
			return new LinkedListOperationsMonitor<>(list, this.context);
		case HASH:
			return new HashArrayListOperationsMonitor<>(new HashArrayList<>(list), this.context);
		default:
			break;
		}

		return null;
	}

	@Override
	public List<E> createListMonitor(int inicialCapacity, CollectionTypeEnum championCollectionType) {

		switch (championCollectionType) {
		case ARRAY:
			return new ArrayListOperationsMonitor<>(inicialCapacity, this.context);
		case LINKED:
			return new LinkedListOperationsMonitor<>(this.context);
		case HASH:
			return new HashArrayListOperationsMonitor<>(new HashArrayList<>(inicialCapacity), this.context);
		default:
			break;
		}

		return null;
	}
	
	@Override
	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {

		int copyCount = count.getAndIncrement();
		
		if(copyCount < WINDOW_SIZE) {
			sizes[copyCount] = size;
			
			CollectionTypeEnum vote;
			
			// ----------------------------------------------------------
			// --------------------- VOTE SYSTEM ------------------------
			// ----------------------------------------------------------
			
			// R1: [Default] Use array
			vote = CollectionTypeEnum.ARRAY;
			
			// R2: If contains is highly used
			if(containsOp > CONTAINS_THRESHOLD) {
				vote = CollectionTypeEnum.HASH;
			}
			
			// R3: If operations in the middle are WAY more common than operation by index
			if(midListOp > 3 * indexOp) {
				vote = CollectionTypeEnum.LINKED;
			}
			
			votedCollections.addToValue(vote, 1);
			
			if (copyCount == WINDOW_SIZE) {
				updateContext();
			}
			
			count.set(0);
		}

	}

}
