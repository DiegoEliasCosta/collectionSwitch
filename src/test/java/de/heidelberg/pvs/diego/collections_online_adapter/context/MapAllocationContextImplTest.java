package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.MapAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.PassiveOptimizer;
import jlibs.core.lang.RuntimeUtil;

public class MapAllocationContextImplTest {
	
	private static final int SAMPLE = 1;
	private static final int WINDOW_SIZE = 10;
	private AllocationOptimizer optimizer;
	private MapAllocationContextImpl context;

	@Before
	public void setup() {
		optimizer = new PassiveOptimizer(WINDOW_SIZE);
		assertNotNull(optimizer);
		
		context = new MapAllocationContextImpl(optimizer, SAMPLE);
		assertNotNull(context);
		
		optimizer.setContext(context);
	}
	
	@Test
	public void sanityTest() throws Exception {
		
		AllocationOptimizer optimizer = new PassiveOptimizer(WINDOW_SIZE);
		assertNotNull(optimizer);
		
		MapAllocationContext context = new MapAllocationContextImpl(optimizer);
		assertNotNull(context);
		
		optimizer.setContext(context);
		
		Map<Object, Object> createMap = context.createMap();
		assertNotNull(createMap);
		
	}
	
	@Test
	public void testWarmupToAdaptive() throws Exception {
		
		int n = 7;
		for(int i = 0; i < WINDOW_SIZE * SAMPLE; i++) {
			
			Map<Integer, Integer> map = context.createMap();
			
			for(int j = 0; j < n; j++) {
				map.put(j, j);
			}
			
			map = null;
		}
		
		RuntimeUtil.gc();
		RuntimeUtil.gc();
		Thread.sleep(1000);
		
		AllocationContextState state = context.getAllocationContextState();
		Assert.assertEquals(AllocationContextState.ADAPTIVE, state);

		int analyzedSize = context.getAnalyzedSize();
		Assert.assertEquals(n, analyzedSize);
		
	}

}
