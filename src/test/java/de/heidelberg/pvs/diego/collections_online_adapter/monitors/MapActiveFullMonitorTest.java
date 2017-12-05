package de.heidelberg.pvs.diego.collections_online_adapter.monitors;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapMetrics;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetActiveFullMonitor;

public class MapActiveFullMonitorTest {
	
	@Test
	public void testMapMonitor() throws Exception {
		
		MapMetrics state = new MapMetrics(null);
		Map<Integer, Integer> list = new HashMap<Integer, Integer>();
		Map<Integer, Integer> monitor = new MapActiveFullMonitor<Integer, Integer>(list, state);
		
		monitor.put(1, 1);
		Assert.assertEquals(1, state.getLastSize());
		
		monitor.remove(new Integer(1));
		Assert.assertEquals(0, state.getLastSize());
		
		int n = 100;
		for(int i = 0; i < n; i++) {
			monitor.put(i, i);
		}
		Assert.assertEquals(n, state.getLastSize());
		Assert.assertEquals(n, state.getMaxSize());
		
		int r = 55;
		for(int i = 0; i < r; i++) {
			monitor.remove(new Integer(i));
		}
		Assert.assertEquals(n - r, state.getLastSize());
		Assert.assertEquals(n, state.getMaxSize());
		
		
		int c = 78;
		for(int i = 0; i < c; i++) {
			monitor.get(new Integer(c));
		}
		
		Assert.assertEquals(c, state.getContainsOp());
		
		int it = 170;
		for(int i = 0; i < it; i++) {
			for(Integer k : monitor.keySet()) {
				k += 10;
			}
		}
		
		Assert.assertEquals(it, state.getIterationOp());
		
		monitor.clear();
		Assert.assertEquals(0, state.getLastSize());
		Assert.assertEquals(n, state.getMaxSize());
		
	}

}
