package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.SetAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.PassiveOptimizer;

public class SetAllocationContextImplTest {
	
	@Test
	public void testUpdateSize() throws Exception {
		
		
		SetAllocationContext context = new SetAllocationContextImpl();
		Assert.assertNotNull(context);
		
		Set set = context.createSet();
		Assert.assertNotNull(set);
		
		
	}
	

}
