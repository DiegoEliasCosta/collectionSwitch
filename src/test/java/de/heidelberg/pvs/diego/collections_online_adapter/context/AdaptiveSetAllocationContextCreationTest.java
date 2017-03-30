package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.ArraySet;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.HashSetFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.HashSetSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.SetFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.sets.SetSizeMonitor;

public class AdaptiveSetAllocationContextCreationTest {
	
	private SetAllocationContext hashContext;
	private SetAllocationContext linkedContext;
	private SetAllocationContext arrayContext;
	private SetAllocationContext unifiedContext;
	
	Set<Integer> fullSet;
	
	@Before
	public void setup() {
		
		hashContext = 	AllocationContextFactory.buildSetContext(CollectionTypeEnum.HASH);
		unifiedContext = 	AllocationContextFactory.buildSetContext(CollectionTypeEnum.ARRAY_HASH);
		linkedContext = AllocationContextFactory.buildSetContext(CollectionTypeEnum.LINKED);
		arrayContext = 	AllocationContextFactory.buildSetContext(CollectionTypeEnum.ARRAY);
		
		fullSet = new HashSet<>();
		for(int i = 0; i < AllocationContextFactory.FULL_ANALYSIS_THRESHOLD; i++) {
			fullSet.add(i);
		}
	}
	
	
	@Test
	public void testHashSetCreation() throws Exception {
		
		((AdaptiveSetAllocationContext) hashContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Set<Integer> set = hashContext.createSet();
		assertNotNull(set);
		assertTrue(set instanceof HashSet);
		
		((AdaptiveSetAllocationContext) hashContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		
		set = hashContext.createSet();
		assertNotNull(set);
		assertTrue(set instanceof HashSet);
		
	}
	
	@Test
	public void testLinkedHashSetCreation() throws Exception {
		
		((AdaptiveSetAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Set<Integer> set = linkedContext.createSet();
		assertNotNull(set);
		assertTrue(set instanceof LinkedHashSet);
		
		
		((AdaptiveSetAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		set = linkedContext.createSet();
		assertNotNull(set);
		assertTrue(set instanceof LinkedHashSet);
		
	}
	
	@Test
	public void testArraySetCreation() throws Exception {
		
		((AdaptiveSetAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Set<Object> set = arrayContext.createSet();
		assertNotNull(set);
		assertTrue(set instanceof ArraySet);
		
		((AdaptiveSetAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		set = arrayContext.createSet();
		assertNotNull(set);
		assertTrue(set instanceof ArraySet);
		
	}
	
	@Test
	public void testUnifiedSetCreation() throws Exception {
		
		((AdaptiveSetAllocationContext) unifiedContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Set<Object> set = unifiedContext.createSet();
		assertNotNull(set);
		assertTrue(set instanceof UnifiedSet);
		
		((AdaptiveSetAllocationContext) unifiedContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		set = unifiedContext.createSet();
		assertNotNull(set);
		assertTrue(set instanceof UnifiedSet);
		
	}
	
	
	@Test
	public void testSizeMonitorCreation() throws Exception {
		
		((AdaptiveSetAllocationContext) hashContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		Set<Object> set = hashContext.createSet();
		assertTrue(set instanceof HashSetSizeMonitor);
		
		
		((AdaptiveSetAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		Set<Object> set2 = linkedContext.createSet();
		assertTrue(set2 instanceof SetSizeMonitor);
		
		((AdaptiveSetAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		set2 = arrayContext.createSet();
		assertTrue(set2 instanceof SetSizeMonitor);
		
	}
	
	@Test
	public void testFullMonitorCreation() throws Exception {
		
		((AdaptiveSetAllocationContext) hashContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		Set<Object> set = hashContext.createSet();
		assertTrue(set instanceof HashSetFullMonitor);
		
		
		((AdaptiveSetAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		Set<Object> set2 = linkedContext.createSet();
		assertTrue(set2 instanceof SetFullMonitor);
		
		((AdaptiveSetAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		set2 = arrayContext.createSet();
		assertTrue(set2 instanceof SetFullMonitor);
		
	}

}
