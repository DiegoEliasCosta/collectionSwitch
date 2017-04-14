package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class RuleBasedListOptimizer implements ListAllocationOptimizer {

	public static final int MIDLIST_LINKED_THRESHOLD = 3;
	public static final int CONTAINS_HASH_THRESHOLD = 16;

	private int arrayVote = 0;
	private int hashVote = 0;
	private int linkedVote = 0;

	private int sizes[];
	private AtomicInteger indexManager = new AtomicInteger(0);
	private AtomicInteger nfinalizedLists = new AtomicInteger(0);

	ListAllocationContext context;

	private final int windowSize;
	private final int convergenceRate;

	public RuleBasedListOptimizer(int windowSize, int convergenceRate) {
		super();
		this.windowSize = windowSize;
		this.convergenceRate = convergenceRate;
		this.sizes = new int[windowSize];
	}

	@Override
	public void updateSize(int index, int size) {

		// This is our only thread-safe control
		int nFinalized = nfinalizedLists.getAndIncrement();

		sizes[index] = size;

		// Always vote for array list before analyzing the operations
		this.arrayVote++;

		if (nFinalized == windowSize - 1) {

			this.updateContext();
		}

	}

	@Override
	public void updateOperationsAndSize(int index, int indexOp, int midListOp, int containsOp, int size) {

		int nFinalized = nfinalizedLists.getAndIncrement();

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
		
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 3);
		}
		
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 5);
		}
		
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 10);
		}
		
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
		indexManager.set(0);
		nfinalizedLists.set(0);
		this.arrayVote = 0;
		this.linkedVote = 0;
		this.hashVote = 0;
		
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
	public void setContext(ListAllocationContext context) {
		this.context = context;
		
	}

	@Override
	public void checkFinalizedAnalysis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVote(int index, CollectionTypeEnum vote) {
		switch(vote) {
		case ARRAY:
			arrayVote++;
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
	public void addReference(List<?> list) {
		// FIXME: This is not used here - to be removed from the interface later
		
	}

}
