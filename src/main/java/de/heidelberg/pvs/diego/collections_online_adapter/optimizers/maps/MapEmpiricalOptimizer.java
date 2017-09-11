package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.collections.api.block.predicate.primitive.ObjectDoublePredicate;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapMetrics;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.EmpiricalPerformanceEvaluator;

public class MapEmpiricalOptimizer implements MapAllocationOptimizer {

	MapAllocationContext context;

	List<MapMetrics> collectionsState;

	private double finishedRatio;

	private MapCollectionType defaultType;

	private EmpiricalPerformanceEvaluator<MapMetrics, MapCollectionType> evaluator;

	public MapEmpiricalOptimizer(EmpiricalPerformanceEvaluator<MapMetrics, MapCollectionType> evaluator, 
			MapCollectionType defaultType, int windowSize, double finishedRatio) {
		super();
		
		this.defaultType = defaultType;
		this.collectionsState = new ArrayList<MapMetrics>(windowSize);
		this.evaluator = evaluator;
		
		if(finishedRatio == 0.0) {
			this.finishedRatio = 0;
		} else {
			this.finishedRatio = (int) (windowSize / finishedRatio);
			
		}
	}

	@Override
	public void analyzeAndOptimize() {

		int amountFinishedCollections = 0;

		// Checks how many collections have finished
		for (MapMetrics metric : collectionsState) {
			if (metric.hasCollectionFinished())
				amountFinishedCollections++;
		}

		// Only analyze it when
		if (amountFinishedCollections >= finishedRatio) {

			// Get candidates from the major performance goal
			MutableObjectDoubleMap<MapCollectionType> majorCandidates = getCandidates(
					PerformanceGoal.INSTANCE.majorDimension, PerformanceGoal.INSTANCE.minImprovement);

			// Get candidates that fulfill the minor performance goal
			MutableObjectDoubleMap<MapCollectionType> minorCandidates = getCandidates(
					PerformanceGoal.INSTANCE.minorDimension, PerformanceGoal.INSTANCE.maxPenalty);

			@SuppressWarnings("serial")
			MutableObjectDoubleMap<MapCollectionType> bestOptions = majorCandidates
					.select(new ObjectDoublePredicate<MapCollectionType>() {
						@Override
						public boolean accept(MapCollectionType key, double value) {
							return minorCandidates.containsKey(key);
						}
					});

			// Get the top implementation - Finding the minimum value
			// FIXME: Find a better implementation for this
			double min = Double.MAX_VALUE;
			MapCollectionType champion = defaultType;
			for (MapCollectionType type : bestOptions.keySet()) {
				double perf = bestOptions.get(type);
				if (perf < min) {
					champion = type;
					min = perf;
				}
			}

			context.updateCollectionType(champion);

			// Reset
			collectionsState.clear();
		}

	}

	private MutableObjectDoubleMap<MapCollectionType> getCandidates(PerformanceDimension performanceDimension,
			double factor) {

		// Gets the performance prediction for each instance
		MutableObjectDoubleMap<MapCollectionType> majorPerformance = evaluator
				.predictPerformance(collectionsState, PerformanceGoal.INSTANCE.majorDimension);

		// Gets the default performance
		double defaultPerformance = majorPerformance.get(defaultType);

		// Selects only the implementations with better performance
		@SuppressWarnings("serial")
		MutableObjectDoubleMap<MapCollectionType> candidates = majorPerformance
				.select(new ObjectDoublePredicate<MapCollectionType>() {
					@Override
					public boolean accept(MapCollectionType object, double value) {
						return defaultPerformance / value > factor;
					}
				});

		return candidates;
	}

	@Override
	public <K, V> Map<K, V> createMonitor(Map<K, V> map) {
		MapMetrics metrics = new MapMetrics(new WeakReference<Map<K, V>>(map));
		collectionsState.add(metrics);
		return new MapActiveFullMonitor<>(map, metrics);
	}

	@Override
	public void setContext(MapAllocationContext context) {
		this.context = context;

	}

}
