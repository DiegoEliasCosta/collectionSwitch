package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class STRuleBasedMapOptimizer implements MapAllocationOptimizer {

	public static final int RULE_LINKED_ITERATIONS = 100;
	public static final int RULE_UNIFIED_SIZE = 500;
	public static final int RULE_ARRAY_SIZE = 10;

	private int sizes[];

	// VOTES
	private int arrayVote = 0;
	private int hashVote = 0;
	private int unifiedVote = 0;
	private int linkedVote = 0;

	private AllocationContextUpdatable context;

	private int indexManager;
	private int finalizedManager;

	private final int windowSize;
	private final int convergenceRate;

	public STRuleBasedMapOptimizer(int windowSize, int convergencyRate) {
		super();
		this.windowSize = windowSize;
		this.convergenceRate = convergencyRate;

		this.sizes = new int[windowSize];

		this.indexManager = 0;
		this.finalizedManager = 0;
	}

	@Override
	public void updateSize(int index, int size) {

		int nFinalized = this.finalizedManager++;

		sizes[index] = size;

		if (size < RULE_ARRAY_SIZE) {
			arrayVote++;
		}

		else if (size < RULE_UNIFIED_SIZE) {
			unifiedVote++;
		}

		else {
			hashVote++;
		}
		// Last update
		if (nFinalized == windowSize - 1) {
			updateContext();

		}

	}

	@Override
	public void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size) {

		int nFinalized = this.finalizedManager++;

		sizes[index] = size;

		if (size < RULE_ARRAY_SIZE) {
			arrayVote++;
		}

		else if (size < RULE_UNIFIED_SIZE) {
			unifiedVote++;
		}

		else if (iterationOp > RULE_LINKED_ITERATIONS) {
			linkedVote++;
		}

		else {
			hashVote++;
		}

		// Last update
		if (nFinalized == windowSize - 1) {
			updateContext();
		}

	}

	private void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int mode = IntArrayUtils.calculateModeWithThrehsold(sizes, convergenceRate);
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 10);
		}
		int median = IntArrayUtils.calculateMedian(sizes);

		// Inform the Allocation Context
		if (arrayVote >= convergenceRate) {
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

		resetOptimizer();
	}

	@Override
	public int getMonitoringIndex() {
		int index = indexManager++;
		if (index < windowSize) {
			return index;
		}
		return -1;

	}
	
	private void resetOptimizer() {
		this.indexManager = 0;
		this.finalizedManager = 0;
		this.arrayVote = 0;
		this.linkedVote = 0;
		this.unifiedVote = 0;
		this.hashVote = 0;
		
	}

	@Override
	public void setContext(AllocationContextUpdatable context) {
		this.context = context;
		
	}

	@Override
	public void updateVote(int index, CollectionTypeEnum vote) {
		switch(vote) {
		case ARRAY:
			arrayVote++;
			break;
		case OPEN_HASH:
			unifiedVote++;
			break;
		case HASH:
			hashVote++;
			break;
		case LINKED:
			linkedVote++;
			break;
		}
		
	}

	@Override
	public void addReference(Map<?, ?> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkFinalizedAnalysis() {
		// TODO Auto-generated method stub
		
	}

}
