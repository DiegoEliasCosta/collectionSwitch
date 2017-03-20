package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.ArraySet;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.HashSetSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.SetSizeMonitor;

public class FirstSamplesSetMemoryOptimizer<E> implements SetAllocationContext<E> {

	// Threshold
	private static final int ARRAY_THRESHOLD = 30;
	private static final int ARRAY_HASH_THRESHOLD = 1000;
	
	private int initialCapacity = 10;
	private static int SAMPLES = 10;

	// Monitored data
	private int[] sizes = new int[SAMPLES];
	private int[] contains = new int[SAMPLES];
	private int[] interations = new int[SAMPLES];

	private volatile int count = 0;

	private CollectionTypeEnum collectionType;

	public FirstSamplesSetMemoryOptimizer(CollectionTypeEnum collectionType) {
		super();
		this.collectionType = collectionType;
	}

	public boolean isOnline() {
		return count < SAMPLES;
	}

	@Override
	public Set<E> createSet() {
		return createSet(10);
	}

	@Override
	public Set<E> createSet(int initialCapacity) {
		
		switch (collectionType) {
		
		case ARRAY:
			return isOnline() ? new SetSizeMonitor<>(new ArraySet(), this): new ArraySet();
		case ARRAY_HASH:
			return isOnline() ? new SetSizeMonitor<E>(new UnifiedSet<E>(initialCapacity), this): new UnifiedSet<E>(this.initialCapacity);
		case LINKED:
			return isOnline() ? new HashSetSizeMonitor<E>(this): new LinkedHashSet<E>(this.initialCapacity);
		case HASH:
			return isOnline() ? new SetSizeMonitor<E>(new HashSet<E>(initialCapacity), this): new HashSet<E>(this.initialCapacity);
		}
		
		return null;
	}

	@Override
	public Set<E> createSet(Collection<? extends E> set) {
		switch (collectionType) {
		case ARRAY:
			return isOnline() ? new SetSizeMonitor<E>(new UnifiedSet<E>(set), this): new UnifiedSet<E>(set);
		case LINKED:
			return isOnline() ? new SetSizeMonitor<E>(new LinkedHashSet<E>(set), this): new LinkedHashSet<E>(set);
		case HASH:
			return isOnline() ? new SetSizeMonitor<E>(new HashSet<E>(set), this): new HashSet<E>(set);
		}
		
		return null;
	}

	@Override
	public void updateSize(int size) {
		// FIXME: This needs to be thread-safe
		int copyCount = count++;
		this.sizes[copyCount] = size;
		
		if(copyCount >= SAMPLES) {
			updateContext();
		}

	}

	@Override
	public void updateOperationsAndSize(int iterations, int contains, int size) {
		// FIXME: This needs to be thread-safe
		int copyCount = count++;
		this.interations[copyCount] = iterations;
		this.contains[copyCount] = contains;
		
		if(copyCount >= SAMPLES) {
			updateContext();
		}

	}

	private synchronized void updateContext() {

		int summedSize = 0;
		for (int i = 0; i < SAMPLES; i++) {
			summedSize += this.sizes [i];
		}
		this.initialCapacity = summedSize / SAMPLES;

		// FIXME: This is too arbitrary
		if (this.initialCapacity < ARRAY_THRESHOLD) {
			collectionType = CollectionTypeEnum.ARRAY;
		} else if (this.initialCapacity < ARRAY_HASH_THRESHOLD) {
			collectionType = CollectionTypeEnum.ARRAY_HASH;
		}

	}

}
