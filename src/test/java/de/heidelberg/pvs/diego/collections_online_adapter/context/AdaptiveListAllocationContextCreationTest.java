package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.HashArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ListSizeMonitor;

public class AdaptiveListAllocationContextCreationTest {
	
	private ListAllocationContext hashContext;
	private ListAllocationContext linkedContext;
	private ListAllocationContext arrayContext;
	
	List<Integer> fullList;
	
	@Before
	public void setup() {
		
		hashContext = 		AllocationContextFactory.buildListContext(CollectionTypeEnum.HASH);
		linkedContext = 	AllocationContextFactory.buildListContext(CollectionTypeEnum.LINKED);
		arrayContext = 	AllocationContextFactory.buildListContext(CollectionTypeEnum.ARRAY);
		
		fullList = new ArrayList<>();
		for(int i = 0; i < AllocationContextFactory.FULL_ANALYSIS_THRESHOLD; i++) {
			fullList.add(i);
		}
	}
	
	
	@Test
	public void testHashMapCreation() throws Exception {
		
		((AdaptiveListAllocationContext) hashContext).setAllocationContextState(AllocationContextState.INACTIVE);
		
		List<Integer> list = hashContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof HashArrayList);
		
		((AdaptiveListAllocationContext) hashContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		
		list = hashContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof HashArrayList);
		
	}
	
	@Test
	public void testLinkedHashMapCreation() throws Exception {
		
		((AdaptiveListAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.INACTIVE);
		List<Integer> list = linkedContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof LinkedList);
		
		
		((AdaptiveListAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		list = linkedContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof LinkedList);
		
	}
	
	@Test
	public void testArrayMapCreation() throws Exception {
		
		((AdaptiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.INACTIVE);
		List<Object> list = arrayContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof ArrayList);
		
		((AdaptiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		list = arrayContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof ArrayList);
		
	}
	
	
	@Test
	public void testSizeMonitorCreation() throws Exception {
		
		((AdaptiveListAllocationContext) hashContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		List<Object> list = hashContext.createList();
		assertTrue(list instanceof ListSizeMonitor);
		
		
		((AdaptiveListAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		List<Object> list2 = linkedContext.createList();
		assertTrue(list2 instanceof LinkedListSizeMonitor);
		
		((AdaptiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		list2 = arrayContext.createList();
		assertTrue(list2 instanceof ArrayListSizeMonitor);
		
	}
	
	@Test
	public void testFullMonitorCreation() throws Exception {
		
		((AdaptiveListAllocationContext) hashContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		List<Object> list = hashContext.createList();
		assertTrue(list instanceof HashArrayListFullMonitor);
		
		
		((AdaptiveListAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		List<Object> list2 = linkedContext.createList();
		assertTrue(list2 instanceof LinkedListFullMonitor);
		
		((AdaptiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		list2 = arrayContext.createList();
		assertTrue(list2 instanceof ArrayListFullMonitor);
		
	}

}
