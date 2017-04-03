package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class RuleBasedMapOptimizer implements MapAllocationOptimizer {

	public static final int RULE_LINKED_ITERATIONS = 100;
	public static final int RULE_UNIFIED_SIZE = 500;
	public static final int RULE_ARRAY_SIZE = 10;

	private int sizes[];

	private ObjectIntHashMap<CollectionTypeEnum> votedCollection;

	private AllocationContextUpdatable context;

	private AtomicInteger indexManager;
	private AtomicInteger finalizedManager;

	private final int windowSize;
	private final int convergenceRate;

	public RuleBasedMapOptimizer(int windowSize, int convergencyRate) {
		super();
		this.windowSize = windowSize;
		this.convergenceRate = convergencyRate;

		this.sizes = new int[windowSize];

		this.indexManager = new AtomicInteger(0);
		this.finalizedManager = new AtomicInteger(0);
		votedCollection = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0, CollectionTypeEnum.ARRAY_HASH,
				0, CollectionTypeEnum.HASH, 0, CollectionTypeEnum.LINKED, 0);
	}

	@Override
	public void updateSize(int index, int size) {

		int nFinalized = this.finalizedManager.getAndIncrement();

		sizes[index] = size;

		if (size < RULE_ARRAY_SIZE) {
			this.votedCollection.addToValue(CollectionTypeEnum.ARRAY, 1);
		}

		else if (size < RULE_UNIFIED_SIZE) {
			this.votedCollection.addToValue(CollectionTypeEnum.ARRAY_HASH, 1);
		}

		else {
			this.votedCollection.addToValue(CollectionTypeEnum.HASH, 1);
		}
		// Last update
		if (nFinalized == windowSize - 1) {
			updateContext();

		}

	}

	@Override
	public void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size) {

		int nFinalized = this.finalizedManager.getAndIncrement();

		sizes[index] = size;

		if (size < RULE_ARRAY_SIZE) {
			this.votedCollection.addToValue(CollectionTypeEnum.ARRAY, 1);
		}

		else if (size < RULE_UNIFIED_SIZE) {
			this.votedCollection.addToValue(CollectionTypeEnum.ARRAY_HASH, 1);
		}

		else if (iterationOp > RULE_LINKED_ITERATIONS) {
			this.votedCollection.addToValue(CollectionTypeEnum.LINKED, 1);
		}

		else {
			this.votedCollection.addToValue(CollectionTypeEnum.HASH, 1);
		}

		// Last update
		if (nFinalized == windowSize - 1) {
			updateContext();
		}

	}

	private void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int median = IntArrayUtils.calculateMedian(sizes);

		// Inform the Allocation Context
		if (votedCollection.get(CollectionTypeEnum.ARRAY) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY, median);
		}

		// Inform the Allocation Context
		else if (votedCollection.get(CollectionTypeEnum.ARRAY_HASH) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY_HASH, median);
		}

		else if (votedCollection.get(CollectionTypeEnum.LINKED) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.LINKED, median);
		}

		else if (votedCollection.get(CollectionTypeEnum.HASH) > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.HASH, median);
		}

		else {
			// No clear convergence for one collection type - more analysis
			// might be needed
			this.context.noCollectionTypeConvergence(median);
		}

		resetOptimizer();
	}

	@Override
	public int getMonitoringIndex() {
		int index = indexManager.getAndIncrement();
		if (index < windowSize) {
			return index;
		}
		return -1;

	}
	
	private void resetOptimizer() {
		indexManager.set(0);
		finalizedManager.set(0);
		this.votedCollection = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0, CollectionTypeEnum.HASH,
				0, CollectionTypeEnum.LINKED, 0);
		
	}

	@Override
	public void setContext(AllocationContextUpdatable context) {
		this.context = context;
		
	}

}
