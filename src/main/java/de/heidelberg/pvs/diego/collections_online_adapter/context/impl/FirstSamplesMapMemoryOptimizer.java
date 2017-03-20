package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import com.google.api.client.util.ArrayMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps.HashMapSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps.MapSizeMonitor;

/**
 * Simple optimizer that uses the first samples of the allocation-site to determine the best collection.
 * Used only for memory optimizations.
 * 
 * @author Diego
 *
 * @param <K>
 * @param <V>
 */
public class FirstSamplesMapMemoryOptimizer<K, V> implements MapAllocationContext<K, V> {

	// Thresholds
	private static final int ARRAY_THRESHOLD = 30;
	private static final int ARRAY_HASH_THRESHOLD = 1000;

	private static final int SAMPLES = 10;
	
	CollectionTypeEnum collectionType;

	private volatile int initialCapacity;
	private volatile int count;
	
	// Monitored data
	private int[] sizes = new int[SAMPLES];

	public FirstSamplesMapMemoryOptimizer(CollectionTypeEnum type) {
		super();
		this.collectionType = type;
	}
	
	public FirstSamplesMapMemoryOptimizer(CollectionTypeEnum type, int specifiedInitialCapacity) {
		super();
		this.collectionType = type;
		this.initialCapacity = specifiedInitialCapacity;
	}

	@Override
	public Map<K, V> createMap() {

		switch (collectionType) {
		
		case ARRAY:
			return isOnline() ? new MapSizeMonitor<>(ArrayMap.create(initialCapacity), this) : ArrayMap.create(initialCapacity);

		case ARRAY_HASH:
			return isOnline() ? new MapSizeMonitor<>(new UnifiedMap<K, V>(initialCapacity), this) : new UnifiedMap<K, V>(initialCapacity);

		case HASH:
			return isOnline() ? new HashMapSizeMonitor<>(initialCapacity, this) : new HashMap<K, V>(initialCapacity);

		case LINKED:
			return isOnline() ? new MapSizeMonitor<>(new LinkedHashMap<K, V>(initialCapacity), this) : new LinkedHashMap<K, V>(initialCapacity);

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
			// ArrayMap does not have a constructor for copy - making it too slow for this operation
			// We go for UnifiedMaps for those cases
		case ARRAY_HASH:
			return isOnline() ? new MapSizeMonitor<>(new UnifiedMap<K, V>(map), this) : new UnifiedMap<K, V>(map);

		case HASH:
			return isOnline() ? new HashMapSizeMonitor<K, V>(map, this) : new HashMap<K, V>(map);

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
		} else if(this.initialCapacity < ARRAY_HASH_THRESHOLD) {
			collectionType = CollectionTypeEnum.ARRAY_HASH;
		}
		
	}

	@Override
	public boolean isOnline() {
		return count >= SAMPLES;
	}

}
