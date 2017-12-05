package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacityMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import jlibs.core.lang.RuntimeUtil;

public class InitialCapacityMapAllocationContextTest {
	
	@Test
	public void testCreatingMapContext() throws Exception {
		
		MapAllocationOptimizer optimizer = new MapActiveOptimizer(10, 0.75);
		
		MapAllocationContext context = new InitialCapacityMapAllocationContext(optimizer , 10);
		
		Map<Integer, Integer> createMap = context.createMap();
		assertNotNull(createMap);
		
	}
	
	@Test
	public void testAnalyzingInitialCapacity() throws Exception {
		
		int windowsSize = 10;
		MapAllocationOptimizer optimizer = new MapActiveOptimizer(windowsSize, 1);
		
		MapAllocationContextInfo context = new InitialCapacityMapAllocationContext(optimizer , windowsSize);
		optimizer.setContext(context);
		
		for (int i = 0; i < windowsSize; i++) {
			
			int analyzed = context.getAnalyzedInitialCapacity();
			assertEquals(16, analyzed);
			
			Map<Integer, Integer> createMap2 = context.createMap();
			
			for(int j = 0; j < 100; j++) {
				createMap2.put(j, j);
			}
			
			createMap2 = null;
		}
		
		RuntimeUtil.gc();
		optimizer.analyzeAndOptimize();
		
		int analyzed = context.getAnalyzedInitialCapacity();
		assertEquals((int)(100 / 0.75 + 1), analyzed);
		
	}

}
