package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.HashMap;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveMap;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapPassiveSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;

public class MapAllocationContextImpl implements MapAllocationContext {

	
	private static final int SAMPLE = 1;
	
	private int analyzedCollectionSize;
	private int analyzedCollectionInitialCapacity;
	
	private AllocationContextState state;
	private AllocationOptimizer optimizer;
	
	private int count;
	
	
	public MapAllocationContextImpl(AllocationOptimizer optimizer) {
		super();
		this.optimizer = optimizer;
		this.state = AllocationContextState.WARMUP;
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
			if (count++ % SAMPLE == 0) {
				return new MapPassiveSizeMonitor<K, V>(new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity), optimizer);
			}
			return new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % SAMPLE == 0) {
				return new MapPassiveSizeMonitor<K, V>(new HashMap<K, V>(), optimizer);
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
			if (count++ % SAMPLE == 0) {
				return new MapPassiveSizeMonitor<K, V>(new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity), optimizer);
			}
			return new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % SAMPLE == 0) {
				return new MapPassiveSizeMonitor<K, V>(new HashMap<K, V>(initialCapacity), optimizer);
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
			if (count++ % SAMPLE == 0) {
				return new MapPassiveSizeMonitor<K, V>(new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity), optimizer);
			}
			return new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % SAMPLE == 0) {
				return new MapPassiveSizeMonitor<K, V>(new HashMap<K, V>(initialCapacity, loadFactor), optimizer);
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
			if (count++ % SAMPLE == 0) {
				return new MapPassiveSizeMonitor<K, V>(new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity), optimizer);
			}
			return new AdaptiveMap<K, V>(analyzedCollectionInitialCapacity);

		case WARMUP:
			if (count++ % SAMPLE == 0) {
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

	@Override
	public AllocationContextState getAllocationContextState() {
		return state;
	}


}
