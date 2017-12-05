package de.heidelberg.pvs.diego.collectionswitch.adaptive;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.api.client.util.ArrayMap;

import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

public class AdaptiveMap<K, V> implements Map<K, V> {
	
	private static final int TURNING_POINT = 50;
	
	private Map<K,V> map;

	private boolean transformed;
	
	public AdaptiveMap() {
		super();
		map = new ArrayMap<K,V>();
	}
	
	public AdaptiveMap(int initialCapacity) {
		super();
		if (initialCapacity < TURNING_POINT) {
			map = new ArrayMap<K, V>();
			
		} else  {
			map = HashObjObjMaps.newMutableMap(initialCapacity);
			transformed = true;
		} 
	}
	
	public AdaptiveMap(int initialCapacity, float loadFactor) {
		super();
		if (initialCapacity < TURNING_POINT) {
			map = new ArrayMap<K, V>();
			
		} else  {
			map = HashObjObjMaps.newMutableMap(initialCapacity);
			transformed = true;
		} 
	}
	
	public AdaptiveMap(Map<? extends K, ? extends V> m) {
		super();
		if (m.size() < TURNING_POINT) {
			map = new ArrayMap<K, V>();
			map.putAll(m);
			
		} else  {
			map = HashObjObjMaps.newMutableMap(m);
			transformed = true;
		} 
	}
	

	private void manageImplementation(int delta) {
		int newSize = size() + delta;
		if (!transformed && newSize > TURNING_POINT) {
			map = HashObjObjMaps.newMutableMap(map);
			transformed = true;
		}
	}
	
	// -----------------------------------------------------
	// 						ADD	
	// -----------------------------------------------------

	public V put(K key, V value) {
		if(!transformed) {
			manageImplementation(size() + 1);
		}
		return map.put(key, value);
	}
	
	public void putAll(Map<? extends K, ? extends V> m) {
		if(!transformed) {
			manageImplementation(m.size());
		}
		map.putAll(m);
	}
	
	// -----------------------------------------------------
	
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
