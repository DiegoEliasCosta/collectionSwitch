package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.*;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.EmpiricalMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapEmpiricalOptimizer;

public class MapEmpiricalAllocationContextTest {
	
	@Test
	public void testContextInitialization() throws Exception {
		
		int windowSize = 10;
		MapAllocationOptimizer optimizer = null;
		MapAllocationContext context = new EmpiricalMapAllocationContext(MapCollectionType.JDK_HASHMAP, optimizer , windowSize);
		
	}

}
