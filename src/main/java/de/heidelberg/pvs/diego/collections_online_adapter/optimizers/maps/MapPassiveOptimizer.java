package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapPassiveSizeMonitor;

public class MapPassiveOptimizer implements MapAllocationOptimizer {

	private static final float ALPHA = 0.9f;
	private AllocationContextUpdatable context;
	private int finalizedCount;
	private int analyzedInitialCapacity;
	private int windowSize;
	
	public MapPassiveOptimizer(int windowSize) {
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
	public <K, V> Map<K, V> createMonitor(Map<K, V> map) {
		return new MapPassiveSizeMonitor<K, V>(map, this);
	}


}
