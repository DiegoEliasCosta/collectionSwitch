package de.heidelberg.pvs.diego.collections_online_adapter.monitors;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetMetrics;

public class SetActiveFullMonitorTest {
	
	@Test
	public void testSetMonitor() throws Exception {
		
		SetMetrics state = new SetMetrics(null);
		Set<Integer> list = new HashSet<Integer>();
		Set<Integer> monitor = new SetActiveFullMonitor<Integer>(list, state);
		
		monitor.add(1);
		Assert.assertEquals(1, state.getLastSize());
		
		monitor.remove(new Integer(1));
		Assert.assertEquals(0, state.getLastSize());
		
		int n = 100;
		for(int i = 0; i < n; i++) {
			monitor.add(i);
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
			monitor.contains(new Integer(c));
		}
		
		Assert.assertEquals(c, state.getContainsOp());
		
		int it = 170;
		for(int i = 0; i < it; i++) {
			for(Integer k : monitor) {
				k += 10;
			}
		}
		
		Assert.assertEquals(it, state.getIterationOp());
		
		monitor.clear();
		Assert.assertEquals(0, state.getLastSize());
		Assert.assertEquals(n, state.getMaxSize());
		
	}

}
