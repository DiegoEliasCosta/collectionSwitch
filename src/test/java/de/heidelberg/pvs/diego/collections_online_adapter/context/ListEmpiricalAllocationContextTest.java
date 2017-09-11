package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.EmpiricalListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListEmpiricalOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListEmpiricalPerformanceEvaluator;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListPerformanceModel;
import junit.framework.Assert;

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
		ListAllocationContext context = new EmpiricalListAllocationContext(ListCollectionType.JDK_ARRAYLIST, optimizer, 10);

		context.createList();

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testEmpiricalContextAdaptiveChampion() throws Exception {

		ListAllocationOptimizer optimizer = new ListEmpiricalOptimizer(ListCollectionType.JDK_ARRAYLIST, 10, 0);
		ListAllocationContextInfo context = new EmpiricalListAllocationContext(ListCollectionType.JDK_ARRAYLIST, optimizer, 10);
		
		optimizer.setContext(context);

		for(int i = 0; i < 10; i++) {
			
			List<Object> createList = context.createList();
			
			for(int j = 0; j < 100; j++) {
				createList.contains(j);
			}
			
			for(int j = 0; j < 100; j++) {
				createList.contains(j);
			}
			
		}
		
		optimizer.analyzeAndOptimize();
		
		Assert.assertEquals(ListCollectionType.SWITCH_ADAPTIVELIST, context.getCurrentCollectionType());
		

	}

}
