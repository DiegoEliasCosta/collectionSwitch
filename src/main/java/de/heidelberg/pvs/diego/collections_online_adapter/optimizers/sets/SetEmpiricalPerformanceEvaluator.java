package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import java.util.List;
import java.util.Map;

import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectDoubleHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetMetrics;

public class SetEmpiricalPerformanceEvaluator {

	private Map<PerformanceDimension, List<SetPerformanceModel>> setEmpiricalModel = new UnifiedMap<>();

	public void addEmpiricalModel(PerformanceDimension dimension, List<SetPerformanceModel> performanceModel) {
		// FIXME: This method REWRITES the empirical model every time
		// We need to find a better way of handling this
		setEmpiricalModel.put(dimension, performanceModel);
	}
	
	public MutableObjectDoubleMap<SetCollectionType> predictPerformance(List<SetMetrics> collectionsState,
			PerformanceDimension dimension) {

		MutableObjectDoubleMap<SetCollectionType> performanceResult = new ObjectDoubleHashMap<>(setEmpiricalModel.size());

		List<SetPerformanceModel> models = setEmpiricalModel.get(dimension);
		
		// For each monitored collection
		for (SetMetrics state : collectionsState) {

			// For each model
			for (SetPerformanceModel model : models) {

				// Accumulate the performance of each implementation
				performanceResult.addToValue(model.getType(),
						model.calculatePerformance(state));
			}
		}

		return performanceResult;

	}

}
