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

	
	public static <E> List<E> createNormalList(CollectionTypeEnum type) {

		switch (type) {

		case ARRAY:
			return new ArrayList<E>();
		case LINKED:
			return new LinkedList<E>();
		case HASH:
			return new HashArrayList<E>();
		default:
			break;
		}

		return null;

	}
	
	public static <E> List<E> createNormalList(CollectionTypeEnum type, int initialCapacity) {

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

	public static <E> List<E> createNormalList(CollectionTypeEnum type, Collection<? extends E> list) {

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

	
	public static <E> List<E> createSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			int initialCapacity) {

		int index = optimizer.getMonitoringIndex();
		
		if (index >= 0) {
			
			switch (type) {
			case ARRAY:
				return new ArrayListSizeMonitor<E>(initialCapacity, optimizer, index);
			case LINKED:
				return new LinkedListSizeMonitor<E>(optimizer, index);
			case HASH:
				return new ListSizeMonitor<E>(new HashArrayList<E>(initialCapacity), optimizer, index);
			default:
				break;
			}

		}

		return createNormalList(type, initialCapacity);

	}
	
	public static <E> List<E> createProactiveSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			int initialCapacity) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			ListProactiveSizeMonitor<E> monitor;
			switch (type) {
			case ARRAY:
				monitor = new ListProactiveSizeMonitor<E>(new ArrayList<E>(initialCapacity), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			case LINKED:
				monitor = new ListProactiveSizeMonitor<E>(new LinkedList<E>(), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
				
			case HASH:
				monitor = new ListProactiveSizeMonitor<E>(new HashArrayList<E>(initialCapacity), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			default:
				break;
			}

		}

		return createNormalList(type, initialCapacity);
	}

	public static <E> List<E> createSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			Collection<? extends E> list) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {
			switch (type) {
			case ARRAY:
				return new ArrayListSizeMonitor<E>(list, optimizer, index);
			case LINKED:
				return new LinkedListSizeMonitor<E>(list, optimizer, index);
			case HASH:
				return new ListSizeMonitor<E>(new HashArrayList<E>(list), optimizer, index);
			default:
				break;
			}
		}

		return createNormalList(type, list);
	}

	public static <E> List<E> createProactiveSizeMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			Collection<? extends E>list) {


		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			ListProactiveSizeMonitor<E> monitor;
			switch (type) {
			case ARRAY:
				monitor = new ListProactiveSizeMonitor<E>(new ArrayList<E>(list), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			case LINKED:
				monitor = new ListProactiveSizeMonitor<E>(new LinkedList<E>(list), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
				
			case HASH:
				monitor = new ListProactiveSizeMonitor<E>(new HashArrayList<E>(list), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			default:
				break;
			}

		}

		return createNormalList(type, list);
	}

	public static <E> List<E> createFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			int initialCapacity) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new ArrayListFullMonitor<E>(initialCapacity, optimizer, index);
			case LINKED:
				return new LinkedListFullMonitor<E>(optimizer, index);
			case HASH:
				return new HashArrayListFullMonitor<E>(initialCapacity, optimizer, index);
			default:
				break;
			}

		}

		return createNormalList(type, initialCapacity);
	}
	
	public static <E> List<E> createProactiveFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			int initialCapacity) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			ListProactiveFullMonitor<E> monitor;
			switch (type) {
			case ARRAY:
				monitor = new ListProactiveFullMonitor<E>(new ArrayList<E>(initialCapacity), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			case LINKED:
				monitor = new ListProactiveFullMonitor<E>(new LinkedList<E>(), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
				
			case HASH:
				monitor = new ListProactiveFullMonitor<E>(new HashArrayList<E>(initialCapacity), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			default:
				break;
			}

		}

		return createNormalList(type, initialCapacity);
	}

	public static <E> List<E> createFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			Collection<? extends E>list) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new ArrayListFullMonitor<E>(list, optimizer, index);
			case LINKED:
				return new LinkedListFullMonitor<E>(list, optimizer, index);
			case HASH:
				return new HashArrayListFullMonitor<E>(list, optimizer, index);
			default:
				break;
			}

		}

		return createNormalList(type, list);
	}

	public static <E> List<E> createProactiveFullMonitor(CollectionTypeEnum type, ListAllocationOptimizer optimizer,
			Collection<? extends E>list) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {
			
			ListProactiveFullMonitor<E> monitor;
			switch (type) {
			case ARRAY:
				monitor = new ListProactiveFullMonitor<E>(new ArrayList<E>(list), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			case LINKED:
				monitor = new ListProactiveFullMonitor<E>(new LinkedList<E>(list), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
				
			case HASH:
				monitor = new ListProactiveFullMonitor<E>(new HashArrayList<E>(list), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			default:
				break;
			}

		}

		return createNormalList(type, list);
	}

}
