package de.heidelberg.pvs.diego.collections_online_adapter.optimizers;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class ActiveOptimizer implements AllocationOptimizer {

	private static final Object DUMB_OBJECT = new Object();
	
	public static final int MIDLIST_LINKED_THRESHOLD = 3;
	public static final int CONTAINS_HASH_THRESHOLD = 16;

	private int sizes[];
	private AtomicInteger indexManager = new AtomicInteger(0);
	protected Map<List<?>, Object> finalizedManager;

	ListAllocationContext context;
	
	Thread proactiveThread;

	private final int windowSize;
	private final int convergenceRate;

	public ActiveOptimizer(int windowSize, int convergenceRate) {
		super();
		this.windowSize = windowSize;
		this.convergenceRate = convergenceRate;
		this.sizes = new int[windowSize];
		this.finalizedManager = new WeakHashMap<List<?>, Object>(windowSize);
		
	}
	
	@Override
	public void updateSize(int index, int size) {
		sizes[index] = size;
	}


	protected void updateContext() {

		// TODO Implement it here
		
		resetOptimizer();
	}

	private void resetOptimizer() {
		indexManager.set(0);
		finalizedManager = new WeakHashMap<List<?>, Object>(windowSize);

	}

	public void checkFinalizedAnalysis() {
		if(indexManager.get() >= windowSize) {
			if(finalizedManager.isEmpty()) {
				this.updateContext();
			}
		}
	}



	public void addReference(List<?> list) {
		this.finalizedManager.put(list, DUMB_OBJECT);
		
	}

	@Override
	public void setContext(AllocationContextUpdatable context) {
		// TODO Auto-generated method stub
		
	}
}
