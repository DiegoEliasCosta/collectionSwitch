package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.HashArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ListsFactory {

	public static <E> List<E> createNormalLists(CollectionTypeEnum type, int initialCapacity) {

		switch (type) {

		case ARRAY:
			return new ArrayList<E>(initialCapacity);
		case LINKED:
			return new LinkedList<E>();
		case HASH:
			return new HashArrayList<E>(initialCapacity);
		default:
			break;
		}

		return null;

	}

	public static <E> List<E> createNormalLists(CollectionTypeEnum type, Collection<? extends E> list) {

		switch (type) {

		case ARRAY:
			return new ArrayList<E>(list);
		case LINKED:
			return new LinkedList<E>(list);
		case HASH:
			return new HashArrayList<E>(list);
		default:
			break;
		}

		return null;
	}

	public static <E> List<E> createSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer context,
			int initialCapacity) {

		switch (type) {
		case ARRAY:
			return new ArrayListSizeMonitor<>(initialCapacity, context);
		case LINKED:
			return new LinkedListSizeMonitor<>(context);
		case HASH:
			return new ListSizeMonitor<>(new HashArrayList<>(initialCapacity), context);
		default:
			break;
		}

		return null;

	}

	public static <E> List<E> createSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer context,
			Collection<? extends E> list) {

		switch (type) {
		case ARRAY:
			return new ArrayListSizeMonitor<>(list, context);
		case LINKED:
			return new LinkedListSizeMonitor<>(list, context);
		case HASH:
			return new ListSizeMonitor<>(new HashArrayList<>(list), context);
		default:
			break;
		}

		return null;
	}

	public static <E> List<E> createFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer context,
			int initialCapacity) {

		switch (type) {
		case ARRAY:
			return new ArrayListFullMonitor<>(initialCapacity, context);
		case LINKED:
			return new LinkedListFullMonitor<>(context);
		case HASH:
			return new HashArrayListFullMonitor<>(initialCapacity, context);
		default:
			break;
		}

		return null;
	}

	public static <E> List<E> createFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer context,
			Collection<? extends E> list) {
		switch (type) {
		case ARRAY:
			return new ArrayListFullMonitor<>(list, context);
		case LINKED:
			return new LinkedListFullMonitor<>(list, context);
		case HASH:
			return new HashArrayListFullMonitor<>(list, context);
		default:
			break;
		}

		return null;
	}

}
