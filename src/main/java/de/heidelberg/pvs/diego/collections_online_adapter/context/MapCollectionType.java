package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveMap;
import edu.stanford.nlp.util.ArrayMap;
import gnu.trove.map.hash.THashMap;
import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;
import vlsi.utils.CompactHashMap;

public enum MapCollectionType {

	JDK_HASHMAP, 
	KOLOBOKE_HASHMAP,
	ONLINEADAPTER_ADAPTIVEMAP, 

	JDK_LINKEDHASHMAP,
	
	FASTUTILS_HASHMAP, 
	GSCOLLECTIONS_UNIFIEDMAP, 
	
	NLP_ARRAYMAP,
	GOOGLE_ARRAYMAP, NAYUKI_COMPACTHASHMAP;

	public <K, V> Map<K, V> createMap(int initialCapacity) {

		Map<K, V> map;

		switch (this) {

		case JDK_HASHMAP:
			map = new HashMap<>(initialCapacity);
			
		case KOLOBOKE_HASHMAP:
			map = HashObjObjMaps.newMutableMap(initialCapacity);
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			map = new AdaptiveMap<>(initialCapacity);
			
		case JDK_LINKEDHASHMAP:
			map = new LinkedHashMap<>(initialCapacity);

		case FASTUTILS_HASHMAP:
			map = new THashMap<>(initialCapacity);

		case GSCOLLECTIONS_UNIFIEDMAP:
			map = new UnifiedMap<>(initialCapacity);
			
		case GOOGLE_ARRAYMAP:
			map = new com.google.api.client.util.ArrayMap<>();
			
		case NLP_ARRAYMAP:
			map = new ArrayMap<>(initialCapacity);
			
		case NAYUKI_COMPACTHASHMAP:
			map = new CompactHashMap<K, V>();
			
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
			
		case KOLOBOKE_HASHMAP:
			map = HashObjObjMaps.newMutableMap();
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			map = new AdaptiveMap<>();
			
		case JDK_LINKEDHASHMAP:
			map = new LinkedHashMap<>();

		case FASTUTILS_HASHMAP:
			map = new THashMap<>();

		case GSCOLLECTIONS_UNIFIEDMAP:
			map = new UnifiedMap<>();
			
		case GOOGLE_ARRAYMAP:
			map = new com.google.api.client.util.ArrayMap<>();
			
		case NLP_ARRAYMAP:
			map = new ArrayMap<>();
			
		case NAYUKI_COMPACTHASHMAP:
			map = new CompactHashMap<K, V>();
			
		default:
			map = new HashMap<>();
		}

		return map;
	}
	
	public <K, V> Map<K, V> createMap(Map<K, V> mapToCopy) {

		Map<K, V> map;

		switch (this) {

		case JDK_HASHMAP:
			map = new HashMap<>(mapToCopy);
			
		case KOLOBOKE_HASHMAP:
			map = HashObjObjMaps.newMutableMap(mapToCopy);
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			map = new AdaptiveMap<>(mapToCopy);
			
		case JDK_LINKEDHASHMAP:
			map = new LinkedHashMap<>(mapToCopy);

		case FASTUTILS_HASHMAP:
			map = new THashMap<>(mapToCopy);

		case GSCOLLECTIONS_UNIFIEDMAP:
			map = new UnifiedMap<>(mapToCopy);
			
		case GOOGLE_ARRAYMAP:
			map = new com.google.api.client.util.ArrayMap<>();
			map.putAll(mapToCopy);
			
		case NLP_ARRAYMAP:
			map = new ArrayMap<>(mapToCopy);
			
		case NAYUKI_COMPACTHASHMAP:
			map = new CompactHashMap<K, V>();
			map.putAll(mapToCopy);
			
		default:
			map = new HashMap<>(mapToCopy);
		}

		return map;
	}

}
