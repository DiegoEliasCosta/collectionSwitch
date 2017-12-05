package de.heidelberg.pvs.diego.collectionswitch.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collectionswitch.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.SetAllocationContextInfo;
import de.heidelberg.pvs.diego.collectionswitch.context.SetCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.context.impl.EmpiricalSetAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collectionswitch.manager.SwitchManager;
import de.heidelberg.pvs.diego.collectionswitch.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.sets.SetAllocationOptimizer;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.sets.SetEmpiricalOptimizer;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.sets.SetEmpiricalPerformanceEvaluator;
import de.heidelberg.pvs.diego.collectionswitch.optimizers.sets.SetPerformanceModel;
import jlibs.core.lang.RuntimeUtil;

public class SetEmpiricalAllocationContextTest {

	private ArrayList<SetPerformanceModel> performanceModel;
	
	private PerformanceGoal goal;

	@Before
	public void setup() {
		
		goal = new PerformanceGoal(PerformanceDimension.TIME, PerformanceDimension.ALLOCATION, 1.2, -1);

		performanceModel = new ArrayList<SetPerformanceModel>();

		// Faster on Contains
		SetPerformanceModel arraySetModel = new SetPerformanceModel(SetCollectionType.NLP_ARRAYSET,
				new double[] { 10, 2 }, new double[] { 10, 1 }, new double[] { 10, 2 });

		performanceModel.add(arraySetModel);

		// Default
		SetPerformanceModel hashSetModel = new SetPerformanceModel(SetCollectionType.JDK_HASHSET,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 2 });

		performanceModel.add(hashSetModel);

		// Faster on iterate
		SetPerformanceModel gscollectionsModel = new SetPerformanceModel(SetCollectionType.GSCOLLECTIONS_UNIFIEDSET,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 1 });
		
		performanceModel.add(gscollectionsModel);
		
	}

	@Test
	public void testEmpiricalContextInitialization() throws Exception {
		
		SetEmpiricalPerformanceEvaluator evaluator = new SetEmpiricalPerformanceEvaluator();
		evaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(evaluator, SetCollectionType.JDK_HASHSET, goal, 10, 1);
		SetAllocationContext context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer, 10);

		context.createSet();

	}

	@Test
	public void testEmpiricalContextNLPChampion() throws Exception {

		int windowSize = 10;
		
		SetEmpiricalPerformanceEvaluator evaluator = new SetEmpiricalPerformanceEvaluator();
		evaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(evaluator, SetCollectionType.JDK_HASHSET, goal, windowSize, 0.0);
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
		
		SetEmpiricalPerformanceEvaluator evaluator = new SetEmpiricalPerformanceEvaluator();
		evaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(evaluator, SetCollectionType.JDK_HASHSET, goal, windowSize, 0.0);
		SetAllocationContextInfo context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer,
				windowSize);

		optimizer.setContext(context);

		buildGSCollectionsBestScenario(windowSize, context);

		optimizer.analyzeAndOptimize();
		Assert.assertEquals(SetCollectionType.GSCOLLECTIONS_UNIFIEDSET.toString(), context.getCurrentCollectionType());

	}

	private void buildGSCollectionsBestScenario(int windowSize, SetAllocationContextInfo context) {
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
	}

	@Test
	public void testWithSwitchManager() throws Exception {
		
		int windowSize = 10;
		
		SetEmpiricalPerformanceEvaluator evaluator = new SetEmpiricalPerformanceEvaluator();
		evaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(evaluator, SetCollectionType.JDK_HASHSET, goal, windowSize, 1);
		SetAllocationContextInfo context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer,
				windowSize);
		
		optimizer.setContext(context);
		
		SwitchManager manager = new SwitchManager();
		manager.addOptimizer(optimizer);
		manager.configureAndScheduleManager(1, 100, 100);
		
		buildGSCollectionsBestScenario(windowSize, context);
		
		RuntimeUtil.gc();
		Thread.sleep(200);
		
		Assert.assertEquals(SetCollectionType.GSCOLLECTIONS_UNIFIEDSET.toString(), context.getCurrentCollectionType());
		
		
	}
	
	@Test
	public void testBestOptionWithSwitchManager() throws Exception {
		
		int windowSize = 10;
		
		SetEmpiricalPerformanceEvaluator evaluator = new SetEmpiricalPerformanceEvaluator();
		evaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(evaluator, SetCollectionType.JDK_HASHSET, goal, windowSize, 1);
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
