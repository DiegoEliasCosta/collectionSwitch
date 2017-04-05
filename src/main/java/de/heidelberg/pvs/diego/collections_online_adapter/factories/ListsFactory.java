package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.HashArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.LinkedListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.LinkedListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListProactiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListProactiveSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ListsFactory {

	public static  List createNormalList(CollectionTypeEnum type, int initialCapacity) {

		switch (type) {

		case ARRAY:
			return new ArrayList(initialCapacity);
		case LINKED:
			return new LinkedList();
		case HASH:
			return new HashArrayList(initialCapacity);
		default:
			break;
		}

		return null;

	}

	public static  List createNormalList(CollectionTypeEnum type, Collection list) {

		switch (type) {

		case ARRAY:
			return new ArrayList(list);
		case LINKED:
			return new LinkedList(list);
		case HASH:
			return new HashArrayList(list);
		default:
			break;
		}

		return null;
	}

	public static  List createSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			int initialCapacity) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {
			switch (type) {
			case ARRAY:
				return new ArrayListSizeMonitor(initialCapacity, optimizer, index);
			case LINKED:
				return new LinkedListSizeMonitor(optimizer, index);
			case HASH:
				return new ListSizeMonitor(new HashArrayList(initialCapacity), optimizer, index);
			default:
				break;
			}

		}

		return createNormalList(type, initialCapacity);

	}

	public static  List createSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			Collection list) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {
			switch (type) {
			case ARRAY:
				return new ArrayListSizeMonitor(list, optimizer, index);
			case LINKED:
				return new LinkedListSizeMonitor(list, optimizer, index);
			case HASH:
				return new ListSizeMonitor(new HashArrayList(list), optimizer, index);
			default:
				break;
			}
		}

		return createNormalList(type, list);
	}

	public static  List createProactiveSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			Collection list, int sizeThreshold) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new ListProactiveSizeMonitor(new ArrayList(list), optimizer, sizeThreshold, index);
			case LINKED:
				return new ListProactiveSizeMonitor(new LinkedList(list), optimizer, sizeThreshold, index);
			case HASH:
				return new ListProactiveSizeMonitor(new HashArrayList(list), optimizer, sizeThreshold, index);
			default:
				break;
			}

		}

		return createNormalList(type, list);
	}

	public static  List createFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			int initialCapacity) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new ArrayListFullMonitor(initialCapacity, optimizer, index);
			case LINKED:
				return new LinkedListFullMonitor(optimizer, index);
			case HASH:
				return new HashArrayListFullMonitor(initialCapacity, optimizer, index);
			default:
				break;
			}

		}

		return createNormalList(type, initialCapacity);
	}

	public static  List createFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			Collection list) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new ArrayListFullMonitor(list, optimizer, index);
			case LINKED:
				return new LinkedListFullMonitor(list, optimizer, index);
			case HASH:
				return new HashArrayListFullMonitor(list, optimizer, index);
			default:
				break;
			}

		}

		return createNormalList(type, list);
	}

	public static  List createProactiveFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			Collection list) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new ListProactiveFullMonitor(new ArrayList(list), optimizer, index);
			case LINKED:
				return new ListProactiveFullMonitor(new LinkedList(list), optimizer, index);
			case HASH:
				return new ListProactiveFullMonitor(new HashArrayList(list), optimizer, index);
			default:
				break;
			}

		}

		return createNormalList(type, list);
	}

}
