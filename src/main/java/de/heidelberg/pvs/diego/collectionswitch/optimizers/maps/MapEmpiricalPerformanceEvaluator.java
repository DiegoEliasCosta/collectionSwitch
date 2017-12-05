package de.heidelberg.pvs.diego.collectionswitch.optimizers.maps;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectDoubleHashMap;

import de.heidelberg.pvs.diego.collectionswitch.context.MapCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collectionswitch.monitors.maps.MapMetrics;

public class MapEmpiricalPerformanceEvaluator {
	
	private Map<PerformanceDimension, List<MapPerformanceModel>> empiricalModel = 
			new UnifiedMap<PerformanceDimension, List<MapPerformanceModel>>();

	public void addEmpiricalModel(PerformanceDimension dimension, List<MapPerformanceModel> performanceModel) {
		// FIXME: This method REWRITES the empirical model every time
		// We need to find a better way of handling this
		empiricalModel.put(dimension, performanceModel);
	}

	public MutableObjectDoubleMap<MapCollectionType> predictPerformance(List<MapMetrics> collectionsState,
			PerformanceDimension dimension) {

		MutableObjectDoubleMap<MapCollectionType> performanceResult = new ObjectDoubleHashMap<MapCollectionType>(empiricalModel.size());

		List<MapPerformanceModel> models = empiricalModel.getOrDefault(dimension, Collections.EMPTY_LIST);

		// For each monitored collection
		for (MapMetrics state : collectionsState) {

			// For each model
			for (MapPerformanceModel model : models) {

				// Accumulate the performance of each implementation
				performanceResult.addToValue(model.getType(), model.calculatePerformance(state));
			}
		}

		return performanceResult;

	}

}
