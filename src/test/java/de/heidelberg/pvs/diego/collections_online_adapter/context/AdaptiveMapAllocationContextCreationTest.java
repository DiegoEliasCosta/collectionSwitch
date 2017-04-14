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

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveMapAllocationContext;
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
		
		((AdaptiveMapAllocationContext) hashMapContext).setAllocationContextState(AllocationContextState.INACTIVE);
		
		Map<Object, Object> createMap = hashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof HashMap);
		
	}
	
	@Test
	public void testLinkedHashMapCreation() throws Exception {
		
		((AdaptiveMapAllocationContext) linkedHashMapContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Map<Object, Object> createMap = linkedHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof LinkedHashMap);
		
		
		((AdaptiveMapAllocationContext) linkedHashMapContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		createMap = linkedHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof LinkedHashMap);
		
	}
	
	@Test
	public void testArrayMapCreation() throws Exception {
		
		((AdaptiveMapAllocationContext) arrayHashMapContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Map<Object, Object> createMap = arrayHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof ArrayMap);
		
		((AdaptiveMapAllocationContext) arrayHashMapContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		createMap = arrayHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof ArrayMap);
		
	}
	
	@Test
	public void testUnifiedMapCreation() throws Exception {
		
		((AdaptiveMapAllocationContext) unifiedHashMapContext).setAllocationContextState(AllocationContextState.INACTIVE);
		Map<Object, Object> createMap = unifiedHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof UnifiedMap);
		
		((AdaptiveMapAllocationContext) unifiedHashMapContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		createMap = unifiedHashMapContext.createMap();
		assertNotNull(createMap);
		assertTrue(createMap instanceof UnifiedMap);
		
	}
	
	@Test
	public void testSizeMonitorCreation() throws Exception {
		
		((AdaptiveMapAllocationContext) hashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		Map<Object, Object> createMap = hashMapContext.createMap();
		assertTrue(createMap instanceof HashMapSizeMonitor);
		
		
		((AdaptiveMapAllocationContext) linkedHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		Map<Object, Object> createMap2 = linkedHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapSizeMonitor);
		
		((AdaptiveMapAllocationContext) arrayHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		createMap2 = arrayHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapSizeMonitor);
		
		((AdaptiveMapAllocationContext) unifiedHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		createMap2 = unifiedHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapSizeMonitor);
		
	}
	
	@Test
	public void testFullMonitorCreation() throws Exception {
		
		((AdaptiveMapAllocationContext) hashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		Map<Object, Object> createMap = hashMapContext.createMap();
		assertTrue(createMap instanceof HashMapFullMonitor);
		
		
		((AdaptiveMapAllocationContext) linkedHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		Map<Object, Object> createMap2 = linkedHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapFullMonitor);
		
		((AdaptiveMapAllocationContext) arrayHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		createMap2 = arrayHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapFullMonitor);
		
		((AdaptiveMapAllocationContext) unifiedHashMapContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		createMap2 = unifiedHashMapContext.createMap();
		assertTrue(createMap2 instanceof MapFullMonitor);
		
	}

}
