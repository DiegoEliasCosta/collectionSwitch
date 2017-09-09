package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.EmpiricalSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.SetCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetEmpiricalOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetEmpiricalPerformanceEvaluator;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetPerformanceModel;

public class SetEmpiricalAllocationContextTest {

	@Before
	public void setup() {

		PerformanceGoal.INSTANCE.init(PerformanceDimension.TIME, PerformanceDimension.ALLOCATION, 0.9, 2);

	}

	@Test
	public void testEmpiricalContextInitialization() throws Exception {

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(SetCollectionType.JDK_HASHSET, 10, 1);
		SetAllocationContext context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer, 10);

		context.createSet();

	}

	@Test
	public void testEmpiricalContextBasicOptimization() throws Exception {

		int windowSize = 10;

		List<SetPerformanceModel> performanceModel = new ArrayList<>();

		SetPerformanceModel arraySetModel = new SetPerformanceModel(SetCollectionType.NLP_ARRAYSET,
				new double[] { 10, 2 }, new double[] { 10, 2 }, new double[] { 10, 2 });

		performanceModel.add(arraySetModel);

		SetPerformanceModel hashSetModel = new SetPerformanceModel(SetCollectionType.JDK_HASHSET,
				new double[] { 10, 3 }, new double[] { 10, 3 }, new double[] { 10, 3 });

		performanceModel.add(hashSetModel);

		SetEmpiricalPerformanceEvaluator.addEmpiricalModel(PerformanceDimension.TIME, performanceModel);

		SetAllocationOptimizer optimizer = new SetEmpiricalOptimizer(SetCollectionType.JDK_HASHSET, windowSize, 0.0);
		SetAllocationContextInfo context = new EmpiricalSetAllocationContext(SetCollectionType.JDK_HASHSET, optimizer,
				windowSize);

		optimizer.setContext(context);

		for (int i = 0; i < windowSize; i++) {
			Set<Object> createSet = context.createSet();

			for (int j = 0; j < 100; j++) {
				createSet.add(j);
			}

			for (int j = 0; j < 10; j++) {
				createSet.contains(j);
			}
		}

		optimizer.analyzeAndOptimize();
		Assert.assertEquals(SetCollectionType.NLP_ARRAYSET.toString(), context.getCurrentCollectionType());

	}

}
