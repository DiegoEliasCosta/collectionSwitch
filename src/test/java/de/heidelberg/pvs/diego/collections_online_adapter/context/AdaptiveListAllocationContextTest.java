package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.FULL_ANALYSIS_THRESHOLD;
import static de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.SLEEPING_FREQUENCY;
import static de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.WINDOW_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.HashArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.RuleBasedListOptimizer;
import jlibs.core.lang.RuntimeUtil;

public class AdaptiveListAllocationContextTest {

	private ListAllocationContext arrayContext;
	private ListAllocationContext linkedContext;
	private List<Integer> fullAnalysisList;

	@Before
	public void setup() {
		this.arrayContext = AllocationContextFactory.buildListContext(CollectionTypeEnum.ARRAY);
		this.linkedContext = AllocationContextFactory.buildListContext(CollectionTypeEnum.LINKED);

		this.fullAnalysisList = new ArrayList<Integer>();

		for (int i = 0; i < FULL_ANALYSIS_THRESHOLD; i++) {
			this.fullAnalysisList.add(i);
		}
		this.fullAnalysisList.add(0);

	}

	@Test
	public void testBootstrap() throws Exception {

		assertNotNull(arrayContext);
		assertNotNull(linkedContext);

		List<Integer> createList = arrayContext.createList();
		assertTrue(createList instanceof ArrayList);

		List<Integer> linkedList = linkedContext.createList();
		assertTrue(linkedList instanceof LinkedList);

	}

	@Test
	public void testChangeToFullAnalysisState() throws Exception {

		for (int i = 0; i < WINDOW_SIZE; i++) {

			List<Integer> list = arrayContext.createList();
			list.addAll(fullAnalysisList);
			list.add(101);
			list = null;
		}

		RuntimeUtil.gc();
		Thread.sleep(10);

		// FULL_ANALYSIS
		List<Integer> monitorList = arrayContext.createList();
		assertTrue(monitorList instanceof ArrayListFullMonitor);

	}

	@Test
	public void testFullToMemoryAnalysisState() throws Exception {

		// Create 10 lists
		for (int i = 0; i < WINDOW_SIZE; i++) {

			List<Integer> list = arrayContext.createList();
			list.addAll(fullAnalysisList);
			list.add(101);
			list = null;
		}

		RuntimeUtil.gc();
		Thread.sleep(10);

		// FULL_ANALYSIS
		List<Integer> fullMonitorList = arrayContext.createList();
		assertEquals(ArrayListFullMonitor.class, fullMonitorList.getClass());

		// Create 10 lists
		for (int i = 0; i < WINDOW_SIZE; i++) {

			List<Integer> list = arrayContext.createList();
			list.add(1);
			list = null;
		}

		RuntimeUtil.gc();
		Thread.sleep(10);

		// MEMORY_ANALYSIS
		List<Integer> sizeMonitorList = arrayContext.createList();
		assertEquals(ArrayListSizeMonitor.class, sizeMonitorList.getClass());

	}

	@Test
	public void testMemorySleepingState() throws Exception {

		// Create 10 lists
		for (int i = 0; i < WINDOW_SIZE; i++) {

			List<Integer> list = arrayContext.createList();
			list.add(1);
			list = null;
		}

		RuntimeUtil.gc();
		Thread.sleep(10);

		// SLEEPING_MEMORY_ANALYSIS
		List<Integer> fullMonitorList = arrayContext.createList();
		assertEquals(ArrayListSizeMonitor.class, fullMonitorList.getClass());

		// Create 10 lists
		for (int i = 0; i < WINDOW_SIZE * SLEEPING_FREQUENCY; i++) {

			List<Integer> list = arrayContext.createList();
			list.addAll(fullAnalysisList);
			list = null;
		}

		RuntimeUtil.gc();
		Thread.sleep(10);

		// FULL_ANALYSIS
		List<Integer> sizeMonitorList = arrayContext.createList();
		assertEquals(ArrayListFullMonitor.class, sizeMonitorList.getClass());

	}

	@Test
	public void testLinkedListChampion() throws Exception {

		// FULL ANALYSIS
		((AdaptiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);	
		
		// Prepare to LINKEDLIST champion 
		for (int i = 0; i < WINDOW_SIZE; i++) {

			List<Integer> list = arrayContext.createList();
			list.addAll(this.fullAnalysisList);

			for (int j = 0; j < RuleBasedListOptimizer.MIDLIST_LINKED_THRESHOLD; j++) {
				list.remove(50);
			}

			list = null;
		}
		
		RuntimeUtil.gc();

		Thread.sleep(10);

		// FULL_ANALYSIS
		List<Integer> sizeMonitorList = arrayContext.createList();
		assertEquals(LinkedListFullMonitor.class, sizeMonitorList.getClass());

	}
	
	@Test
	public void testHashListChampion() throws Exception {

		// FULL ANALYSIS
		((AdaptiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);	
		
		// Prepare to LINKEDLIST champion 
		for (int i = 0; i < WINDOW_SIZE; i++) {

			List<Integer> list = arrayContext.createList();
			list.addAll(this.fullAnalysisList);

			for (int j = 0; j < RuleBasedListOptimizer.CONTAINS_HASH_THRESHOLD + 1; j++) {
				list.contains(50);
			}

			list = null;
		}
		
		RuntimeUtil.gc();

		Thread.sleep(10);

		// FULL_ANALYSIS
		List<Integer> sizeMonitorList = arrayContext.createList();
		assertEquals(HashArrayListFullMonitor.class, sizeMonitorList.getClass());

	}

}
