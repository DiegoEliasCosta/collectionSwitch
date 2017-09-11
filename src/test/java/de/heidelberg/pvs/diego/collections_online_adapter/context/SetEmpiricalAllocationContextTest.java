package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.EmpiricalSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.SwitchManager;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetEmpiricalOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetEmpiricalPerformanceEvaluator;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetPerformanceModel;
import jlibs.core.lang.RuntimeUtil;

public class SetEmpiricalAllocationContextTest {

	@Before
	public void setup() {

		PerformanceGoal.INSTANCE.init(PerformanceDimension.TIME, PerformanceDimension.ALLOCATION, 0.9, 2);

		List<SetPerformanceModel> performanceModel = new ArrayList<>();

		// Faster on Contains
		SetPerformanceModel arraySetModel = new SetPerformanceModel(SetCollectionType.NLP_ARRAYSET,
				new double[] { 10, 1 }, new double[] { 10, 2 }, new double[] { 10, 2 });

		performanceModel.add(arraySetModel);

		// Default
		SetPerformanceModel hashSetModel = new SetPerformanceModel(SetCollectionType.JDK_HASHSET,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 2 });

		performanceModel.add(hashSetModel);

		// Faster on iterate
		SetPerformanceModel gscollectionsModel = new SetPerformanceModel(SetCollectionType.GSCOLLECTIONS_UNIFIEDSET,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 1 });
		
		performanceModel.add(gscollectionsModel);
		
		SetEmpiricalPerformanceEvaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);
		
	}

	@Test
	public void testEmpiricalContextInitialization() throws Exception {

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(SetCollectionType.JDK_HASHSET, 10, 1);
		SetAllocationContext context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer, 10);

		context.createSet();

	}

	@Test
	public void testEmpiricalContextNLPChampion() throws Exception {

		int windowSize = 10;

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(SetCollectionType.JDK_HASHSET, windowSize, 0.0);
		SetAllocationContextInfo context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer,
				windowSize);

		optimizer.setContext(context);

		for (int i = 0; i < windowSize; i++) {
			Set<Object> createSet = context.createSet();

			for (int j = 0; j < 100; j++) {
				createSet.add(j);
			}

			// Contains is faster on NLP
			for (int j = 0; j < 10; j++) {
				createSet.contains(j);
			}
		}

		optimizer.analyzeAndOptimize();
		Assert.assertEquals(SetCollectionType.NLP_ARRAYSET.toString(), context.getCurrentCollectionType());

	}
	
	@Test
	public void testEmpiricalContextGSCollectionsChampion() throws Exception {

		int windowSize = 10;

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(SetCollectionType.JDK_HASHSET, windowSize, 0.0);
		SetAllocationContextInfo context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer,
				windowSize);

		optimizer.setContext(context);

		for (int i = 0; i < windowSize; i++) {
			Set<Integer> createSet = context.createSet();

			for (int j = 0; j < 100; j++) {
				createSet.add(j);
			}

			// Iterate is faster on GSCollections
			for(Integer e : createSet) {
				e += 10;
			}
		}

		optimizer.analyzeAndOptimize();
		Assert.assertEquals(SetCollectionType.GSCOLLECTIONS_UNIFIEDSET.toString(), context.getCurrentCollectionType());

	}

	@Test
	public void testWithSwitchManager() throws Exception {
		
		int windowSize = 10;

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(SetCollectionType.JDK_HASHSET, windowSize, 1);
		SetAllocationContextInfo context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer,
				windowSize);
		
		optimizer.setContext(context);
		
		SwitchManager manager = new SwitchManager();
		manager.addOptimizer(optimizer);
		manager.configureAndScheduleManager(1, 100, 100);
		
		for (int i = 0; i < windowSize; i++) {
			Set<Integer> createSet = context.createSet();

			for (int j = 0; j < 100; j++) {
				createSet.add(j);
			}

			// Iterate is faster on GSCollections
			for(Integer e : createSet) {
				e += 10;
			}
		}
		
		RuntimeUtil.gc();
		Thread.sleep(200);
		
		Assert.assertEquals(SetCollectionType.GSCOLLECTIONS_UNIFIEDSET.toString(), context.getCurrentCollectionType());
		
		
	}
	
	@Test
	public void testBestOptionWithSwitchManager() throws Exception {
		
		int windowSize = 10;

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(SetCollectionType.JDK_HASHSET, windowSize, 1);
		SetAllocationContextInfo context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer,
				windowSize);

		optimizer.setContext(context);
		
		SwitchManager manager = new SwitchManager();
		manager.addOptimizer(optimizer);
		manager.configureAndScheduleManager(1, 100, 50);
		
		for (int i = 0; i < windowSize; i++) {
			Set<Integer> createSet = context.createSet();

			for (int j = 0; j < 100; j++) {
				createSet.add(j);
			}

			// Iterate is faster on GSCollections
			for (int j = 0; j < 100; j++) {
				for(Integer e : createSet) {
					e += 10;
				}
			}
			
			// Contains is faster on NLP 
			for (int j = 0; j < 100 + 1; j++) {
				createSet.contains(j);
			}
			
			createSet = null;
		}
		
		RuntimeUtil.gc();
		Thread.sleep(500);
		RuntimeUtil.gc();
		Thread.sleep(500);
		
		Assert.assertEquals(SetCollectionType.NLP_ARRAYSET.toString(), context.getCurrentCollectionType());
		
		
	}
	
}
