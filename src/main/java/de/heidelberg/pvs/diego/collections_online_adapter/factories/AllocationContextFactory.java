package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.InactiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InactiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ReactiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ReactiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ReactiveSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.RuleBasedListOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.STRuleBasedListOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.RuleBasedMapOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.STRuleBasedMapOptimizer;

public class AllocationContextFactory {

	// Default Values
	public static final int WINDOW_SIZE = 5;
	public static final int CONVERGENCE_RATE = 4;
	public static final int FULL_ANALYSIS_THRESHOLD = 100;
	public static final int SLEEPING_FREQUENCY = 10000;
	public static final int DIVERGENCE_ROUNDS_THRESHOLD = 2;

	/*
	 * LISTS
	 */
	public static ListAllocationContext buildListContext(CollectionTypeEnum type) {
		return buildListContext(type, "");
	}

	public static ListAllocationContext buildListContext(CollectionTypeEnum type, String identifier) {
		
		String windowSizeStr = System.getProperty("windowSize");
		Integer windowSize = windowSizeStr != null? Integer.parseInt(windowSizeStr) : WINDOW_SIZE;
		
		String convergencyRateStr = System.getProperty("convergencyRate");
		Integer convergencyRate = convergencyRateStr != null? Integer.parseInt(convergencyRateStr) : CONVERGENCE_RATE;
		
		String sleepingFrequencyStr = System.getProperty("sleepingFrequency");
		Integer sleepingFrequency = sleepingFrequencyStr != null? Integer.parseInt(sleepingFrequencyStr) : SLEEPING_FREQUENCY;
		

		// No list will be monitored
		if (System.getProperty("no-lists") != null) {
			return new InactiveListAllocationContext(type);
		}
		
		ListAllocationOptimizer optimizer;
		
		if(System.getProperty("single-thread") != null) {
			optimizer = new STRuleBasedListOptimizer(windowSize, convergencyRate);
		} else {
			optimizer = new RuleBasedListOptimizer(windowSize, convergencyRate);
			
		}


		ListAllocationContext context = new ReactiveListAllocationContext(type, optimizer, windowSize,
				FULL_ANALYSIS_THRESHOLD, sleepingFrequency, convergencyRate, DIVERGENCE_ROUNDS_THRESHOLD);

		// Print the log of the changes
		if (System.getProperty("log") != null) {

			String dir = System.getProperty("log");

			ListAllocationContext logContext = new LogListAllocationContext(context, identifier, dir);
			optimizer.setContext(logContext);
			return logContext;

		} else {
			optimizer.setContext(context);
			return context;
		}

	}

	/*
	 * SETS
	 */
	public static <E> SetAllocationContext buildSetContext(CollectionTypeEnum type) {
		return new ReactiveSetAllocationContext(type, WINDOW_SIZE, FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY,
				CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);
	}

	/*
	 * MAPS
	 */
	public static <K, V> MapAllocationContext buildMapContext(CollectionTypeEnum type) {

		MapAllocationOptimizer optimizer = new RuleBasedMapOptimizer(WINDOW_SIZE, CONVERGENCE_RATE);

		MapAllocationContext context = new ReactiveMapAllocationContext(type, optimizer, WINDOW_SIZE,
				FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);

		optimizer.setContext(context);

		return context;
	}

	/*
	 * MAPS
	 */
	public static <K, V> MapAllocationContext buildMapContext(CollectionTypeEnum type, String identifier) {

		// No maps will be monitored
		if (System.getProperty("no-maps") != null) {
			return new InactiveMapAllocationContext(type);
		}
		
		String windowSizeStr = System.getProperty("windowSize");
		Integer windowSize = windowSizeStr != null? Integer.parseInt(windowSizeStr) : WINDOW_SIZE;
		
		String convergencyRateStr = System.getProperty("convergencyRate");
		Integer convergencyRate = convergencyRateStr != null? Integer.parseInt(convergencyRateStr) : CONVERGENCE_RATE;
		
		String sleepingFrequencyStr = System.getProperty("sleepingFrequency");
		Integer sleepingFrequency = sleepingFrequencyStr != null? Integer.parseInt(sleepingFrequencyStr) : SLEEPING_FREQUENCY;
		
		
		MapAllocationOptimizer optimizer;
		if(System.getProperty("single-thread") != null) {
			optimizer = new STRuleBasedMapOptimizer(windowSize, convergencyRate);
		} else {
			optimizer = new RuleBasedMapOptimizer(windowSize, convergencyRate);
			
		}

		MapAllocationContext context = new ReactiveMapAllocationContext(type, optimizer, windowSize,
				FULL_ANALYSIS_THRESHOLD, sleepingFrequency, convergencyRate, DIVERGENCE_ROUNDS_THRESHOLD);

		if (System.getProperty("log") != null) {
			String dir = System.getProperty("log");

			MapAllocationContext logcontext = new LogMapAllocationContext(context, identifier, dir);
			optimizer.setContext(logcontext);
			return logcontext;
		} else {
			optimizer.setContext(context);
			return context;
		}

	}

}
