package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetPassiveSizeMonitor;

public class SetPassiveOptimizer implements SetAllocationOptimizer {

	private static final float ALPHA = 0.9f;
	private AllocationContextUpdatable context;
	private int finalizedCount;
	private int analyzedInitialCapacity;
	private int windowSize;
	
	public SetPassiveOptimizer(int windowSize) {
		super();
		this.windowSize = windowSize;
	}

	@Override
	public void updateSize(int index, int size) {
		
		int finalizedCopy = finalizedCount;
		
		if (finalizedCopy == 0) {
			analyzedInitialCapacity = size;
			finalizedCount++;

		} else {
			analyzedInitialCapacity = (int) (analyzedInitialCapacity * ALPHA + (1 - ALPHA) * size);
			finalizedCount++;
		}
		
		if(finalizedCopy == windowSize - 1) {
			this.context.updateCollectionSize(analyzedInitialCapacity);
			finalizedCount = 1;
		}	
		
	}

	@Override
	public void setContext(AllocationContextUpdatable context) {
		this.context = context;
		
	}

	@Override
	public <E> Set<E> createMonitor(Set<E> set) {
		return new SetPassiveSizeMonitor<E>(set, this);
	}


}
