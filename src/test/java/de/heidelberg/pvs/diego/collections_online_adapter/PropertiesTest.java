package de.heidelberg.pvs.diego.collections_online_adapter;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;

public class PropertiesTest {
	
	public static void main(String[] args) {
		
		ListAllocationContext buildListContext = AllocationContextFactory.buildListContext(CollectionTypeEnum.ARRAY, "MyTest");
		
		buildListContext.createList();
		
	}

}
