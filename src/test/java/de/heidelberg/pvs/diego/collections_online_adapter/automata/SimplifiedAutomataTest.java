package de.heidelberg.pvs.diego.collections_online_adapter.automata;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.FastListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import jlibs.core.lang.RuntimeUtil;

public class SimplifiedAutomataTest {
	
	
	ListAllocationContext listContext;
	ListAllocationOptimizer optimizer;
	
	@Before
	public void setup() {
		listContext = new FastListAllocationContext(CollectionTypeEnum.ARRAY, optimizer, 1);
	}
	
	@Test
	public void testWarmupToActive() throws Exception {
		
		for(int i = 0; i < 11; i++) {
			List<Object> createList = listContext.createList();
			createList.add(1); 
			createList.add(i); 
		}
		
		RuntimeUtil.gc();
		Thread.sleep(200);
		
		System.out.println(String.format("%l", System.nanoTime()));
		
		System.nanoTime();
		
		assertEquals(AllocationContextState.ACTIVE, listContext.getAllocationContextState());
		
	}

}
