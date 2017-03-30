package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps;

import java.util.HashMap;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

/**
 * Those specific inherited monitors were created to reduce the overhead of the composition monitors.
 * 
 * @author Diego
 *
 * @param <K>
 * @param <V>
 */
public class HashMapSizeMonitor<K, V> extends HashMap<K, V> {
	
	private static final long serialVersionUID = 20170101L;
	
	private MapAllocationOptimizer context;
	
	public HashMapSizeMonitor(int initialCapacity, MapAllocationOptimizer context) {
		super(initialCapacity);
		this.context = context;
	}
	
	public HashMapSizeMonitor(Map<? extends K, ? extends V> map, MapAllocationOptimizer context) {
		super(map);
		this.context = context;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(size());
	}
	
}
