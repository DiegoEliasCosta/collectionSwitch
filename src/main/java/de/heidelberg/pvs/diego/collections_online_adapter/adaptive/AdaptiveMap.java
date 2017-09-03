package de.heidelberg.pvs.diego.collections_online_adapter.adaptive;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import edu.stanford.nlp.util.ArrayMap;

public class AdaptiveMap<K, V> implements Map<K, V> {
	
	private static final int OPENHASH_HIGH_BOUND = 10000;
	private static final int ARRAY_HIGH_BOUND = 10;
	
	private static final int SAMPLE = 50;
	
	private Map<K,V> map;
	CollectionTypeEnum type;
	private int count;
	
	public AdaptiveMap() {
		super();
		map = new ArrayMap<K,V>();
		type = CollectionTypeEnum.ARRAY;
	}
	
	public AdaptiveMap(int initialCapacity) {
		super();
		
		if (initialCapacity < ARRAY_HIGH_BOUND) {
			map = new ArrayMap<K, V>(initialCapacity);
			type = CollectionTypeEnum.ARRAY;
			
		} else if(initialCapacity < OPENHASH_HIGH_BOUND) {
			map = new UnifiedMap<K, V>(initialCapacity);
			type = CollectionTypeEnum.OPEN_HASH;

		} else {
			map = new HashMap<K, V>(initialCapacity);
			type = CollectionTypeEnum.HASH;
		}
		
	}
	
	public AdaptiveMap(int initialCapacity, float loadFactor) {
		super();
		
		if (initialCapacity < ARRAY_HIGH_BOUND) {
			map = new ArrayMap<K, V>(initialCapacity);
			type = CollectionTypeEnum.ARRAY;
			
		} else if(initialCapacity < OPENHASH_HIGH_BOUND) {
			map = new UnifiedMap<K, V>(initialCapacity, loadFactor);
			type = CollectionTypeEnum.OPEN_HASH;

		} else {
			map = new HashMap<K, V>(initialCapacity, loadFactor);
			type = CollectionTypeEnum.HASH;
		}
		
	}
	
	public AdaptiveMap(Map<? extends K, ? extends V> m) {
		super();
		
		if (m.size() < ARRAY_HIGH_BOUND) {
			map = new ArrayMap<K, V>(m);
			type = CollectionTypeEnum.ARRAY;
			
		} else if(m.size() < OPENHASH_HIGH_BOUND) {
			map = new UnifiedMap<K, V>(m);
			type = CollectionTypeEnum.OPEN_HASH;

		} else {
			map = new HashMap<K, V>(m);
			type = CollectionTypeEnum.HASH;
		}
		
	}
	

	private void manageImplementation(int size) {
		switch(type) {
		case ARRAY:
			if(size() > ARRAY_HIGH_BOUND) {
				map= new UnifiedMap<K, V>(map);
				type = CollectionTypeEnum.OPEN_HASH;
			}
			break;
			
		case OPEN_HASH:
			if(size() > OPENHASH_HIGH_BOUND) {
				map = new HashMap<K, V>(map);
				type = CollectionTypeEnum.HASH;
			}
			
			break;
		default:
			break;
			
		}
	}
	

	public V put(K key, V value) {
		if (count++ % SAMPLE == 0)
			manageImplementation(1);
		return map.put(key, value);
	}
	
	public void putAll(Map<? extends K, ? extends V> m) {
		manageImplementation(m.size());
		map.putAll(m);
	}

	
	
	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public V get(Object key) {
		return map.get(key);
	}


	public V remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<V> values() {
		return map.values();
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}

}
