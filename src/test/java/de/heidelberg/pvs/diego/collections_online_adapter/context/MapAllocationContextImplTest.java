package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.MapAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.PassiveOptimizer;

public class MapAllocationContextImplTest {
	
	@Test
	public void sanityTest() throws Exception {
		
		AllocationOptimizer optimizer = new PassiveOptimizer();
		assertNotNull(optimizer);
		
		MapAllocationContext context = new MapAllocationContextImpl(optimizer);
		assertNotNull(context);
		
		optimizer.setContext(context);
		
		Map<Object, Object> createMap = context.createMap();
		assertNotNull(createMap);
		
	}

}
