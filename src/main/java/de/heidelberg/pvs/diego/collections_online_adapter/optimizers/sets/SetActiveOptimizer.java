package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetState;

public class SetActiveOptimizer implements SetAllocationOptimizer {

	AllocationContextUpdatable context;

	List<SetState> collectionsState;

	private final int windowSize;

	public SetActiveOptimizer(int windowSize) {
		super();
		this.windowSize = windowSize;
		collectionsState = new ArrayList<SetState>(windowSize);
	}

	@Override
	public <E> Set<E> createMonitor(Set<E> set) {

		SetState state = new SetState(new WeakReference<Set<E>>(set));
		collectionsState.add(state);
		return new SetActiveFullMonitor<>(set, state);
	}

	@Override
	public void analyzeAndOptimize() {
		// TODO To be implemented...

	}

}
