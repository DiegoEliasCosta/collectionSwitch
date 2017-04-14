package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class ProactiveRuleBasedMapOptimizer implements MapAllocationOptimizer {

	private static final Object DUMB = new Object();
	public static final int RULE_LINKED_ITERATIONS = 100;
	public static final int RULE_UNIFIED_SIZE = 500;
	public static final int RULE_ARRAY_SIZE = 10;

	private int sizes[];

	private AllocationContextUpdatable context;
	private CollectionTypeEnum[] votes;

	private AtomicInteger indexManager;
	protected Map<Map<?, ?>, Object> finalizedManager;

	private final int windowSize;
	private final int convergenceRate;

	public ProactiveRuleBasedMapOptimizer(int windowSize, int convergencyRate) {
		super();
		this.windowSize = windowSize;
		this.convergenceRate = convergencyRate;

		this.sizes = new int[windowSize];
		this.votes = new CollectionTypeEnum[windowSize];

		this.indexManager = new AtomicInteger(0);
		this.finalizedManager = new WeakHashMap<Map<?, ?>, Object>(windowSize);
	}

	@Override
	public void updateSize(int index, int size) {

		sizes[index] = size;

		if (size < RULE_ARRAY_SIZE) {
			votes[index] = CollectionTypeEnum.ARRAY;
		}

		else if (size < RULE_UNIFIED_SIZE) {
			votes[index] = CollectionTypeEnum.ARRAY_HASH;
		}

		else {
			votes[index] = CollectionTypeEnum.HASH;
		}

	}

	@Override
	public void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size) {

		// FIXME: Not suppose to be used here
	}

	private void updateContext() {

		// FIXME: Add the adaptive aspect to the size as well
		int mode = IntArrayUtils.calculateModeWithThrehsold(sizes, convergenceRate);
		if(mode < 0) {
			mode = IntArrayUtils.calculateModeCategoryWithThreshold(sizes, convergenceRate, 10);
		}
		int median = IntArrayUtils.calculateMedian(sizes);
		
		int arrayVote = 0;
		int hashVote = 0;
		int unifiedVote = 0;
		int linkedVote = 0;
		
		for (int i = 0; i < windowSize; i++) {
			if (this.votes[i] == CollectionTypeEnum.ARRAY)
				arrayVote++;
			else if (this.votes[i] == CollectionTypeEnum.ARRAY_HASH)
				unifiedVote++;
			else if (this.votes[i] == CollectionTypeEnum.HASH)
				hashVote++;
			else if (this.votes[i] == CollectionTypeEnum.LINKED)
				linkedVote++;
		}

		// Inform the Allocation Context
		if (arrayVote >= convergenceRate) {
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
		finalizedManager = new WeakHashMap<Map<?, ?>, Object>(windowSize);
	}

	@Override
	public void setContext(AllocationContextUpdatable context) {
		this.context = context;
		
	}

	@Override
	public void updateVote(int index, CollectionTypeEnum vote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReference(Map<?, ?> map) {
		this.finalizedManager.put(map, DUMB);
		
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
