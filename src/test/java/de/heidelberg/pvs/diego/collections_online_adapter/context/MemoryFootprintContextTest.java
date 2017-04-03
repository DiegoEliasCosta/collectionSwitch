package de.heidelberg.pvs.diego.collections_online_adapter.context;

import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;

import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;

public class MemoryFootprintContextTest {

	@Test
	public void testAdaptiveListAllocationContextFootprint() throws Exception {

		// Write to the file
		String footprint = String.format("AdaptiveListAllocationContext Footprint\n%s", 
				GraphLayout.parseInstance(AllocationContextFactory.buildListContext(CollectionTypeEnum.ARRAY)).toFootprint());

		System.out.println(String.format("%s", footprint));
		

	}
	
	@Test
	public void testAdaptiveSetAllocationContextFootprint() throws Exception {

		// Write to the file
		String footprint = String.format("AdaptiveSetAllocationContext Footprint\n%s", 
				GraphLayout.parseInstance(AllocationContextFactory.buildSetContext(CollectionTypeEnum.HASH)).toFootprint());

		System.out.println(String.format("%s", footprint));
		

	}
	
	@Test
	public void testAdaptiveMapAllocationContextFootprint() throws Exception {

		// Write to the file
		String footprint = String.format("AdaptiveMapAllocationContext Footprint\n%s", 
				GraphLayout.parseInstance(AllocationContextFactory.buildMapContext(CollectionTypeEnum.HASH)).toFootprint());

		System.out.println(String.format("%s", footprint));
		

	}

}
