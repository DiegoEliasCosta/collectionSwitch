package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetProactiveFullMonitor;

public class SetActiveOptimizer implements SetAllocationOptimizer {

	private static final Object DUMB_OBJECT = new Object();

	private static final float ALPHA = 0.9f;

	private int sizes[];
	private AtomicInteger indexManager = new AtomicInteger(0);
	protected Map<Set<?>, Object> finalizedManager;

	AllocationContextUpdatable context;

	Thread proactiveThread;

	private final int windowSize;

	private int analyzedInitialCapacity;

	private boolean firstUpdate;

	public SetActiveOptimizer(int windowSize) {
		super();
		this.firstUpdate = true;
		this.windowSize = windowSize;
		this.sizes = new int[windowSize];
		this.finalizedManager = new WeakHashMap<Set<?>, Object>(windowSize);

	}

	@Override
	public void updateSize(int index, int size) {
		if(index < windowSize) {
			sizes[index] = size;
		}
		
	}

	protected void updateContext() {
		
		int begin = 0;
		
		if(firstUpdate) {
			analyzedInitialCapacity = sizes[0];
			firstUpdate = false;
			begin = 1;
		}
		
		for(int i = begin; i < windowSize; i++) {
			analyzedInitialCapacity = (int) (analyzedInitialCapacity * ALPHA + (1 - ALPHA) * sizes[i]);
		}
		
		this.context.updateCollectionSize(analyzedInitialCapacity);

		resetOptimizer();
	}

	private void resetOptimizer() {
		indexManager.set(0);
		finalizedManager.clear();
	}

	public void checkFinalizedAnalysis() {
		if (indexManager.get() >= windowSize) {
			if (finalizedManager.isEmpty()) {
				this.updateContext();
			}
		}
	}


	@Override
	public void setContext(AllocationContextUpdatable context) {
		this.context = context;

	}

	@Override
	public <E> Set<E> createMonitor(Set<E> set) {
		int index = indexManager.getAndIncrement();
		SetProactiveFullMonitor<E> monitor = new SetProactiveFullMonitor<E>(set, this, index);
		if(index < windowSize) {
			this.finalizedManager.put(monitor, DUMB_OBJECT);
		}
		
		return monitor;
	}
}
