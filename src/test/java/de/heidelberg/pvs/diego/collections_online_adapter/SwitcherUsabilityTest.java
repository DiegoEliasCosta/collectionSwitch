package de.heidelberg.pvs.diego.collections_online_adapter;

import java.util.List;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;

public class SwitcherUsabilityTest {

	List<Integer> list;
	
	
	@Test
	public void testWithContext() throws Exception {
		ListAllocationContext<Integer> context = new AdaptiveListAllocationContext<>(CollectionTypeEnum.ARRAY);
		list = context.createList();
		
	}

	@Test
	public void testWithFactory() throws Exception {
		ListAllocationContext<Integer> context = AllocationContextFactory.buildListContext(CollectionTypeEnum.ARRAY);
		list = context.createList();
		
	}
	
	
}
