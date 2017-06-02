package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveSet;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetPassiveSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class SetAllocationContextImpl implements SetAllocationContext {

	private static final int SAMPLE = 1;

	private AllocationOptimizer optimizer;

	private AllocationContextState state;

	private int analyzedCollectionSize = 0;
	private int analyzedCollectionInitialCapacity = 0;
	private int count;

	public SetAllocationContextImpl(AllocationOptimizer optimizer) {
		this.optimizer = optimizer;
		this.state = AllocationContextState.WARMUP;
	}

	@Override
	public void updateCollectionSize(int size) {
		analyzedCollectionSize = size;
		analyzedCollectionInitialCapacity = (int) (size /.75f + 1);
		this.state = AllocationContextState.ADAPTIVE;
	}

	@Override
	public void noSizeConvergence() {
		// TODO To be implemented

	}

	@Override
	public <E> Set<E> createSet() {
		
		switch (state) {

		case ADAPTIVE:
			if (count++ % SAMPLE == 0) {
				return new SetPassiveSizeMonitor<E>(new AdaptiveSet<E>(analyzedCollectionInitialCapacity), optimizer);
			}
			return new AdaptiveSet<>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % SAMPLE == 0) {
				return new SetPassiveSizeMonitor<E>(new HashSet<E>(), optimizer);
			}
			return new HashSet<E>();

		case INACTIVE:
			return new HashSet<E>();

		default:
			break;
		}

		return null;
	}

	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		
		switch (state) {

		case ADAPTIVE:
			if (count++ % SAMPLE == 0) {
				return new SetPassiveSizeMonitor<E>(new AdaptiveSet<E>(analyzedCollectionInitialCapacity), optimizer);
			}
			return new AdaptiveSet<>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % SAMPLE == 0) {
				return new SetPassiveSizeMonitor<E>(new HashSet<E>(initialCapacity), optimizer);
			}
			return new HashSet<E>(initialCapacity);

		case INACTIVE:
			return new HashSet<E>(initialCapacity);

		default:
			break;
		}

		return null;
	}

	@Override
	public <E> Set<E> createSet(Collection<? extends E> set) {
		
		switch (state) {

		case ADAPTIVE:
			if (count++ % SAMPLE == 0) {
				return new SetPassiveSizeMonitor<E>(new AdaptiveSet<E>(set), optimizer);
			}
			return new AdaptiveSet<>(set);

		case WARMUP:
			if (count++ % SAMPLE == 0) {
				return new SetPassiveSizeMonitor<E>(new HashSet<E>(set), optimizer);
			}
			return new HashSet<E>(set);

		case INACTIVE:
			return new HashSet<E>(set);

		default:
			break;
		}

		return null;
		
	}

	@Override
	public AllocationContextState getAllocationContextState() {
		return state;
	}

}
