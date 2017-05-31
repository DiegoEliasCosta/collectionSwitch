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
			return new HashSet<E>(initialCapacity);

		case ARRAY:
			return new ArraySet(initialCapacity);

		case OPEN_HASH:
			return new UnifiedSet<E>(initialCapacity);

		case LINKED:
			return new LinkedHashSet<E>(initialCapacity);

		default:
			break;

		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createNormalSet(CollectionTypeEnum type, Collection<? extends E> set) {

		switch (type) {

		case HASH:
			return new HashSet<E>(set);

		case ARRAY:
			return new ArraySet(set);

		case OPEN_HASH:
			return new UnifiedSet<E>(set);

		case LINKED:
			return new LinkedHashSet<E>(set);

		default:
			break;

		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createSizeMonitor(CollectionTypeEnum type, SetAllocationOptimizer context,
			int initialCapacity) {

		int index = context.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {

			case HASH:
				return new HashSetSizeMonitor<E>(initialCapacity, context, index);

			case ARRAY:
				return new SetSizeMonitor<E>(new ArraySet(initialCapacity), context, index);

			case OPEN_HASH:
				return new SetSizeMonitor<E>(new UnifiedSet<E>(initialCapacity), context, index);

			case LINKED:
				return new SetSizeMonitor<E>(new LinkedHashSet<E>(initialCapacity), context, index);

			default:
				break;

			}

		}

		return createNormalSet(type, initialCapacity);

	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createSizeMonitor(CollectionTypeEnum type, SetAllocationOptimizer optimizer,
			Collection<? extends E> set) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {

			case HASH:
				return new HashSetSizeMonitor<E>(set, optimizer, index);

			case ARRAY:
				return new SetSizeMonitor<E>(new ArraySet(set), optimizer, index);

			case OPEN_HASH:
				return new SetSizeMonitor<E>(new UnifiedSet<E>(set), optimizer, index);

			case LINKED:
				return new SetSizeMonitor<E>(new LinkedHashSet<E>(set), optimizer, index);

			default:
				break;

			}

		}

		return createNormalSet(type, set);

	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createFullMonitor(CollectionTypeEnum type, SetAllocationOptimizer context,
			int initialCapacity) {

		int index = context.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {

			case HASH:
				return new HashSetFullMonitor<E>(initialCapacity, context, index);

			case ARRAY:
				return new SetFullMonitor<E>(new ArraySet(initialCapacity), context, index);

			case OPEN_HASH:
				return new SetFullMonitor<E>(new UnifiedSet<E>(initialCapacity), context, index);

			case LINKED:
				return new SetFullMonitor<E>(new LinkedHashSet<E>(initialCapacity), context, index);

			default:
				break;

			}

		}

		return createNormalSet(type, initialCapacity);
	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> createFullMonitor(CollectionTypeEnum type, SetAllocationOptimizer context,
			Collection<? extends E> set) {

		int index = context.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {

			case HASH:
				return new HashSetFullMonitor<E>(set, context, index);

			case ARRAY:
				return new SetFullMonitor<E>(new ArraySet(set), context, index);

			case OPEN_HASH:
				return new SetFullMonitor<E>(new UnifiedSet<E>(set), context, index);

			case LINKED:
				return new SetFullMonitor<E>(new LinkedHashSet<E>(set), context, index);

			default:
				break;

			}

		}

		return createNormalSet(type, set);
	}

}
