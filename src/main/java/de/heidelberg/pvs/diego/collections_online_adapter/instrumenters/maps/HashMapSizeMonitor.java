package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.maps;

import java.util.HashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;

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
	
	private MapAllocationContext<K, V> context;
	
	public HashMapSizeMonitor(MapAllocationContext<K, V> context) {
		super();
		this.context = context;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		context.updateSize(size());
	}
	
}
