package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Map;

public interface MapAllocationContext<K, V> {
	
	public Map<K, V> createMap();

	public Map<K, V> createMap(int initialCapacity);

	public Map<K, V> createMap(Map<K, V> map);

	public void updateSize(int size);
	
	public boolean isOnline();

}
