package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.EmpiricalListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.SwitchManager;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListEmpiricalOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListEmpiricalPerformanceEvaluator;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListPerformanceModel;
import jlibs.core.lang.RuntimeUtil;

public class ListEmpiricalAllocationContextTest {

	@Before
	public void setup() {

		PerformanceGoal.INSTANCE.init(PerformanceDimension.TIME, PerformanceDimension.ALLOCATION, 0.8, 2);

		List<ListPerformanceModel> performanceModel = new ArrayList<>();

		// Faster on Contains
		ListPerformanceModel arraySetModel = new ListPerformanceModel(ListCollectionType.SWITCH_ADAPTIVELIST,
				new double[] { 10, 2 }, new double[] { 10, 1 }, new double[] { 10, 2 }, new double[] { 10, 2 });

		performanceModel.add(arraySetModel);

		// Default
		ListPerformanceModel hashSetModel = new ListPerformanceModel(ListCollectionType.JDK_ARRAYLIST,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 2 });

		performanceModel.add(hashSetModel);

		// Faster on iterate
		ListPerformanceModel gscollectionsModel = new ListPerformanceModel(ListCollectionType.JDK_LINKEDLIST,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 1 }, new double[] { 10, 2 });

		performanceModel.add(gscollectionsModel);

		ListEmpiricalPerformanceEvaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);

	}

	@Test
	public void testEmpiricalContextInitialization() throws Exception {

		ListAllocationOptimizer optimizer = new ListEmpiricalOptimizer(ListCollectionType.JDK_ARRAYLIST, 10, 1);
		ListAllocationContext context = new EmpiricalListAllocationContext(ListCollectionType.JDK_ARRAYLIST, optimizer,
				10);

		context.createList();

	}

	@Test
	public void testEmpiricalContextAdaptiveChampion() throws Exception {

		ListAllocationOptimizer optimizer = new ListEmpiricalOptimizer(ListCollectionType.JDK_ARRAYLIST, 10, 0);
		ListAllocationContextInfo context = new EmpiricalListAllocationContext(ListCollectionType.JDK_ARRAYLIST,
				optimizer, 10);

		optimizer.setContext(context);

		bestScenarioAdaptiveList(context, 10);

		optimizer.analyzeAndOptimize();

		Assert.assertEquals(ListCollectionType.SWITCH_ADAPTIVELIST, context.getCurrentCollectionType());

	}

	@Test
	public void testEmpiricalContextLinkedListChampion() throws Exception {

		ListAllocationOptimizer optimizer = new ListEmpiricalOptimizer(ListCollectionType.JDK_ARRAYLIST, 10, 0);
		ListAllocationContextInfo context = new EmpiricalListAllocationContext(ListCollectionType.JDK_ARRAYLIST,
				optimizer, 10);

		optimizer.setContext(context);

		bestScenarioLinkedList(context, 10);

		optimizer.analyzeAndOptimize();

		Assert.assertEquals(ListCollectionType.JDK_LINKEDLIST, context.getCurrentCollectionType());

	}

	@Test
	public void testWithSwitchManager() throws Exception {

		ListAllocationOptimizer optimizer = new ListEmpiricalOptimizer(ListCollectionType.JDK_ARRAYLIST, 10, 1);
		ListAllocationContextInfo context = new EmpiricalListAllocationContext(ListCollectionType.JDK_ARRAYLIST,
				optimizer, 10);

		optimizer.setContext(context);

		SwitchManager manager = new SwitchManager();

		manager.addOptimizer(optimizer);
		manager.configureAndScheduleManager(1, 100, 100);

		bestScenarioLinkedList(context, 10);

		RuntimeUtil.gc();
		Thread.sleep(200);

		Assert.assertEquals(ListCollectionType.JDK_LINKEDLIST, context.getCurrentCollectionType());

	}

	@Test
	public void testWithSwitchMultipleStages() throws Exception {

		ListAllocationOptimizer optimizer = new ListEmpiricalOptimizer(ListCollectionType.JDK_ARRAYLIST, 10, 1);
		ListAllocationContextInfo context = new EmpiricalListAllocationContext(ListCollectionType.JDK_ARRAYLIST,
				optimizer, 10);

		optimizer.setContext(context);

		SwitchManager manager = new SwitchManager();

		manager.addOptimizer(optimizer);
		manager.configureAndScheduleManager(1, 100, 100);

		bestScenarioLinkedList(context, 10);

		RuntimeUtil.gc();
		Thread.sleep(200);

		Assert.assertEquals(ListCollectionType.JDK_LINKEDLIST, context.getCurrentCollectionType());

		bestScenarioAdaptiveList(context, 10);

		RuntimeUtil.gc();
		Thread.sleep(200);

		Assert.assertEquals(ListCollectionType.SWITCH_ADAPTIVELIST, context.getCurrentCollectionType());
		
		bestScenarioArrayList(context, 10);

		RuntimeUtil.gc();
		Thread.sleep(200);

		Assert.assertEquals(ListCollectionType.JDK_ARRAYLIST, context.getCurrentCollectionType());

	}

	private void bestScenarioArrayList(ListAllocationContextInfo context, int windowSize) {

		for (int i = 0; i < windowSize; i++) {

			List<Object> createList = context.createList();

			for (int j = 0; j < 100; j++) {
				createList.add(j);
			}

			createList = null;

		}

	}

	private void bestScenarioAdaptiveList(ListAllocationContextInfo context, int windowSize) {

		for (int i = 0; i < windowSize; i++) {

			List<Object> createList = context.createList();

			for (int j = 0; j < 100; j++) {
				createList.add(j);
			}

			for (int j = 0; j < 100; j++) {
				createList.contains(j);
			}

			createList = null;
		}

	}

	private void bestScenarioLinkedList(ListAllocationContextInfo context, int windowSize) {
		for (int i = 0; i < windowSize; i++) {

			List<Integer> createList = context.createList();

			for (int j = 0; j < 100; j++) {
				createList.add(j);
			}

			for (int j = 0; j < 100; j++) {
				for (Integer o : createList) {
					o += 2;
				}
			}
			createList = null;

		}

	}

}
