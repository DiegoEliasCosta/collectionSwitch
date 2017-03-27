package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.ArraySet;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.HashSetSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.SetOperationMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.SetSizeMonitor;

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
	public static <E> Set<E> createSizeMonitor(CollectionTypeEnum type, SetAllocationContext<E> context,
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
	public static <E> Set<E> createSizeMonitor(CollectionTypeEnum type, SetAllocationContext<E> context,
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
	public static <E> Set<E> createFullMonitor(CollectionTypeEnum type, SetAllocationContext<E> context,
			int initialCapacity) {

		switch (type) {

		case HASH:
			return new SetOperationMonitor<>(new HashSet<>(initialCapacity), context);

		case ARRAY:
			return new SetOperationMonitor<>(new ArraySet(initialCapacity), context);

		case ARRAY_HASH:
			return new SetOperationMonitor<>(new UnifiedSet<>(initialCapacity), context);

		case LINKED:
			return new SetOperationMonitor<>(new LinkedHashSet<>(initialCapacity), context);

		default:
			break;

		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <E> Set<E> createFullMonitor(CollectionTypeEnum type, SetAllocationContext<E> context,
			Collection<? extends E> set) {

		switch (type) {

		case HASH:
			return new SetOperationMonitor<>(new HashSet<>(set), context);

		case ARRAY:
			return new SetOperationMonitor<>(new ArraySet(set), context);

		case ARRAY_HASH:
			return new SetOperationMonitor<>(new UnifiedSet<>(set), context);

		case LINKED:
			return new SetOperationMonitor<>(new LinkedHashSet<>(set), context);

		default:
			break;

		}

		return null;
	}


}
