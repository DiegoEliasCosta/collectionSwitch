package de.heidelberg.pvs.diego.collectionswitch.context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import de.heidelberg.pvs.diego.collectionswitch.adaptive.AdaptiveMap;
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
			return new HashMap<K, V>(initialCapacity);
			
		case KOLOBOKE_HASHMAP:
			return HashObjObjMaps.newMutableMap(initialCapacity);
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			return new AdaptiveMap<K, V>(initialCapacity);
			
		case JDK_LINKEDHASHMAP:
			return new LinkedHashMap<K, V>(initialCapacity);

		case FASTUTILS_HASHMAP:
			return new THashMap<K, V>(initialCapacity);

		case GSCOLLECTIONS_UNIFIEDMAP:
			return new UnifiedMap<K, V>(initialCapacity);
			
		case GOOGLE_ARRAYMAP:
			return new com.google.api.client.util.ArrayMap<K, V>();
			
		case NLP_ARRAYMAP:
			return new ArrayMap<K, V>(initialCapacity);
			
		case NAYUKI_COMPACTHASHMAP:
			return new CompactHashMap<K, V>();
			
		default:
			return new HashMap<K, V>(initialCapacity);
		}

	}

	public <K, V> Map<K, V> createMap() {


		switch (this) {

		case JDK_HASHMAP:
			return new HashMap<K, V>();
			
		case KOLOBOKE_HASHMAP:
			return HashObjObjMaps.newMutableMap();
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			return new AdaptiveMap<K, V>();
			
		case JDK_LINKEDHASHMAP:
			return new LinkedHashMap<K, V>();

		case FASTUTILS_HASHMAP:
			return new THashMap<K, V>();

		case GSCOLLECTIONS_UNIFIEDMAP:
			return new UnifiedMap<K, V>();
			
		case GOOGLE_ARRAYMAP:
			return new com.google.api.client.util.ArrayMap<K, V>();
			
		case NLP_ARRAYMAP:
			return new ArrayMap<K, V>();
			
		case NAYUKI_COMPACTHASHMAP:
			return new CompactHashMap<K, V>();
			
		default:
			return new HashMap<K, V>();
		}

	}
	
	public <K, V> Map<K, V> createMap(Map<K, V> mapToCopy) {

		switch (this) {

		case JDK_HASHMAP:
			return new HashMap<K, V>(mapToCopy);
			
		case KOLOBOKE_HASHMAP:
			return HashObjObjMaps.newMutableMap(mapToCopy);
			
		case ONLINEADAPTER_ADAPTIVEMAP:
			return new AdaptiveMap<K, V>(mapToCopy);
			
		case JDK_LINKEDHASHMAP:
			return new LinkedHashMap<K, V>(mapToCopy);

		case FASTUTILS_HASHMAP:
			return new THashMap<K, V>(mapToCopy);

		case GSCOLLECTIONS_UNIFIEDMAP:
			return new UnifiedMap<K, V>(mapToCopy);
			
		case GOOGLE_ARRAYMAP:
			Map<K, V> map = new com.google.api.client.util.ArrayMap<K, V>();
			map.putAll(mapToCopy);
			return map;
			
		case NLP_ARRAYMAP:
			return new ArrayMap<K, V>(mapToCopy);
			
		case NAYUKI_COMPACTHASHMAP:
			Map<K, V> map2 = new CompactHashMap<K, V>();
			map2.putAll(mapToCopy);
			return map2;
			
		default:
			return new HashMap<K, V>(mapToCopy);
		}

	}

}
