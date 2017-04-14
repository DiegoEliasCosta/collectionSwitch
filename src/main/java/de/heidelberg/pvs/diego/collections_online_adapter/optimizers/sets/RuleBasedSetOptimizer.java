package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class RuleBasedSetOptimizer implements SetAllocationOptimizer {

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
	private AtomicInteger finalizedManager;

	SetAllocationContext context;
	private int windowSize;
	private int convergenceRate;

	public RuleBasedSetOptimizer(int windowSize, int convergencyRate) {
		super();
		this.context = context;
		this.windowSize = windowSize;
		this.convergenceRate = convergencyRate;
		this.sizes = new int[windowSize];
		this.indexManager = new AtomicInteger(0);
		this.finalizedManager = new AtomicInteger(0);

	}

	@Override
	public void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size) {

		int nFinalized = finalizedManager.getAndIncrement();

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

		if (nFinalized == windowSize - 1) {

			updateContext();

		}

	}

	@Override
	public void updateSize(int index, int size) {

		int nFinalized = finalizedManager.getAndIncrement();

		sizes[index] = size;

		// R1: Small sets can be transcribed to ARRAY
		if (size < ARRAY_THRESHOLD) {
			arrayVote++;
		} else if (size < UNIFIED_THRESHOLD) {
			unifiedVote++;
		} else {
			hashVote++;
		}

		// last position
		if (nFinalized == windowSize - 1) {
			updateContext();
		}

	}

	private void updateContext() {

		int mode = IntArrayUtils.calculateModeWithThrehsold(sizes, convergenceRate);
		
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 2);
		}
		
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 5);
		}
		
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
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY_HASH, mode, median);
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
		finalizedManager.set(0);
		arrayVote = 0;
		unifiedVote = 0;
		linkedVote = 0;
		hashVote = 0;
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkFinalizedAnalysis() {
		// TODO Auto-generated method stub
		
	}

}
