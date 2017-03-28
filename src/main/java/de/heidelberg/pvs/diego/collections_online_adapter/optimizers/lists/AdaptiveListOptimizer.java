package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class AdaptiveListOptimizer<E> implements ListAllocationOptimizer<E> {

	private static final int CONTAINS_THRESHOLD = 16;

	private ObjectIntHashMap<CollectionTypeEnum> votedCollections;
	
	private int sizes[] = new int[WINDOW_SIZE];
	private AtomicInteger index = new AtomicInteger(0);

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
		int myIndex = index.getAndIncrement();
		
		if(myIndex < WINDOW_SIZE) {
			sizes[myIndex] = size;
			
			// Always vote for array list before analyzing the operations
			this.votedCollections.addToValue(CollectionTypeEnum.ARRAY, 1);
			
			if(myIndex == WINDOW_SIZE) {
				
				this.updateContext();
			}
		}
			
	}


	@Override
	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {

		int copyCount = index.getAndIncrement();
		
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
			
			if (copyCount == WINDOW_SIZE - 1) { 
				updateContext();
			}
			
			index.set(0);
		}

	}
	
	private void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int median = IntArrayUtils.calculateMedian(sizes);
		this.context.optimizeInitialCapacity(median);
		
		// FIXME: To add here the no convergence initial capacity as well
		
		// Inform the Allocation Context
		if(votedCollections.get(CollectionTypeEnum.ARRAY) > CONVERGENCE_RATE) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY);
		}

		else if(votedCollections.get(CollectionTypeEnum.LINKED) > CONVERGENCE_RATE) {
			this.context.optimizeCollectionType(CollectionTypeEnum.LINKED);
		}
		 
		else if(votedCollections.get(CollectionTypeEnum.HASH) > CONVERGENCE_RATE) {
			this.context.optimizeCollectionType(CollectionTypeEnum.HASH);
		}
		
		else {
			// No clear convergence for one collection type - more analysis might be needed
			this.context.noCollectionTypeConvergence();
		}
		
	}

}
