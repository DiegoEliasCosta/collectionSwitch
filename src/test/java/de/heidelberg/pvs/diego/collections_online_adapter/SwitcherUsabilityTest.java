package de.heidelberg.pvs.diego.collections_online_adapter;

import java.util.List;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;

public class SwitcherUsabilityTest {

	List<Integer> list;

	@Test
	public void testWithFactory() throws Exception {
		ListAllocationContext context = AllocationContextFactory.buildListContext(CollectionTypeEnum.ARRAY);
		list = context.createList();
		
	}
	
	
}
