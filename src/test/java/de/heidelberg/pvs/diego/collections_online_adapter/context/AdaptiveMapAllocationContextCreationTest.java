package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.junit.Before;
import org.junit.Test;

import com.google.api.client.util.ArrayMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ReactiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.HashMapFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.HashMapSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapSizeMonitor;

public class AdaptiveMapAllocationContextCreationTest {
	
	private MapAllocationContext hashMapContext;
	private MapAllocationContext linkedHashMapContext;
	private MapAllocationContext arrayHashMapContext;
	private MapAllocationContext unifiedHashMapContext;
	
	Map<Integer, Integer> fullMap;
	
	@Before
	public void setup() {
		
		hashMapContext = AllocationContextFactory.buildMapContext(CollectionTypeEnum.HASH);
		linkedHashMapContext = AllocationContextFactory.buildMapContext(CollectionTypeEnum.LINKED);
		arrayHashMapContext = AllocationContextFactory.buildMapContext(CollectionTypeEnum.ARRAY);
		unifiedHashMapContext = AllocationContextFactory.buildMapContext(CollectionTypeEnum.ARRAY_HASH);
		
		fullMap = new HashedMap();
		for(int i = 0; i < AllocationContextFactory.FULL_ANALYSIS_THRESHOLD; i++) {
			fullMap.put(i, i);
		}
	}
	
	
	@Test
	public void testHashMapCreation() throws Exception {
		
		((ReactiveMapAllocationContext) hashMapContext).setAllocationContextState(AllocationContextState.INACTIVE);
		
		Map<Object, Object> createMap = hashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof HashMap);
		
	}
	
	@Test
	public void testLinkedHashMapCreation() throws Exception {
		
		((ReactiveMapAllocationContext) linkedHashMapContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Map<Object, Object> createMap = linkedHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof LinkedHashMap);
		
		
		((ReactiveMapAllocationContext) linkedHashMapContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		createMap = linkedHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof LinkedHashMap);
		
	}
	
	@Test
	public void testArrayMapCreation() throws Exception {
		
		((ReactiveMapAllocationContext) arrayHashMapContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Map<Object, Object> createMap = arrayHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof ArrayMap);
		
		((ReactiveMapAllocationContext) arrayHashMapContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		createMap = arrayHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof ArrayMap);
		
	}
	
	@Test
	public void testUnifiedMapCreation() throws Exception {
		
		((ReactiveMapAllocationContext) unifiedHashMapContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Map<Object, Object> createMap = unifiedHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof UnifiedMap);
		
		((ReactiveMapAllocationContext) unifiedHashMapContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		createMap = unifiedHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof UnifiedMap);
		
	}
	
	@Test
	public void testSizeMonitorCreation() throws Exception {
		
		((ReactiveMapAllocationContext) hashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		Map<Object, Object> createMap = hashMapContext.createMap();
		assertTrue(createMap instanceof HashMapSizeMonitor);
		
		
		((ReactiveMapAllocationContext) linkedHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		Map<Object, Object> createMap2 = linkedHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapSizeMonitor);
		
		((ReactiveMapAllocationContext) arrayHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		createMap2 = arrayHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapSizeMonitor);
		
		((ReactiveMapAllocationContext) unifiedHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		createMap2 = unifiedHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapSizeMonitor);
		
	}
	
	@Test
	public void testFullMonitorCreation() throws Exception {
		
		((ReactiveMapAllocationContext) hashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		Map<Object, Object> createMap = hashMapContext.createMap();
		assertTrue(createMap instanceof HashMapFullMonitor);
		
		
		((ReactiveMapAllocationContext) linkedHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		Map<Object, Object> createMap2 = linkedHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapFullMonitor);
		
		((ReactiveMapAllocationContext) arrayHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		createMap2 = arrayHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapFullMonitor);
		
		((ReactiveMapAllocationContext) unifiedHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		createMap2 = unifiedHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapFullMonitor);
		
	}

}
