package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import gnu.trove.map.hash.THashMap;
import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

public enum MapCollectionType {

	FASTUTILS_HASHMAP, GSCOLLECTIONS_UNIFIEDMAP, JDK_HASHMAP, JDK_LINKEDHASHMAP, KOLOBOKE_HASHMAP, NLP_ARRAYMAP, ONLINEADAPTER_ARRAYMAP, ONLINEADAPTER_BINARYARRAYMAP;

	public <K, V> Map<K, V> createMap(int initialCapacity) {

		Map<K, V> map;

		switch (this) {

		case JDK_HASHMAP:
			map = new HashMap<>(initialCapacity);

		case FASTUTILS_HASHMAP:
			map = new THashMap<>(initialCapacity);

		case GSCOLLECTIONS_UNIFIEDMAP:
			map = new UnifiedMap<>(initialCapacity);

		default:
			map = new HashMap<>(initialCapacity);
		}

		return map;
	}

	public <K, V> Map<K, V> createMap() {

		Map<K, V> map;

		switch (this) {

		case JDK_HASHMAP:
			map = new HashMap<>();

		case FASTUTILS_HASHMAP:
			map = new THashMap<>();

		case GSCOLLECTIONS_UNIFIEDMAP:
			map = new UnifiedMap<>();
			
		case JDK_LINKEDHASHMAP:
			map = new LinkedHashMap<>();
			
		case KOLOBOKE_HASHMAP:
			map = HashObjObjMaps.newMutableMap();

		default:
			map = new HashMap<>();
		}

		return map;
	}

}
