package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapState;

public class MapActiveOptimizer implements MapAllocationOptimizer {

	AllocationContextUpdatable context;
	List<MapState> collectionsState; 

	private final int windowSize;


	public MapActiveOptimizer(int windowSize) {
		super();
		this.windowSize = windowSize;
		collectionsState = new ArrayList<MapState>(windowSize);

	}

	@Override
	public <K, V> Map<K, V> createMonitor(Map<K, V> map) {
		
		MapState state = new MapState(new WeakReference<Map<K, V>>(map));
		collectionsState.add(state);
		MapActiveFullMonitor<K, V> monitor = new MapActiveFullMonitor<>(map, state);
		return monitor;
	}

	@Override
	public void analyzeAndOptimizeContext() {
		// TODO To be implemented
		
	}

}
