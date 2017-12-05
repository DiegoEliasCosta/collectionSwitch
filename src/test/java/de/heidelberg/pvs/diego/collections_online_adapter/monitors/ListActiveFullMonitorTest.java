package de.heidelberg.pvs.diego.collections_online_adapter.monitors;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.andrewoma.dexx.collection.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListMetrics;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetMetrics;

public class ListActiveFullMonitorTest {
	
	@Test
	public void testListMonitor() throws Exception {
		
		ListMetrics state = new ListMetrics(null);
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> monitor = new ListActiveFullMonitor<Integer>(list, state);
		
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
		
		Assert.assertEquals(1 + r, state.getContainsOp());
		
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
