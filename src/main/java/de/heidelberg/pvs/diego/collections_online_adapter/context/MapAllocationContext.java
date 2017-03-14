package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Map;

public interface MapAllocationContext<K, V> {
	
	Map<K, V> createMap();

	Map<K, V> createMap(int initialCapacity);

	Map<K, V> createMap(Map<K, V> map);

	void updateSize(int size);

}
