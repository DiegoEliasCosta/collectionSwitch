package de.heidelberg.pvs.diego.collections_online_adapter.factories;

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
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class MapFactory {

	public static <K, V> Map<K, V> createNormalMap(CollectionTypeEnum type, int initialCapacity) {

		switch (type) {
		case ARRAY:
			return ArrayMap.create(initialCapacity);

		case ARRAY_HASH:
			return new UnifiedMap<>(initialCapacity);

		case HASH:
			return new HashMap<>(initialCapacity);

		case LINKED:
			return new LinkedHashMap<>(initialCapacity);

		default:
			break;
		}

		return null;
	}

	public static <K, V> Map<K, V> createNormalMap(CollectionTypeEnum type, Map<K, V> map) {
		switch (type) {
		case ARRAY:
			Map<K, V> arrayMap = new ArrayMap<>();
			arrayMap.putAll(map);
			return arrayMap;

		case ARRAY_HASH:
			return new UnifiedMap<>(map);

		case HASH:
			return new HashMap<>(map);

		case LINKED:
			return new LinkedHashMap<>(map);

		default:
			break;
		}

		return null;
	}

	public static <K, V> Map<K, V> createSizeMonitor(CollectionTypeEnum type, MapAllocationOptimizer optimizer,
			int initialCapacity) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new MapSizeMonitor<>(ArrayMap.create(initialCapacity), optimizer, index);

			case ARRAY_HASH:
				return new MapSizeMonitor<>(new UnifiedMap<>(initialCapacity), optimizer, index);

			case HASH:
				return new HashMapSizeMonitor<>(initialCapacity, optimizer, index);

			case LINKED:
				return new MapSizeMonitor<>(new LinkedHashMap<>(initialCapacity), optimizer, index);

			default:
				break;
			}

		}

		return createNormalMap(type, initialCapacity);
	}

	public static <K, V> Map<K, V> createSizeMonitor(CollectionTypeEnum type, MapAllocationOptimizer optimizer,
			Map<K, V> map) {

		int index = optimizer.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				Map<K, V> arrayMap = new ArrayMap<>();
				arrayMap.putAll(map);
				return new MapSizeMonitor<>(arrayMap, optimizer, index);

			case ARRAY_HASH:
				return new MapSizeMonitor<>(new UnifiedMap<>(map), optimizer, index);

			case HASH:
				return new HashMapSizeMonitor<>(map, optimizer, index);

			case LINKED:
				return new MapSizeMonitor<>(new LinkedHashMap<>(map), optimizer, index);

			default:
				break;
			}

		}
		return createNormalMap(type, map);
	}

	public static <K, V> Map<K, V> createFullMonitor(CollectionTypeEnum type, MapAllocationOptimizer context,
			int initialCapacity) {

		int index = context.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				return new MapFullMonitor<>(ArrayMap.create(initialCapacity), context, index);

			case ARRAY_HASH:
				return new MapFullMonitor<>(new UnifiedMap<>(initialCapacity), context, index);

			case HASH:
				return new HashMapFullMonitor<>(initialCapacity, context, index);

			case LINKED:
				return new MapFullMonitor<>(new LinkedHashMap<>(initialCapacity), context, index);

			default:
				break;
			}

		}

		return createNormalMap(type, initialCapacity);
	}

	public static <K, V> Map<K, V> createFullMonitor(CollectionTypeEnum type, MapAllocationOptimizer context,
			Map<K, V> map) {

		int index = context.getMonitoringIndex();

		if (index >= 0) {

			switch (type) {
			case ARRAY:
				Map<K, V> arrayMap = new ArrayMap<>();
				arrayMap.putAll(map);
				return new MapFullMonitor<>(arrayMap, context, index);

			case ARRAY_HASH:
				return new MapFullMonitor<>(new UnifiedMap<>(map), context, index);

			case HASH:
				return new HashMapFullMonitor<>(map, context, index);

			case LINKED:
				return new MapFullMonitor<>(new LinkedHashMap<>(map), context, index);

			default:
				break;
			}

		}

		return createNormalMap(type, map);
	}

}
