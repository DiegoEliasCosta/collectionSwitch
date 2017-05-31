package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.rmi.server.LoaderHandler;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import com.google.api.client.util.ArrayMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.HashMapFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.HashMapSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.ProactiveMapFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class MapFactory {

	public static <K, V> Map<K, V> createNormalMap(CollectionTypeEnum type, int initialCapacity, float loadFactor) {

		switch (type) {
		case ARRAY:
			return ArrayMap.create(initialCapacity);

		case OPEN_HASH:
			return new UnifiedMap<K, V>(initialCapacity, loadFactor);

		case HASH:
			return new HashMap<K, V>(initialCapacity, loadFactor);

		case LINKED:
			return new LinkedHashMap<K, V>(initialCapacity, loadFactor);

		default:
			break;
		}

		return null;
	}

	public static <K, V> Map<K, V> createNormalMap(CollectionTypeEnum type, Map<K, V> map) {
		switch (type) {
		case ARRAY:
			Map<K, V> arrayMap = new ArrayMap<K, V>();
			arrayMap.putAll(map);
			return arrayMap;

		case OPEN_HASH:
			return new UnifiedMap<K, V>(map);

		case HASH:
			return new HashMap<K, V>(map);

		case LINKED:
			return new LinkedHashMap<K, V>(map);

		default:
			break;
		}

		return null;
	}

	public static <K, V> Map<K, V> createSizeMonitor(CollectionTypeEnum type, MapAllocationOptimizer optimizer,
			int initialCapacity, float loadFactor) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new MapSizeMonitor(ArrayMap.create(initialCapacity), optimizer, index);

			case OPEN_HASH:
				return new MapSizeMonitor<K, V>(new UnifiedMap<K, V>(initialCapacity, loadFactor), optimizer, index);

			case HASH:
				return new HashMapSizeMonitor<K, V>(initialCapacity, loadFactor, optimizer, index);

			case LINKED:
				return new MapSizeMonitor<K, V>(new LinkedHashMap<K, V>(initialCapacity, loadFactor), optimizer, index);

			default:
				break;
			}

		}

		return createNormalMap(type, initialCapacity, loadFactor);
	}
	
	public static <K, V> Map<K, V> createProactiveSizeMonitor(CollectionTypeEnum type, MapAllocationOptimizer optimizer,
			int initialCapacity, float loadFactor) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {
			
			ProactiveMapFullMonitor<K, V> monitor;

			switch (type) {
			case ARRAY:
				monitor = new ProactiveMapFullMonitor(ArrayMap.create(initialCapacity), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;

			case OPEN_HASH:
				monitor = new ProactiveMapFullMonitor<K, V>(new UnifiedMap<K, V>(initialCapacity, loadFactor), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			case HASH:
				monitor = new ProactiveMapFullMonitor<K, V>(new HashMap<K, V>(initialCapacity, loadFactor), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
					
			case LINKED:
				monitor = new ProactiveMapFullMonitor<K, V>(new LinkedHashMap<K, V>(initialCapacity, loadFactor), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			default:
				break;
			}

		}

		return createNormalMap(type, initialCapacity, loadFactor);
	}
	

	public static <K, V> Map<K, V> createSizeMonitor(CollectionTypeEnum type, MapAllocationOptimizer optimizer,
			Map<K, V> map) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				Map<K, V> arrayMap = new ArrayMap<K, V>();
				arrayMap.putAll(map);
				return new MapSizeMonitor<K, V>(arrayMap, optimizer, index);

			case OPEN_HASH:
				return new MapSizeMonitor<K, V>(new UnifiedMap<K, V>(map), optimizer, index);

			case HASH:
				return new HashMapSizeMonitor<K, V>(map, optimizer, index);

			case LINKED:
				return new MapSizeMonitor<K, V>(new LinkedHashMap<K, V>(map), optimizer, index);

			default:
				break;
			}

		}
		return createNormalMap(type, map);
	}
	
	public static <K, V> Map<K, V> createProactiveSizeMonitor(CollectionTypeEnum type, MapAllocationOptimizer optimizer,
			Map<K,V> map) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {
			
			ProactiveMapFullMonitor<K, V> monitor;

			switch (type) {
			case ARRAY:
				Map<K, V> arrayMap = new ArrayMap<K, V>();
				arrayMap.putAll(map);
				monitor = new ProactiveMapFullMonitor<K, V>(arrayMap, optimizer, index);
				optimizer.addReference(monitor);
				return monitor;

			case OPEN_HASH:
				monitor = new ProactiveMapFullMonitor<K, V>(new UnifiedMap<K, V>(map), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			case HASH:
				monitor = new ProactiveMapFullMonitor<K, V>(new HashMap<K, V>(map), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
					
			case LINKED:
				monitor = new ProactiveMapFullMonitor<K, V>(new LinkedHashMap<K, V>(map), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			default:
				break;
			}

		}

		return createNormalMap(type, map);
	}

	public static <K, V> Map<K, V> createFullMonitor(CollectionTypeEnum type, MapAllocationOptimizer context,
			int initialCapacity, float loadFactor) {

		int index = context.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new MapFullMonitor(ArrayMap.create(initialCapacity), context, index);

			case OPEN_HASH:
				return new MapFullMonitor<K, V>(new UnifiedMap<K, V>(initialCapacity, loadFactor), context, index);

			case HASH:
				return new HashMapFullMonitor<K, V>(initialCapacity, loadFactor, context, index);

			case LINKED:
				return new MapFullMonitor<K, V>(new LinkedHashMap<K, V>(initialCapacity, loadFactor), context, index);

			default:
				break;
			}

		}

		return createNormalMap(type, initialCapacity, loadFactor);
	}
	
	public static <K, V> Map<K, V> createProactiveFullMonitor(CollectionTypeEnum type, MapAllocationOptimizer optimizer,
			int initialCapacity, float loadFactor) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {
			
			ProactiveMapFullMonitor<K, V> monitor;

			switch (type) {
			case ARRAY:
				monitor = new ProactiveMapFullMonitor(ArrayMap.create(initialCapacity), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;

			case OPEN_HASH:
				monitor = new ProactiveMapFullMonitor<K, V>(new UnifiedMap<K, V>(initialCapacity, loadFactor), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			case HASH:
				monitor = new ProactiveMapFullMonitor<K, V>(new HashMap<K, V>(initialCapacity, loadFactor), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
					
			case LINKED:
				monitor = new ProactiveMapFullMonitor<K, V>(new LinkedHashMap<K, V>(initialCapacity, loadFactor), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			default:
				break;
			}

		}

		return createNormalMap(type, initialCapacity, loadFactor);
	}

	public static <K, V> Map<K, V> createFullMonitor(CollectionTypeEnum type, MapAllocationOptimizer context,
			Map<K, V> map) {

		int index = context.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				Map<K, V> arrayMap = new ArrayMap<K, V>();
				arrayMap.putAll(map);
				return new MapFullMonitor<K, V>(arrayMap, context, index);

			case OPEN_HASH:
				return new MapFullMonitor<K, V>(new UnifiedMap<K, V>(map), context, index);

			case HASH:
				return new HashMapFullMonitor<K, V>(map, context, index);

			case LINKED:
				return new MapFullMonitor<K, V>(new LinkedHashMap<K, V>(map), context, index);

			default:
				break;
			}

		}

		return createNormalMap(type, map);
	}
	
	public static <K, V> Map<K, V> createProactiveFullMonitor(CollectionTypeEnum type, MapAllocationOptimizer optimizer,
			Map<K,V> map) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {
			
			ProactiveMapFullMonitor<K, V> monitor;

			switch (type) {
			case ARRAY:
				Map<K, V> arrayMap = new ArrayMap<K, V>();
				arrayMap.putAll(map);
				monitor = new ProactiveMapFullMonitor<K, V>(arrayMap, optimizer, index);
				optimizer.addReference(monitor);
				return monitor;

			case OPEN_HASH:
				monitor = new ProactiveMapFullMonitor<K, V>(new UnifiedMap<K, V>(map), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			case HASH:
				monitor = new ProactiveMapFullMonitor<K, V>(new HashMap<K, V>(map), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
					
			case LINKED:
				monitor = new ProactiveMapFullMonitor<K, V>(new LinkedHashMap<K, V>(map), optimizer, index);
				optimizer.addReference(monitor);
				return monitor;
			default:
				break;
			}

		}

		return createNormalMap(type, map);
	}

}
