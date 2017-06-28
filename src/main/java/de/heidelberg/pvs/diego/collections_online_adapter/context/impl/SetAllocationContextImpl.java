package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveSet;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetPassiveSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class SetAllocationContextImpl implements SetAllocationContextInfo {

	private int sampleRate = 1;

	private SetAllocationOptimizer optimizer;

	private AllocationContextState state;

	private int analyzedCollectionSize = 0;
	private int analyzedCollectionInitialCapacity = 0;
	private int count;

	public SetAllocationContextImpl(SetAllocationOptimizer optimizer) {
		this.optimizer = optimizer;
		this.state = AllocationContextState.WARMUP;
	}
	
	public SetAllocationContextImpl(SetAllocationOptimizer optimizer, int sampleRate) {
		this.optimizer = optimizer;
		this.state = AllocationContextState.WARMUP;
		this.sampleRate = sampleRate;
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
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new AdaptiveSet<E>(analyzedCollectionInitialCapacity));
			}
			return new AdaptiveSet<E>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new HashSet<E>());
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
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new AdaptiveSet<E>(analyzedCollectionInitialCapacity));
			}
			return new AdaptiveSet<E>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new HashSet<E>(initialCapacity));
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
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new AdaptiveSet<E>(set));
			}
			return new AdaptiveSet<E>(set);

		case WARMUP:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new HashSet<E>(set));
			}
			return new HashSet<E>(set);

		case INACTIVE:
			return new HashSet<E>(set);

		default:
			break;
		}

		return null;
		
	}

	public AllocationContextState getAllocationContextState() {
		return state;
	}

	@Override
	public int getAnalyzedSize() {
		return analyzedCollectionSize;
	}

}
