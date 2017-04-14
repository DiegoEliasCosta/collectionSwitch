package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.List;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.AllocationContextBuilder;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.AllocationContextBuilder.AllocationContextAlgorithm;

public class CreateContextTest {

	@Test
	public void test() {
	
		AllocationContextBuilder builder = new AllocationContextBuilder(CollectionTypeEnum.ARRAY, "");
		builder.withAlgorithm(AllocationContextAlgorithm.ADAPTIVE);
		
		ListAllocationContext list_context = AllocationContextFactory.buildListContext(builder);
		
		for(int i = 0; i < 10000; i++) {
			List<Object> createList = list_context.createList();
			
			for(int j = 0; j < 1000; j++) {
				createList.add(j);
			}
		}
		

	}
	
}
