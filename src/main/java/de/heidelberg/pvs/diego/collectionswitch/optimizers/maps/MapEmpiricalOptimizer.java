package de.heidelberg.pvs.diego.collectionswitch.optimizers.maps;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.collections.api.block.predicate.primitive.ObjectDoublePredicate;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;

import de.heidelberg.pvs.diego.collectionswitch.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.MapCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collectionswitch.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collectionswitch.monitors.maps.MapActiveFullMonitor;
import de.heidelberg.pvs.diego.collectionswitch.monitors.maps.MapMetrics;

public class MapEmpiricalOptimizer implements MapAllocationOptimizer {

	MapAllocationContext context;

	List<MapMetrics> collectionsState;

	private double finishedRatio;

	private MapCollectionType defaultType;

	private MapEmpiricalPerformanceEvaluator evaluator;
	
	private PerformanceGoal goal;

	public MapEmpiricalOptimizer(MapEmpiricalPerformanceEvaluator evaluator, 
			MapCollectionType defaultType, PerformanceGoal goal, int windowSize, double finishedRatio) {
		super();
		
		this.defaultType = defaultType;
		this.collectionsState = new ArrayList<MapMetrics>(windowSize);
		this.evaluator = evaluator;
		this.goal = goal;
		
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
					goal.majorDimension, goal.minImprovement);

			MutableObjectDoubleMap<MapCollectionType> bestOptions;
			
			if(goal.maxPenalty > 0) {
			
			// Get candidates that fulfill the minor performance goal
			MutableObjectDoubleMap<MapCollectionType> minorCandidates = getCandidates(
					goal.minorDimension, goal.maxPenalty);

			bestOptions = majorCandidates
					.select(new ObjectDoublePredicate<MapCollectionType>() {
						@Override
						public boolean accept(MapCollectionType key, double value) {
							return minorCandidates.containsKey(key);
						}
					});
			
			} else {
				bestOptions = majorCandidates;
			}

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
				.predictPerformance(collectionsState, performanceDimension);

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
		return new MapActiveFullMonitor<K, V>(map, metrics);
	}

	@Override
	public void setContext(MapAllocationContext context) {
		this.context = context;

	}

}
