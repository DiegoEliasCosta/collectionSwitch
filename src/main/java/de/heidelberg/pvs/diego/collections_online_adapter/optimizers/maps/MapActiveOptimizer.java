package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListState;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapState;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class MapActiveOptimizer implements MapAllocationOptimizer {

	AllocationContextUpdatable context;
	List<MapState> collectionsState;

	private final int windowSize;
	private double finishedRatio;

	public MapActiveOptimizer(int windowSize, double finishedRatio) {
		super();
		this.windowSize = windowSize;
		this.collectionsState = new ArrayList<MapState>(windowSize);
		this.finishedRatio = finishedRatio;

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

		int n = collectionsState.size();
		int[] sizes = new int[n];

		int amountFinishedCollections = 0;
		for (int i = 0; i < n; i++) {
			MapState state = collectionsState.get(i);

			if (state.hasCollectionFinished()) {
				amountFinishedCollections++;
				sizes[i] = state.getSize();
			} else {
				// TODO: IMPLEMENT THIS
			}

		}

		// Only analyze it when
		if (amountFinishedCollections > n / finishedRatio) {

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
