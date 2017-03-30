package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import com.google.api.client.util.ArrayMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps.HashMapFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps.HashMapSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps.MapFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps.MapSizeMonitor;
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

	public static <K, V> Map<K, V> createNormalMap(CollectionTypeEnum type,
			Map<K, V> map) {
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

	public static <K, V> Map<K, V> createSizeMonitor(CollectionTypeEnum type, MapAllocationOptimizer context,
			int initialCapacity) {

		switch (type) {
		case ARRAY:
			return new MapSizeMonitor<>(ArrayMap.create(initialCapacity), context);

		case ARRAY_HASH:
			return new MapSizeMonitor<>(new UnifiedMap<>(initialCapacity), context);

		case HASH:
			return new HashMapSizeMonitor<>(initialCapacity, context);

		case LINKED:
			return new MapSizeMonitor<>(new LinkedHashMap<>(initialCapacity), context);

		default:
			break;
		}

		return null;
	}
	
	public static <K, V> Map<K, V> createSizeMonitor(CollectionTypeEnum type,
			MapAllocationOptimizer context, Map<K, V> map) {
		
		switch (type) {
		case ARRAY:
			Map<K, V> arrayMap = new ArrayMap<>();
			arrayMap.putAll(map);
			return new MapSizeMonitor<>(arrayMap, context);

		case ARRAY_HASH:
			return new MapSizeMonitor<>(new UnifiedMap<>(map), context);

		case HASH:
			return new HashMapSizeMonitor<>(map, context);

		case LINKED:
			return new MapSizeMonitor<>(new LinkedHashMap<>(map), context);

		default:
			break;
		}
		return null;
	}

	public static <K, V> Map<K, V> createFullMonitor(CollectionTypeEnum type, MapAllocationOptimizer context,
			int initialCapacity) {

		switch (type) {
		case ARRAY:
			return new MapFullMonitor<>(ArrayMap.create(initialCapacity), context);

		case ARRAY_HASH:
			return new MapFullMonitor<>(new UnifiedMap<>(initialCapacity), context);

		case HASH:
			return new HashMapFullMonitor<>(initialCapacity, context);

		case LINKED:
			return new MapFullMonitor<>(new LinkedHashMap<>(initialCapacity), context);

		default:
			break;
		}

		return null;
	}

	public static <K, V> Map<K, V> createFullMonitor(CollectionTypeEnum type,
			MapAllocationOptimizer context, Map<K, V> map) {
		
		switch (type) {
		case ARRAY:
			Map<K, V> arrayMap = new ArrayMap<>();
			arrayMap.putAll(map);
			return new MapFullMonitor<>(arrayMap, context);

		case ARRAY_HASH:
			return new MapFullMonitor<>(new UnifiedMap<>(map), context);

		case HASH:
			return new HashMapFullMonitor<>(map, context);

		case LINKED:
			return new MapFullMonitor<>(new LinkedHashMap<>(map), context);

		default:
			break;
		}

		return null;
	}

	

}
