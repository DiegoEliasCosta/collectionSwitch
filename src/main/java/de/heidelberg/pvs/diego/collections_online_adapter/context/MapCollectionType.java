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

		switch (this) {

		case JDK_HASHMAP:
			return new HashMap<>(initialCapacity);
			
		case KOLOBOKE_HASHMAP:
			return HashObjObjMaps.newMutableMap(initialCapacity);
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			return new AdaptiveMap<>(initialCapacity);
			
		case JDK_LINKEDHASHMAP:
			return new LinkedHashMap<>(initialCapacity);

		case FASTUTILS_HASHMAP:
			return new THashMap<>(initialCapacity);

		case GSCOLLECTIONS_UNIFIEDMAP:
			return new UnifiedMap<>(initialCapacity);
			
		case GOOGLE_ARRAYMAP:
			return new com.google.api.client.util.ArrayMap<>();
			
		case NLP_ARRAYMAP:
			return new ArrayMap<>(initialCapacity);
			
		case NAYUKI_COMPACTHASHMAP:
			return new CompactHashMap<K, V>();
			
		default:
			return new HashMap<>(initialCapacity);
		}

	}

	public <K, V> Map<K, V> createMap() {


		switch (this) {

		case JDK_HASHMAP:
			return new HashMap<>();
			
		case KOLOBOKE_HASHMAP:
			return HashObjObjMaps.newMutableMap();
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			return new AdaptiveMap<>();
			
		case JDK_LINKEDHASHMAP:
			return new LinkedHashMap<>();

		case FASTUTILS_HASHMAP:
			return new THashMap<>();

		case GSCOLLECTIONS_UNIFIEDMAP:
			return new UnifiedMap<>();
			
		case GOOGLE_ARRAYMAP:
			return new com.google.api.client.util.ArrayMap<>();
			
		case NLP_ARRAYMAP:
			return new ArrayMap<>();
			
		case NAYUKI_COMPACTHASHMAP:
			return new CompactHashMap<K, V>();
			
		default:
			return new HashMap<>();
		}

	}
	
	public <K, V> Map<K, V> createMap(Map<K, V> mapToCopy) {

		switch (this) {

		case JDK_HASHMAP:
			return new HashMap<>(mapToCopy);
			
		case KOLOBOKE_HASHMAP:
			return HashObjObjMaps.newMutableMap(mapToCopy);
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			return new AdaptiveMap<>(mapToCopy);
			
		case JDK_LINKEDHASHMAP:
			return new LinkedHashMap<>(mapToCopy);

		case FASTUTILS_HASHMAP:
			return new THashMap<>(mapToCopy);

		case GSCOLLECTIONS_UNIFIEDMAP:
			return new UnifiedMap<>(mapToCopy);
			
		case GOOGLE_ARRAYMAP:
			Map<K, V> map = new com.google.api.client.util.ArrayMap<>();
			map.putAll(mapToCopy);
			return map;
			
		case NLP_ARRAYMAP:
			return new ArrayMap<>(mapToCopy);
			
		case NAYUKI_COMPACTHASHMAP:
			Map<K, V> map2 = new CompactHashMap<K, V>();
			map2.putAll(mapToCopy);
			return map2;
			
		default:
			return new HashMap<>(mapToCopy);
		}

	}

}
