package de.heidelberg.pvs.diego.collectionswitch.optimizers.sets;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.collections.api.block.predicate.primitive.ObjectDoublePredicate;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;

import de.heidelberg.pvs.diego.collectionswitch.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.SetCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collectionswitch.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collectionswitch.monitors.sets.SetActiveFullMonitor;
import de.heidelberg.pvs.diego.collectionswitch.monitors.sets.SetMetrics;

public class SetEmpiricalOptimizer implements SetAllocationOptimizer {

	SetAllocationContext context;

	List<SetMetrics> collectionsState;

	private int finishedRatio;
	private SetCollectionType defaultType;

	private SetEmpiricalPerformanceEvaluator evaluator;
	
	private PerformanceGoal goal;

	public SetEmpiricalOptimizer(SetEmpiricalPerformanceEvaluator evaluator, SetCollectionType defaultType, PerformanceGoal goal,
			int windowSize, double finishedRatio) {
		super();
		this.defaultType = defaultType;
		this.collectionsState = new ArrayList<SetMetrics>(windowSize);
		this.evaluator = evaluator;
		this.goal = goal;

		if (finishedRatio == 0.0) {
			this.finishedRatio = 0;
		} else {
			this.finishedRatio = (int) (windowSize / finishedRatio);

		}

	}

	@Override
	public <E> Set<E> createMonitor(Set<E> set) {
		SetMetrics state = new SetMetrics(new WeakReference<Set<E>>(set));
		collectionsState.add(state);
		return new SetActiveFullMonitor<E>(set, state);
	}

	@SuppressWarnings("serial")
	@Override
	public void analyzeAndOptimize() {

		int amountFinishedCollections = 0;

		// Checks how many collections have finished
		for (SetMetrics metric : collectionsState) {
			if (metric.hasCollectionFinished())
				amountFinishedCollections++;
		}

		// Only analyze it when
		if (amountFinishedCollections >= finishedRatio) {

			// Get candidates from the major performance goal
			MutableObjectDoubleMap<SetCollectionType> majorCandidates = getCandidates(
					goal.majorDimension, goal.minImprovement);
		
			MutableObjectDoubleMap<SetCollectionType> bestOptions;
			
			// FIXME: This should be implemented in a better way
			if(goal.maxPenalty > 0) {

			// Get candidates that fulfill the minor performance goal
			MutableObjectDoubleMap<SetCollectionType> minorCandidates = getCandidates(
					goal.minorDimension, goal.maxPenalty);

			bestOptions = majorCandidates
					.select(new ObjectDoublePredicate<SetCollectionType>() {
						@Override
						public boolean accept(SetCollectionType key, double value) {
							return minorCandidates.containsKey(key);
						}
					});

			} else {
				bestOptions = majorCandidates;
			}
			
			
			
			// Get the top implementation - Finding the minimum value
			// FIXME: Find a better implementation for this
			double min = Double.MAX_VALUE;
			SetCollectionType champion = defaultType;
			for (SetCollectionType type : bestOptions.keySet()) {
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

	private MutableObjectDoubleMap<SetCollectionType> getCandidates(PerformanceDimension performanceDimension,
			double factor) {

		// Gets the performance prediction for each instance
		MutableObjectDoubleMap<SetCollectionType> majorPerformance = evaluator.predictPerformance(collectionsState,
				performanceDimension);

		// Gets the default performance
		double defaultPerformance = majorPerformance.get(defaultType);

		// Selects only the implementations with better performance
		@SuppressWarnings("serial")
		MutableObjectDoubleMap<SetCollectionType> candidates = majorPerformance
				.select(new ObjectDoublePredicate<SetCollectionType>() {
					@Override
					public boolean accept(SetCollectionType object, double value) {
						return defaultPerformance / value > factor;
					}
				});

		return candidates;
	}

	@Override
	public void setContext(SetAllocationContext context) {
		this.context = context;

	}

}
