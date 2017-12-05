package de.heidelberg.pvs.diego.collectionswitch.utils;

import de.heidelberg.pvs.diego.collectionswitch.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.ListCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.factories.AllocationContextFactory;

public class PropertiesTest {
	
	public static void main(String[] args) {
		
		ListAllocationContext buildListContext = AllocationContextFactory.buildListContext(ListCollectionType.JDK_ARRAYLIST, "MyTest");
		
		buildListContext.createList();
		
	}

}
