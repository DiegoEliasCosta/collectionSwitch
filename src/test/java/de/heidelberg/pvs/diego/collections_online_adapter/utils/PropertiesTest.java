package de.heidelberg.pvs.diego.collections_online_adapter.utils;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;

public class PropertiesTest {
	
	public static void main(String[] args) {
		
		ListAllocationContext buildListContext = AllocationContextFactory.buildListContext(ListCollectionType.JDK_ARRAYLIST, "MyTest");
		
		buildListContext.createList();
		
	}

}
