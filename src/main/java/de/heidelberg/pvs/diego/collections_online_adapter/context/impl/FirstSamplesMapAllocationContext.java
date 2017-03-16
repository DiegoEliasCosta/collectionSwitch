package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps.HashMapSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps.MapSizeMonitor;

public class FirstSamplesMapAllocationContext<K, V> implements MapAllocationContext<K, V> {

	private static final int ARRAY_THRESHOLD = 30;

	private static final int SAMPLES = 10;
	
	CollectionTypeEnum collectionType;

	private volatile int initialCapacity;
	private volatile int count;
	
	// Monitored data
	private int[] sizes = new int[SAMPLES];

	public FirstSamplesMapAllocationContext(CollectionTypeEnum type) {
		super();
		this.collectionType = type;
	}

	@Override
	public Map<K, V> createMap() {

		switch (collectionType) {

		case ARRAY:
			return isOnline() ? new MapSizeMonitor<>(new UnifiedMap<K, V>(), this) : new UnifiedMap<K, V>();

		case HASH:
			return isOnline() ? new MapSizeMonitor<>(new HashMap<K, V>(), this) : new HashMap<K, V>();

		case LINKED:
			return isOnline() ? new MapSizeMonitor<>(new LinkedHashMap<K, V>(), this) : new LinkedHashMap<K, V>();

		}

		return null;
	}

	@Override
	public Map<K, V> createMap(int initialCapacity) {

		if (isOnline()) {
			this.initialCapacity = initialCapacity;
		}

		return createMap();
	}

	@Override
	public Map<K, V> createMap(Map<K, V> map) {
		switch (collectionType) {

		case ARRAY:
			return isOnline() ? new MapSizeMonitor<K, V>(new UnifiedMap<K, V>(map), this) : new UnifiedMap<K, V>(map);

		case HASH:
			return isOnline() ? new HashMapSizeMonitor<K, V>(this) : new HashMap<K, V>(map);

		case LINKED:
			return isOnline() ? new MapSizeMonitor<K, V>(new LinkedHashMap<K, V>(map), this) : new LinkedHashMap<K, V>(map);

		}

		return null;
	}

	@Override
	public void updateSize(int size) {
		
		int copyCount = count++;
		
		this.sizes[copyCount] = size;
		
		if(copyCount >= SAMPLES) {
			updateContext();
		}

	}

	private synchronized void updateContext() {
		
		int summedSize = 0;
		for (int i = 0; i < SAMPLES; i++) {
			summedSize += this.sizes[i];
		}
		this.initialCapacity = summedSize / SAMPLES;
		
		// FIXME: This is too arbitrary
		if(this.initialCapacity < ARRAY_THRESHOLD) {
			collectionType = CollectionTypeEnum.ARRAY;
		}
		
	}

	@Override
	public boolean isOnline() {
		return count >= SAMPLES;
	}

}
