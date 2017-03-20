package de.heidelberg.pvs.diego.collections_online_adapter.context.facade;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.FirstSamplesMapMemoryOptimizer;

public class MapAllocationContextFacade<K, V> implements MapAllocationContext<K, V> {
	
	private MapAllocationContext<K, V> context;
	
	public MapAllocationContextFacade(CollectionTypeEnum collectionType) {
		super();
		this.context = new FirstSamplesMapMemoryOptimizer<K, V>(collectionType);
	}

	public Map<K, V> createMap() {
		return context.createMap();
	}

	public Map<K, V> createMap(int initialCapacity) {
		return context.createMap(initialCapacity);
	}

	public Map<K, V> createMap(Map<K, V> map) {
		return context.createMap(map);
	}

	@Override
	public void updateSize(int size) {
		context.updateSize(size);
	}

	@Override
	public boolean isOnline() {
		return context.isOnline();
	}
	
	

}
