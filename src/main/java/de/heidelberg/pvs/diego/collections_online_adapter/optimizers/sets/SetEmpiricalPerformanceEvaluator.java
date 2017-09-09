package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.List;
import java.util.Map;

import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectDoubleHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.SetCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetMetrics;

public class SetEmpiricalPerformanceEvaluator {

	private static Map<PerformanceDimension, List<SetPerformanceModel>> empiricalModel = new UnifiedMap<>();

	public static void addEmpiricalModel(PerformanceDimension dimension, List<SetPerformanceModel> performanceModel) {
		empiricalModel.put(dimension, performanceModel);
	}

	public static MutableObjectDoubleMap<SetCollectionType> predictPerformance(List<SetMetrics> collectionsState,
			PerformanceDimension dimension) {

		MutableObjectDoubleMap<SetCollectionType> performanceResult = new ObjectDoubleHashMap<>(empiricalModel.size());

		List<SetPerformanceModel> models = empiricalModel.get(dimension);

		// For each monitored collection
		for (SetMetrics state : collectionsState) {

			// For each model
			for (SetPerformanceModel model : models) {

				// Accumulate the performance of each implementation
				performanceResult.addToValue(model.getType(),
						model.calculatePerformance(state.getSize(), 1, state.getContainsOp(), state.getIterationOp()));
			}

		}

		return performanceResult;

	}

}
