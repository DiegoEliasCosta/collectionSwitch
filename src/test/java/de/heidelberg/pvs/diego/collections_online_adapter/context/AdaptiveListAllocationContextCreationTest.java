package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ReactiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.HashArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.LinkedListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.LinkedListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListSizeMonitor;

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
		
		fullList = new ArrayList();
		for(int i = 0; i < AllocationContextFactory.FULL_ANALYSIS_THRESHOLD; i++) {
			fullList.add(i);
		}
	}
	
	
	@Test
	public void testHashMapCreation() throws Exception {
		
		((ReactiveListAllocationContext) hashContext).setAllocationContextState(AllocationContextState.INACTIVE);
		
		List<Integer> list = hashContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof HashArrayList);
		
		((ReactiveListAllocationContext) hashContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		
		list = hashContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof HashArrayList);
		
	}
	
	@Test
	public void testLinkedHashMapCreation() throws Exception {
		
		((ReactiveListAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.INACTIVE);
		List<Integer> list = linkedContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof LinkedList);
		
		
		((ReactiveListAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		list = linkedContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof LinkedList);
		
	}
	
	@Test
	public void testArrayMapCreation() throws Exception {
		
		((ReactiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.INACTIVE);
		List<Object> list = arrayContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof ArrayList);
		
		((ReactiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.OPTIMIZED);
		list = arrayContext.createList();
		assertNotNull(list);
		assertTrue(list instanceof ArrayList);
		
	}
	
	
	@Test
	public void testSizeMonitorCreation() throws Exception {
		
		((ReactiveListAllocationContext) hashContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		List<Object> list = hashContext.createList();
		assertTrue(list instanceof ListSizeMonitor);
		
		
		((ReactiveListAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		List<Object> list2 = linkedContext.createList();
		assertTrue(list2 instanceof LinkedListSizeMonitor);
		
		((ReactiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);
		list2 = arrayContext.createList();
		assertTrue(list2 instanceof ArrayListSizeMonitor);
		
	}
	
	@Test
	public void testFullMonitorCreation() throws Exception {
		
		((ReactiveListAllocationContext) hashContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		List<Object> list = hashContext.createList();
		assertTrue(list instanceof HashArrayListFullMonitor);
		
		
		((ReactiveListAllocationContext) linkedContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		List<Object> list2 = linkedContext.createList();
		assertTrue(list2 instanceof LinkedListFullMonitor);
		
		((ReactiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);
		list2 = arrayContext.createList();
		assertTrue(list2 instanceof ArrayListFullMonitor);
		
	}

}
