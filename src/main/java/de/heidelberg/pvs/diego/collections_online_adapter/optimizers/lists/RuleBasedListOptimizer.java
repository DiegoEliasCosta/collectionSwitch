package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class RuleBasedListOptimizer implements ListAllocationOptimizer {

	public static final int MIDLIST_LINKED_THRESHOLD = 3;
	public static final int CONTAINS_HASH_THRESHOLD = 16;

	private ObjectIntHashMap<CollectionTypeEnum> votedCollections;
	
	private int sizes[];
	private AtomicInteger indexManager = new AtomicInteger(0);

	ListAllocationContext context;

	private final int windowSize;
	private final int convergenceRate;

	public RuleBasedListOptimizer(ListAllocationContext context, int windowSize, int convergenceRate) {
		super();
		this.context = context;
		this.windowSize = windowSize;
		this.convergenceRate = convergenceRate;
		this.sizes = new int[windowSize];
		this.votedCollections = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0, 
				CollectionTypeEnum.HASH, 0, 
				CollectionTypeEnum.LINKED, 0);
	}

	@Override
	public void updateSize(int size) {
		
		// This is our only thread-safe control
		int myIndex = indexManager.getAndIncrement();
		
		if(myIndex < windowSize) {
			sizes[myIndex] = size;
			
			// Always vote for array list before analyzing the operations
			this.votedCollections.addToValue(CollectionTypeEnum.ARRAY, 1);
			
			if(myIndex == windowSize - 1) {
				
				this.updateContext();
			}
		}
			
	}


	@Override
	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {

		int copyCount = indexManager.getAndIncrement();
		
		if(copyCount < windowSize) {
			sizes[copyCount] = size;
			
			CollectionTypeEnum vote;
			
			// ----------------------------------------------------------
			// --------------------- VOTING SYSTEM ------------------------
			// ----------------------------------------------------------
			
			// R1: [Default] Use array
			vote = CollectionTypeEnum.ARRAY;
			
			// R2: If contains is highly used
			if(containsOp > CONTAINS_HASH_THRESHOLD) {
				vote = CollectionTypeEnum.HASH;
			}
			
			// R3: If operations in the middle are WAY more common than operation by index
			else if(midListOp > MIDLIST_LINKED_THRESHOLD * indexOp) {
				vote = CollectionTypeEnum.LINKED;
			}
			
			votedCollections.addToValue(vote, 1);
			
			if (copyCount == windowSize - 1) { 
				updateContext();
			}
			
		}

	}
	
	private void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int median = IntArrayUtils.calculateMedian(sizes);
		
		// FIXME: To add here the no convergence initial capacity as well
		
		// Inform the Allocation Context
		if(votedCollections.get(CollectionTypeEnum.ARRAY) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY, median);
		}

		else if(votedCollections.get(CollectionTypeEnum.LINKED) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.LINKED, median);
		}
		 
		else if(votedCollections.get(CollectionTypeEnum.HASH) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.HASH, median);
		}
		
		else {
			// No clear convergence for one collection type - more analysis might be needed
			this.context.noCollectionTypeConvergence(median);
		}
		
		indexManager.set(0);
		this.votedCollections = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0, 
				CollectionTypeEnum.HASH, 0, 
				CollectionTypeEnum.LINKED, 0);
		
	}

	
	
}
