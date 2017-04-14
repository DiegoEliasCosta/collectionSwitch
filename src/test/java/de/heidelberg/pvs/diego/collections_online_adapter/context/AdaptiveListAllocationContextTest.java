package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.FULL_ANALYSIS_THRESHOLD;
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
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.HashArrayListFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.LinkedListFullMonitor;
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
	public void testLinkedListChampion() throws Exception {

		// FULL ANALYSIS
		((AdaptiveListAllocationContext) arrayContext).setAllocationContextState(AllocationContextState.ACTIVE_FULL);	
		
		// Prepare to LINKEDLIST champion 
		for (int i = 0; i < WINDOW_SIZE; i++) {

			List<Integer> list = arrayContext.createList();
			list.addAll(this.fullAnalysisList);

			for (int j = 0; j < RuleBasedListOptimizer.MIDLIST_LINKED_THRESHOLD + 3; j++) {
				list.add(50, 100);
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
