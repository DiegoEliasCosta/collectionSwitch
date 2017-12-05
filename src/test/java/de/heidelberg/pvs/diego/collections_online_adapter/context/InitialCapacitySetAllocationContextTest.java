package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacitySetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;
import jlibs.core.lang.RuntimeUtil;

public class InitialCapacitySetAllocationContextTest {
	
	@Test
	public void testCreatingSetContext() throws Exception {
		
		SetAllocationOptimizer optimizer = new SetActiveOptimizer(10, 0.75);
		
		SetAllocationContext context = new InitialCapacitySetAllocationContext(optimizer , 10);
		
		Set<Object> createSet = context.createSet();
		assertNotNull(createSet);
		
	}
	
	@Test
	public void testAnalyzingInitialCapacity() throws Exception {
		
		int windowsSize = 10;
		SetAllocationOptimizer optimizer = new SetActiveOptimizer(windowsSize, 1);
		
		SetAllocationContextInfo context = new InitialCapacitySetAllocationContext(optimizer , windowsSize);
		optimizer.setContext(context);
		
		for (int i = 0; i < windowsSize; i++) {
			
			int analyzed = context.getAnalyzedInitialCapacity();
			assertEquals(16, analyzed);
			
			Set<Object> createSet2 = context.createSet();
			
			for(int j = 0; j < 100; j++) {
				createSet2.add(j);
			}
			
			createSet2 = null;
		}
		
		RuntimeUtil.gc();
		optimizer.analyzeAndOptimize();
		
		int analyzed = context.getAnalyzedInitialCapacity();
		assertEquals((int)(100 / 0.75 + 1), analyzed);
		
	}

}
