package de.heidelberg.pvs.diego.collections_online_adapter;

import java.util.List;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ListAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.impl.FirstSamplesListMemporyOptimizer;

public class SwitcherUsabilityTest {

	List<Integer> list;
	
	
	@Test
	public void testWithContext() throws Exception {
		ListAllocationContext<Integer> context = new ListAllocationContextImpl(CollectionTypeEnum.ARRAY);
		list = context.createList();
		
	}
	
}
