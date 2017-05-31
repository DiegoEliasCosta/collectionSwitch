package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class ProactiveRuleBasedSetOptimizer implements SetAllocationOptimizer {

	private static final Object DUMB_OBJECT = new Object();
	public static final int ITERATION_LINKED_THRESHOLD = 100;
	public static final int UNIFIED_THRESHOLD = 1000;
	public static final int ARRAY_THRESHOLD = 20;

	private int sizes[];

	// VOTE
	private int arrayVote = 0;
	private int linkedVote = 0;
	private int unifiedVote = 0;
	private int hashVote = 0;

	private AtomicInteger indexManager;
	private Map<Set<?>, Object> finalizedManager;

	SetAllocationContext context;
	private int windowSize;
	private int convergenceRate;

	public ProactiveRuleBasedSetOptimizer(int windowSize, int convergencyRate) {
		super();
		this.windowSize = windowSize;
		this.convergenceRate = convergencyRate;
		this.sizes = new int[windowSize];
		this.indexManager = new AtomicInteger(0);
		this.finalizedManager = new WeakHashMap<Set<?>, Object>(windowSize);

	}

	@Override
	public void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size) {

		sizes[index] = size;

		// R1: Small sets are voted for array
		if (size < ARRAY_THRESHOLD) {
			arrayVote++;
		}

		// R2: Medium sets are voted for Unified Set
		else if (size < UNIFIED_THRESHOLD) {
			unifiedVote++;
		}

		// R3: If iteration is highly-used
		else if (iterationOp > ITERATION_LINKED_THRESHOLD) {
			linkedVote++;
		}

		// R4: Use Hash (best in average)
		else {
			hashVote++;
		}


	}

	@Override
	public void updateSize(int index, int size) {

		sizes[index] = size;

		// R1: Small sets can be transcribed to ARRAY
		if (size < ARRAY_THRESHOLD) {
			arrayVote++;
		} else if (size < UNIFIED_THRESHOLD) {
			unifiedVote++;
		} else {
			hashVote++;
		}


	}

	private void updateContext() {

		int mode = IntArrayUtils.calculateModeWithThrehsold(sizes, convergenceRate);
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 10);
		}
		int median = IntArrayUtils.calculateMedian(sizes);

		// Inform the Allocation Context
		if (arrayVote > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY, mode, median);
		}

		// Inform the Allocation Context
		else if (unifiedVote > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.OPEN_HASH, mode, median);
		}

		else if (linkedVote > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.LINKED, mode, median);
		}

		else if (hashVote > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.HASH, mode, median);
		}

		else {
			// No clear convergence for one collection type - more analysis
			// might be needed
			this.context.noCollectionTypeConvergence(mode, median);
		}

		this.resetOptimizer();
	}

	private void resetOptimizer() {
		
		indexManager.set(0);
		finalizedManager = new WeakHashMap<Set<?>, Object>(windowSize);
		
	}

	@Override
	public int getMonitoringIndex() {
		int index = indexManager.getAndIncrement();
		if (index < windowSize) {
			return index;
		}
		return -1;
	}

	@Override
	public void setContext(SetAllocationContext context) {
		this.context = context;
		
	}

	@Override
	public void addReference(Set<?> set) {
		this.finalizedManager.put(set, DUMB_OBJECT);
	}

	@Override
	public void checkFinalizedAnalysis() {
		if(indexManager.get() >= windowSize) {
			if(finalizedManager.isEmpty()) {
				this.updateContext();
			}
		}
	}

}
