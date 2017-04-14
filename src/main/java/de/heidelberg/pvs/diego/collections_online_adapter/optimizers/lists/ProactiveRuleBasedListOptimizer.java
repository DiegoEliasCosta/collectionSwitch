package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class ProactiveRuleBasedListOptimizer implements ListAllocationOptimizer {

	private static final Object DUMB_OBJECT = new Object();
	
	public static final int MIDLIST_LINKED_THRESHOLD = 3;
	public static final int CONTAINS_HASH_THRESHOLD = 16;

	private int sizes[];
	private CollectionTypeEnum[] votes;
	private AtomicInteger indexManager = new AtomicInteger(0);
	protected Map<List<?>, Object> finalizedManager;

	ListAllocationContext context;
	
	Thread proactiveThread;

	private final int windowSize;
	private final int convergenceRate;

	public ProactiveRuleBasedListOptimizer(int windowSize, int convergenceRate) {
		super();
		this.windowSize = windowSize;
		this.convergenceRate = convergenceRate;
		this.sizes = new int[windowSize];
		this.votes = new CollectionTypeEnum[windowSize];
		this.finalizedManager = new WeakHashMap<List<?>, Object>(windowSize);
		
	}
	
	@Override
	public void updateSize(int index, int size) {

		sizes[index] = size;
		// DEFAULT
		this.votes[index] = CollectionTypeEnum.ARRAY;

	}

	@Override
	public void updateOperationsAndSize(int index, int indexOp, int midListOp, int containsOp, int size) {

		sizes[index] = size;

		// ----------------------------------------------------------
		// --------------------- VOTING SYSTEM ------------------------
		// ----------------------------------------------------------

		// R2: If contains is highly used
		if (containsOp > CONTAINS_HASH_THRESHOLD) {
			votes[index] = CollectionTypeEnum.HASH;
		}

		// R3: If operations in the middle are WAY more common than
		// operation by index
		else if (midListOp > MIDLIST_LINKED_THRESHOLD * indexOp) {
			votes[index] = CollectionTypeEnum.LINKED;
		}

		// R1: [Default] Use array
		else {
			votes[index] = CollectionTypeEnum.ARRAY;
		}

	}

	protected void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int mode = IntArrayUtils.calculateModeWithThrehsold(sizes, convergenceRate);
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 10);
		}
		int median = IntArrayUtils.calculateMedian(sizes);

		// FIXME: To add here the no convergence initial capacity as well
		int arrayVote = 0;
		int hashVote = 0;
		int linkedVote = 0;

		for (int i = 0; i < windowSize; i++) {
			if (this.votes[i] == CollectionTypeEnum.ARRAY)
				arrayVote++;
			else if (this.votes[i] == CollectionTypeEnum.HASH)
				hashVote++;
			else if (this.votes[i] == CollectionTypeEnum.LINKED)
				linkedVote++;
		}

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
		finalizedManager = new WeakHashMap<List<?>, Object>(windowSize);

	}

	@Override
	public int getMonitoringIndex() {

		int index = indexManager.getAndIncrement();
		if (index < windowSize) {
			return index;
		}

		return -1;

	}
	
	public void checkFinalizedAnalysis() {
		
		if(indexManager.get() >= windowSize) {
			if(finalizedManager.isEmpty()) {
				this.updateContext();
			}
		}
	}

	@Override
	public void setContext(ListAllocationContext context) {
		this.context = context;

	}

	@Override
	public void updateVote(int index, CollectionTypeEnum vote) {
		this.votes[index] = vote;
		
	}

	@Override
	public void addReference(List<?> list) {
		this.finalizedManager.put(list, DUMB_OBJECT);
		
	}
}
