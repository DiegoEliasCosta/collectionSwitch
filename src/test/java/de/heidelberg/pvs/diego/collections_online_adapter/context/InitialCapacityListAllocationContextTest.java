package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacityListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import jlibs.core.lang.RuntimeUtil;

public class InitialCapacityListAllocationContextTest {
	
	@Test
	public void testCreatingListContext() throws Exception {
		
		ListAllocationOptimizer optimizer = new ListActiveOptimizer(10, 0.75);
		
		ListAllocationContext context = new InitialCapacityListAllocationContext(optimizer , 10, 1);
		
		List<Object> createList = context.createList();
		assertNotNull(createList);
		
	}
	
	@Test
	public void testAnalyzingInitialCapacity() throws Exception {
		
		int windowsSize = 10;
		ListAllocationOptimizer optimizer = new ListActiveOptimizer(windowsSize, 1);
		
		ListAllocationContextInfo context = new InitialCapacityListAllocationContext(optimizer , windowsSize, 1);
		optimizer.setContext(context);
		
		for (int i = 0; i < windowsSize; i++) {
			
			int analyzed = context.getAnalyzedInitialCapacity();
			assertEquals(windowsSize, analyzed);
			
			List<Object> createList2 = context.createList();
			
			for(int j = 0; j < 100; j++) {
				createList2.add(j);
			}
			
			createList2 = null;
		}
		
		RuntimeUtil.gc();
		
		optimizer.analyzeAndOptimize();
		
		int analyzed = context.getAnalyzedInitialCapacity();
		assertEquals(100, analyzed);
		
	}

}
