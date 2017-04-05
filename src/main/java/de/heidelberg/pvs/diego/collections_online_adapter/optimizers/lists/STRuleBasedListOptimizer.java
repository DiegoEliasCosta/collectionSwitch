package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class STRuleBasedListOptimizer implements ListAllocationOptimizer {

	public static final int MIDLIST_LINKED_THRESHOLD = 3;
	public static final int CONTAINS_HASH_THRESHOLD = 16;

	private int arrayVote = 0;
	private int hashVote = 0;
	private int linkedVote = 0;

	private int sizes[];
	private int indexManager = 0;
	private int nfinalizedLists = 0;

	ListAllocationContext context;

	// Thread-unsafe
	private final int windowSize;
	private final int convergenceRate;

	public STRuleBasedListOptimizer(int windowSize, int convergenceRate) {
		super();
		this.windowSize = windowSize;
		this.convergenceRate = convergenceRate;
		this.sizes = new int[windowSize];
	}

	@Override
	public void updateSize(int index, int size) {

		// This is our only thread-safe control
		int nFinalized = nfinalizedLists++;

		sizes[index] = size;

		// Always vote for array list before analyzing the operations
		this.arrayVote++;

		if (nFinalized == windowSize - 1) {

			this.updateContext();
		}

	}

	@Override
	public void updateOperationsAndSize(int index, int indexOp, int midListOp, int containsOp, int size) {

		int nFinalized = nfinalizedLists++;

		sizes[index] = size;

		// ----------------------------------------------------------
		// --------------------- VOTING SYSTEM ------------------------
		// ----------------------------------------------------------

		// R2: If contains is highly used
		if (containsOp > CONTAINS_HASH_THRESHOLD) {
			hashVote++;
		}

		// R3: If operations in the middle are WAY more common than
		// operation by index
		else if (midListOp > MIDLIST_LINKED_THRESHOLD * indexOp) {
			linkedVote++;
		}

		// R1: [Default] Use array
		else {
			arrayVote++;
		}

		if (nFinalized == windowSize - 1) {
			updateContext();
		}

	}

	private void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int mode = IntArrayUtils.calculateModeWithThrehsold(sizes, convergenceRate);
		int median = IntArrayUtils.calculateMedian(sizes);

		// FIXME: To add here the no convergence initial capacity as well

		// Inform the Allocation Context
		if (arrayVote > convergenceRate) {
			this.context.optimizeCollectionType(CollectionTypeEnum.ARRAY, mode, median);
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

	private void resetOptimizer() {
		this.indexManager = 0;
		this.nfinalizedLists = 0;
		this.arrayVote = 0;
		this.linkedVote = 0;
		this.hashVote = 0;
		
	}

	@Override
	public int getMonitoringIndex() {
		
		int index = indexManager++;
		if (index < windowSize) {
			return index;
		}
		return -1;

	}

	@Override
	public void setContext(ListAllocationContext context) {
		this.context = context;
		
	}

}
