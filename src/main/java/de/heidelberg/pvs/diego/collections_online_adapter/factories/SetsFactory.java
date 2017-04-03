package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.ArraySet;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.HashSetFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.HashSetSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class SetsFactory {

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createNormalSet(CollectionTypeEnum type, int initialCapacity) {

		switch (type) {

		case HASH:
			return new HashSet<>(initialCapacity);

		case ARRAY:
			return new ArraySet(initialCapacity);

		case ARRAY_HASH:
			return new UnifiedSet<>(initialCapacity);

		case LINKED:
			return new LinkedHashSet<>(initialCapacity);

		default:
			break;

		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createNormalSet(CollectionTypeEnum type, Collection<? extends E> set) {

		switch (type) {

		case HASH:
			return new HashSet<>(set);

		case ARRAY:
			return new ArraySet(set);

		case ARRAY_HASH:
			return new UnifiedSet<>(set);

		case LINKED:
			return new LinkedHashSet<>(set);

		default:
			break;

		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createSizeMonitor(CollectionTypeEnum type, SetAllocationOptimizer context,
			int initialCapacity) {

		switch (type) {

		case HASH:
			return new HashSetSizeMonitor<>(initialCapacity, context);

		case ARRAY:
			return new SetSizeMonitor<>(new ArraySet(initialCapacity), context);

		case ARRAY_HASH:
			return new SetSizeMonitor<>(new UnifiedSet<>(initialCapacity), context);

		case LINKED:
			return new SetSizeMonitor<>(new LinkedHashSet<>(initialCapacity), context);

		default:
			break;

		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createSizeMonitor(CollectionTypeEnum type, SetAllocationOptimizer context,
			Collection<? extends E> set) {

		switch (type) {

		case HASH:
			return new HashSetSizeMonitor<>(set, context);

		case ARRAY:
			return new SetSizeMonitor<>(new ArraySet(set), context);

		case ARRAY_HASH:
			return new SetSizeMonitor<>(new UnifiedSet<>(set), context);

		case LINKED:
			return new SetSizeMonitor<>(new LinkedHashSet<>(set), context);

		default:
			break;

		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createFullMonitor(CollectionTypeEnum type, SetAllocationOptimizer context,
			int initialCapacity) {

		switch (type) {

		case HASH:
			return new HashSetFullMonitor<>(initialCapacity, context);

		case ARRAY:
			return new SetFullMonitor<>(new ArraySet(initialCapacity), context);

		case ARRAY_HASH:
			return new SetFullMonitor<>(new UnifiedSet<>(initialCapacity), context);

		case LINKED:
			return new SetFullMonitor<>(new LinkedHashSet<>(initialCapacity), context);

		default:
			break;

		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <E> Set<E> createFullMonitor(CollectionTypeEnum type, SetAllocationOptimizer context,
			Collection<? extends E> set) {

		switch (type) {

		case HASH:
			return new HashSetFullMonitor<>(set, context);

		case ARRAY:
			return new SetFullMonitor<>(new ArraySet(set), context);

		case ARRAY_HASH:
			return new SetFullMonitor<>(new UnifiedSet<>(set), context);

		case LINKED:
			return new SetFullMonitor<>(new LinkedHashSet<>(set), context);

		default:
			break;

		}

		return null;
	}


}
