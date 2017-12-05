package de.heidelberg.pvs.diego.collectionswitch.utils;

import java.util.List;

import org.junit.Test;

import de.heidelberg.pvs.diego.collectionswitch.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collectionswitch.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.ListCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.factories.AllocationContextFactory;

public class SwitcherUsabilityTest {

	List<Integer> list;

	@Test
	public void testWithFactory() throws Exception {
		ListAllocationContext context = AllocationContextFactory.buildListContext(ListCollectionType.JDK_ARRAYLIST, "");
		list = context.createList();
		
	}
	
	@Test
	public void testAnnotation() {
		
		
		
		
	}
	
	
}
