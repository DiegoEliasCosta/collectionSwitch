package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.EmpiricalMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.SwitchManager;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapEmpiricalOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapEmpiricalPerformanceEvaluator;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapPerformanceModel;
import jlibs.core.lang.RuntimeUtil;

public class MapEmpiricalAllocationContextTest {
	
	private List<MapPerformanceModel> performanceModel;

	@Before
	public void setup() {

		performanceModel = new ArrayList<MapPerformanceModel>();

		// Faster on Contains
		MapPerformanceModel arraySetModel = new MapPerformanceModel(MapCollectionType.NLP_ARRAYMAP,
				new double[] { 10, 1 }, new double[] { 10, 2 }, new double[] { 10, 2 });

		performanceModel.add(arraySetModel);

		// Default
		MapPerformanceModel hashSetModel = new MapPerformanceModel(MapCollectionType.JDK_HASHMAP,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 2 });

		performanceModel.add(hashSetModel);

		// Faster on iterate
		MapPerformanceModel gscollectionsModel = new MapPerformanceModel(MapCollectionType.GSCOLLECTIONS_UNIFIEDMAP,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 1 });
		
		performanceModel.add(gscollectionsModel);
		
	}
	
	@Test
	public void testContextInitialization() throws Exception {
		
		int windowSize = 10;
		
		MapEmpiricalPerformanceEvaluator evaluator = new MapEmpiricalPerformanceEvaluator();
		evaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);
		
		MapAllocationOptimizer optimizer = new MapEmpiricalOptimizer(evaluator, MapCollectionType.JDK_HASHMAP, 10, 1);
		MapAllocationContext context = new EmpiricalMapAllocationContext(MapCollectionType.JDK_HASHMAP, optimizer , windowSize);
		
		optimizer.setContext(context);
		Assert.assertNotNull(context.createMap());
		
	}
	
	@Test
	public void testEmpiricalContextNLPChampion() throws Exception {
		
		int windowSize = 10;
		
		MapEmpiricalPerformanceEvaluator evaluator = new MapEmpiricalPerformanceEvaluator();
		evaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);
		
		MapAllocationOptimizer optimizer = new MapEmpiricalOptimizer(evaluator, MapCollectionType.JDK_HASHMAP, 10, 0);
		MapAllocationContextInfo context = new EmpiricalMapAllocationContext(MapCollectionType.JDK_HASHMAP, optimizer , windowSize);
		
		optimizer.setContext(context);
		
		buildNLPBestScenario(windowSize, context);
		
		optimizer.analyzeAndOptimize();
		
		MapCollectionType currentCollectionType = context.getCurrentCollectionType();
		Assert.assertEquals(MapCollectionType.NLP_ARRAYMAP, currentCollectionType);
		
	}
	
	
	@Test
	public void testWithSwitchMultipleStages() throws Exception {
		
		int windowSize = 10;
		
		PerformanceGoal.INSTANCE.majorDimension = PerformanceDimension.TIME;
		PerformanceGoal.INSTANCE.minImprovement = 1.1;
		
		MapEmpiricalPerformanceEvaluator evaluator = new MapEmpiricalPerformanceEvaluator();
		evaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);
		
		MapAllocationOptimizer optimizer = new MapEmpiricalOptimizer(evaluator, MapCollectionType.JDK_HASHMAP, 10, 1);
		MapAllocationContextInfo context = new EmpiricalMapAllocationContext(MapCollectionType.JDK_HASHMAP, optimizer , windowSize);
		
		optimizer.setContext(context);
		
		SwitchManager manager = new SwitchManager();
		manager.addOptimizer(optimizer);
		manager.configureAndScheduleManager(1, 100, 100);

		buildNLPBestScenario(10, context);

		RuntimeUtil.gc();
		Thread.sleep(200);

		Assert.assertEquals(MapCollectionType.NLP_ARRAYMAP, context.getCurrentCollectionType());

		bestScenarioGSCollections(context, 10);

		RuntimeUtil.gc();
		Thread.sleep(200);

		Assert.assertEquals(MapCollectionType.GSCOLLECTIONS_UNIFIEDMAP, context.getCurrentCollectionType());
		
		bestScenarioHashSet(context, 10);

		RuntimeUtil.gc();
		Thread.sleep(200);

		Assert.assertEquals(ListCollectionType.JDK_ARRAYLIST, context.getCurrentCollectionType());

	}

	private void bestScenarioHashSet(MapAllocationContextInfo context, int windowSize) {
		for (int i = 0; i < windowSize; i++) {
			Map<Integer, Integer> createSet = context.createMap();

			for (int j = 0; j < 100; j++) {
				createSet.put(j, j);
			}

		}
		
	}

	private void bestScenarioGSCollections(MapAllocationContextInfo context, int windowSize) {
		for (int i = 0; i < windowSize; i++) {
			Map<Integer, Integer> map = context.createMap();

			for (int j = 0; j < 100; j++) {
				map.put(j, j);
			}

			// Iteration is faster on GSCollections
			for (int j = 0; j < 10; j++) {
				
				for(Integer key: map.keySet()) {
					key += 10;
				}
				
			}
			
			map = null;
		}
		
	}

	private void buildNLPBestScenario(int windowSize, MapAllocationContextInfo context) {
		
		for (int i = 0; i < windowSize; i++) {
			Map<Integer, Integer> map = context.createMap();

			for (int j = 0; j < 100; j++) {
				map.put(j, j);
			}

			// Contains is faster on NLP
			for (int j = 0; j < 10; j++) {
				Integer k = map.get(j);
				k += 10;
			}
			
			map = null;
		}
	}

}
