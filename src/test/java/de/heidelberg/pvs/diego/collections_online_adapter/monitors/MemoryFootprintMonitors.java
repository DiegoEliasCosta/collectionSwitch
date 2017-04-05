package de.heidelberg.pvs.diego.collections_online_adapter.monitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;

import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.HashMapFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.HashMapSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.stub.ListOptmizerStub;
import de.heidelberg.pvs.diego.collections_online_adapter.stub.MapOptimizerStub;

public class MemoryFootprintMonitors {
	
	private ListAllocationOptimizer optimizer = new ListOptmizerStub();
	private MapAllocationOptimizer mapOptimizer = new MapOptimizerStub();

	@Test
	public void testMemoryFootprintArrayListMonitor() throws Exception {
		
		List<Integer> monitorList = new ArrayListSizeMonitor(10, optimizer, 1);
		System.out.println(GraphLayout.parseInstance(monitorList).toFootprint());
		
		monitorList = new ListSizeMonitor(new ArrayList(10), optimizer, 1);
		System.out.println(GraphLayout.parseInstance(monitorList).toFootprint());
		
		List<Integer> monitorFullList = new ArrayListFullMonitor(10, optimizer, 1);
		System.out.println(GraphLayout.parseInstance(monitorFullList).toFootprint());
		
		List<Integer> list = new ArrayList(10);
		System.out.println(GraphLayout.parseInstance(list).toFootprint());
		
	}
	
	@Test
	public void testMemoryFootprintHashSetMonitor() throws Exception {
		
		Map<Integer, Integer> monitorList = new HashMapSizeMonitor(10, mapOptimizer, 1);
		System.out.println(GraphLayout.parseInstance(monitorList).toFootprint());
		
		monitorList = new MapSizeMonitor(new HashMap(10), mapOptimizer, 1);
		System.out.println(GraphLayout.parseInstance(monitorList).toFootprint());
		
		Map<Integer, Integer> monitorFullList = new HashMapFullMonitor(10, mapOptimizer, 1);
		System.out.println(GraphLayout.parseInstance(monitorFullList).toFootprint());
		
		Map<Integer, Integer> list = new HashMap(10);
		System.out.println(GraphLayout.parseInstance(list).toFootprint());
		
	}

}
