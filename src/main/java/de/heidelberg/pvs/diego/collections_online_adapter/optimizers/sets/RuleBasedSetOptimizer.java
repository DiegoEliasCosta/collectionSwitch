package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class RuleBasedSetOptimizer implements SetAllocationOptimizer {

	public static final int ITERATION_LINKED_THRESHOLD = 100;
	public static final int UNIFIED_THRESHOLD = 1000;
	public static final int ARRAY_THRESHOLD = 20;
	
	
	private int sizes[];
	private ObjectIntHashMap<CollectionTypeEnum> votedCollections;

	private AtomicInteger indexManager;

	SetAllocationContext context;
	private int windowSize;
	private int convergenceRate;

	public RuleBasedSetOptimizer(SetAllocationContext context, int windowSize, int convergencyRate) {
		super();
		this.context = context;
		this.windowSize = windowSize;
		this.convergenceRate = convergencyRate;
		this.sizes = new int[windowSize];
		this.indexManager = new AtomicInteger(0);
		votedCollections = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0,
				CollectionTypeEnum.ARRAY_HASH, 0, 
				CollectionTypeEnum.HASH, 0, 
				CollectionTypeEnum.LINKED, 0);

	}

	@Override
	public void updateOperationsAndSize(int containsOp, int iterationOp, int size) {

		int myIndex = indexManager.getAndIncrement();

		if (myIndex < windowSize) {

			sizes[myIndex] = size;

			// R1: Small sets are voted for array
			if (size < ARRAY_THRESHOLD) {
				votedCollections.addToValue(CollectionTypeEnum.ARRAY, 1);
			}

			// R2: Medium sets are voted for Unified Set
			else if (size < UNIFIED_THRESHOLD) {
				votedCollections.addToValue(CollectionTypeEnum.ARRAY_HASH, 1);
			}

			// R3: If iteration is highly-used
			else if (iterationOp > ITERATION_LINKED_THRESHOLD) {
				votedCollections.addToValue(CollectionTypeEnum.LINKED, 1);
			}

			// R4: Use Hash (best in average)
			else {
				votedCollections.addToValue(CollectionTypeEnum.HASH, 1);
			}

			if (myIndex == windowSize - 1) {

				updateContext();

			}

		}

	}

	@Override
	public void updateSize(int size) {

		int myIndex = indexManager.getAndIncrement();

		if (myIndex < windowSize) {

			sizes[myIndex] = size;

			// R1: Small sets can be transcribed to ARRAY
			if (size < ARRAY_THRESHOLD) {
				votedCollections.addToValue(CollectionTypeEnum.ARRAY, 1);
			} else if (size < UNIFIED_THRESHOLD) {
				votedCollections.addToValue(CollectionTypeEnum.ARRAY_HASH, 1);
			} else {
				votedCollections.addToValue(CollectionTypeEnum.HASH, 1);
			}

			// last position
			if (myIndex == windowSize - 1) {
				updateContext();
			}

		}

	}

	private void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int median = IntArrayUtils.calculateMedian(sizes);

		// Inform the Allocation Context
		if (votedCollections.get(CollectionTypeEnum.ARRAY) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY, median);
		}

		// Inform the Allocation Context
		else if (votedCollections.get(CollectionTypeEnum.ARRAY_HASH) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY_HASH, median);
		}

		else if (votedCollections.get(CollectionTypeEnum.LINKED) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.LINKED, median);
		}

		else if (votedCollections.get(CollectionTypeEnum.HASH) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.HASH, median);
		}

		else {
			// No clear convergence for one collection type - more analysis
			// might be needed
			this.context.noCollectionTypeConvergence(median);
		}
		
		indexManager.set(0);
		votedCollections = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0,
				CollectionTypeEnum.ARRAY_HASH, 0, 
				CollectionTypeEnum.HASH, 0, 
				CollectionTypeEnum.LINKED, 0);
		

	}

}
