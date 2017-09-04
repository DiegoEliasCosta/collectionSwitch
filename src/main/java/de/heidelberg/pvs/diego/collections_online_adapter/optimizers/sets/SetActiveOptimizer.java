package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapState;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetState;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class SetActiveOptimizer implements SetAllocationOptimizer {

	AllocationContextUpdatable context;

	List<SetState> collectionsState;

	private final int windowSize;

	private double finishedRatio;

	public SetActiveOptimizer(int windowSize, double finishedRatio) {
		super();
		this.windowSize = windowSize;
		this.collectionsState = new ArrayList<SetState>(windowSize);
		this.finishedRatio = finishedRatio;
	}

	@Override
	public <E> Set<E> createMonitor(Set<E> set) {

		SetState state = new SetState(new WeakReference<Set<E>>(set));
		collectionsState.add(state);
		return new SetActiveFullMonitor<>(set, state);
	}

	@Override
	public void analyzeAndOptimize() {

		int n = collectionsState.size();
		int[] sizes = new int[n];

		int amountFinishedCollections = 0;
		for (int i = 0; i < n; i++) {
			SetState state = collectionsState.get(i);

			if (state.hasCollectionFinished()) {
				amountFinishedCollections++;
				sizes[i] = state.getSize();
			} else {
				// TODO: IMPLEMENT THIS
			}

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
	public void setContext(SetAllocationContext context) {
		this.context = context;

	}

}
