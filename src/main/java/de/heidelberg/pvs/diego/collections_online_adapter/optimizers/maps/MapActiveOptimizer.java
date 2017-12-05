package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapMetrics;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class MapActiveOptimizer implements MapAllocationOptimizer {

	AllocationContextUpdatable context;
	List<MapMetrics> collectionsState;

	private final int windowSize;
	private double finishedRatio;

	public MapActiveOptimizer(int windowSize, double finishedRatio) {
		super();
		this.windowSize = windowSize;
		this.collectionsState = new ArrayList<MapMetrics>(windowSize);
		this.finishedRatio = finishedRatio;

	}

	@Override
	public <K, V> Map<K, V> createMonitor(Map<K, V> map) {

		MapMetrics state = new MapMetrics(new WeakReference<Map<K, V>>(map));
		collectionsState.add(state);
		MapActiveFullMonitor<K, V> monitor = new MapActiveFullMonitor<K, V>(map, state);
		return monitor;
	}

	@Override
	public void analyzeAndOptimize() {

		int n = collectionsState.size();
		int[] sizes = new int[n];

		int amountFinishedCollections = 0;
		for (int i = 0; i < n; i++) {
			MapMetrics state = collectionsState.get(i);

			if (state.hasCollectionFinished()) {
				amountFinishedCollections++;
			} 
			sizes[i] = state.getMaxSize();

		}

		// Only analyze it when
		if (amountFinishedCollections >= n / finishedRatio) {

			double mean = IntArrayUtils.calculateMean(sizes);
			double std = IntArrayUtils.calculateStandardDeviation(sizes);

			int newInitialCapacity = (int) ((mean + 2 * std) / 0.75 + 1);

			context.updateCollectionInitialCapacity(newInitialCapacity);

		}

	}

	@Override
	public void setContext(MapAllocationContext context) {
		this.context = context;

	}

}
