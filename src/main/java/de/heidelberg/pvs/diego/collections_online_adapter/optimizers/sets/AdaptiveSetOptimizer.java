package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.collections.api.block.predicate.primitive.ObjectIntPredicate;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class AdaptiveSetOptimizer<E> implements SetAllocationOptimizer<E> {

	private int sizes[] = new int[WINDOW_SIZE];
	private ObjectIntHashMap<CollectionTypeEnum> votedCollections;

	private AtomicInteger indexManager;

	SetAllocationContext<E> context;

	public AdaptiveSetOptimizer(SetAllocationContext<E> context) {
		super();
		this.context = context;
		this.indexManager = new AtomicInteger(0);
		votedCollections = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0,
				CollectionTypeEnum.ARRAY_HASH, 0, CollectionTypeEnum.HASH, 0, CollectionTypeEnum.LINKED, 0);

	}

	@Override
	public void updateOperationsAndSize(int containsOp, int iterationOp, int size) {

		int myIndex = indexManager.getAndIncrement();

		if (myIndex < WINDOW_SIZE) {

			sizes[myIndex] = size;

			// R1: Small sets are voted for array
			if (size < 20) {
				votedCollections.addToValue(CollectionTypeEnum.ARRAY, 1);
			}

			// R2: Medium sets are voted for Unified Set
			else if (size < 1000) {
				votedCollections.addToValue(CollectionTypeEnum.ARRAY_HASH, 1);
			}

			// R3: If iteration is highly-used
			else if (iterationOp > 100) {
				votedCollections.addToValue(CollectionTypeEnum.LINKED, 1);
			}

			// R4: Use Hash (best in average)
			else {
				votedCollections.addToValue(CollectionTypeEnum.HASH, 1);
			}

			if (myIndex == WINDOW_SIZE - 1) {

				updateContext();
				this.indexManager.set(0);

			}

		}

	}

	@Override
	public void updateSize(int size) {

		int myIndex = indexManager.getAndIncrement();

		if (myIndex < WINDOW_SIZE) {

			sizes[myIndex] = size;

			// R1: Small sets can be transcribed to ARRAY
			if (size < 20) {
				votedCollections.addToValue(CollectionTypeEnum.ARRAY, 1);
			} else if (size < 1000) {
				votedCollections.addToValue(CollectionTypeEnum.ARRAY_HASH, 1);
			} else {
				votedCollections.addToValue(CollectionTypeEnum.HASH, 1);
			}

			// last position
			if (myIndex == WINDOW_SIZE - 1) {
				updateContext();
			}

		}

	}

	private void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int median = IntArrayUtils.calculateMedian(sizes);
		this.context.optimizeInitialCapacity(median);

		// Inform the Allocation Context
		if (votedCollections.get(CollectionTypeEnum.ARRAY) > CONVERGENCE_RATE) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY);
		}

		// Inform the Allocation Context
		if (votedCollections.get(CollectionTypeEnum.ARRAY_HASH) > CONVERGENCE_RATE) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY_HASH);
		}

		else if (votedCollections.get(CollectionTypeEnum.LINKED) > CONVERGENCE_RATE) {
			this.context.optimizeCollectionType(CollectionTypeEnum.LINKED);
		}

		else if (votedCollections.get(CollectionTypeEnum.HASH) > CONVERGENCE_RATE) {
			this.context.optimizeCollectionType(CollectionTypeEnum.HASH);
		}

		else {
			// No clear convergence for one collection type - more analysis
			// might be needed
			this.context.noCollectionTypeConvergence();
		}

	}

}
