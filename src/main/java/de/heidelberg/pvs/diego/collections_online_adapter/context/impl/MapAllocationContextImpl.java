package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.HashMap;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveMap;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapPassiveSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class MapAllocationContextImpl implements MapAllocationContextInfo {

	
	private int sampleRate = 1;
	
	private int analyzedCollectionSize;
	private int analyzedCollectionInitialCapacity;
	
	private AllocationContextState state;
	private MapAllocationOptimizer optimizer;
	
	private int count;
	
	
	public MapAllocationContextImpl(MapAllocationOptimizer optimizer) {
		super();
		this.optimizer = optimizer;
		this.state = AllocationContextState.WARMUP;
	}
	
	public MapAllocationContextImpl(MapAllocationOptimizer optimizer, int sampleRate) {
		super();
		this.optimizer = optimizer;
		this.state = AllocationContextState.WARMUP;
		this.sampleRate = sampleRate;
	}

	@Override
	public void updateCollectionSize(int size) {
		analyzedCollectionSize = size;
		analyzedCollectionInitialCapacity = (int) (size /.75f + 1);
		this.state = AllocationContextState.ADAPTIVE;
		
	}

	@Override
	public void noSizeConvergence() {
		// TODO To Implement this behavior
		
	}

	@Override
	public <K, V> Map<K, V> createMap() {
		
		switch (state) {

		case ADAPTIVE:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity));
			}
			return new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new HashMap<K, V>());
			}
			return new HashMap<K, V>();

		case INACTIVE:
			return new HashMap<K, V>();

		default:
			break;
		}

		return null;
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		
		switch (state) {

		case ADAPTIVE:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity));
			}
			return new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new HashMap<K, V>(initialCapacity));
			}
			return new HashMap<K, V>(initialCapacity);

		case INACTIVE:
			return new HashMap<K, V>(initialCapacity);

		default:
			break;
		}

		return null;
		
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {
		
		switch (state) {

		case ADAPTIVE:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity));
			}
			return new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % sampleRate == 0) {
				return optimizer.createMonitor(new HashMap<K, V>(initialCapacity, loadFactor));
			}
			return new HashMap<K, V>(initialCapacity, loadFactor);

		case INACTIVE:
			return new HashMap<K, V>(initialCapacity, loadFactor);

		default:
			break;
		}

		return null;
	}

	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> map) {
		
		switch (state) {

		case ADAPTIVE:
			if (count++ % sampleRate == 0) {
				return new MapPassiveSizeMonitor<K, V>(new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity), optimizer);
			}
			return new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % sampleRate == 0) {
				return new MapPassiveSizeMonitor<K, V>(new HashMap<K, V>(map), optimizer);
			}
			return new HashMap<K, V>(map);

		case INACTIVE:
			return new HashMap<K, V>(map);

		default:
			break;
		}

		return null;
	}

	public AllocationContextState getAllocationContextState() {
		return state;
	}
	
	public int getAnalyzedSize() {
		return analyzedCollectionSize;
	}

	@Override
	public int getInitialCapacity() {
		return analyzedCollectionInitialCapacity;
	}
	
	


}
