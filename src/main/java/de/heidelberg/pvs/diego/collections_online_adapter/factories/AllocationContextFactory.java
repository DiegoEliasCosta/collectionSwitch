package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.Properties;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ReactiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ReactiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ReactiveSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.RuleBasedListOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.RuleBasedMapOptimizer;

public class AllocationContextFactory {

	// Default Values
	public static final int WINDOW_SIZE = 10;
	public static final int CONVERGENCE_RATE = 8;
	public static final int FULL_ANALYSIS_THRESHOLD = 100;
	public static final int SLEEPING_FREQUENCY = 10000;
	public static final int DIVERGENCE_ROUNDS_THRESHOLD = 2;
	
	public static final boolean LOG = true;

	/*
	 * LISTS
	 */
	public static ListAllocationContext buildListContext(CollectionTypeEnum type) {

		ListAllocationOptimizer optimizer = new RuleBasedListOptimizer(WINDOW_SIZE, CONVERGENCE_RATE);

		ListAllocationContext context = new ReactiveListAllocationContext(type, optimizer, WINDOW_SIZE,
				FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);

		optimizer.setContext(context);
		return context;
	}

	public static ListAllocationContext buildListContext(CollectionTypeEnum type, String identifier) {
		
		ListAllocationOptimizer optimizer = new RuleBasedListOptimizer(WINDOW_SIZE, CONVERGENCE_RATE);

		ListAllocationContext context = new ReactiveListAllocationContext(type, optimizer, WINDOW_SIZE,
				FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);

		if(System.getProperty("log") != null) {
			
			ListAllocationContext logContext = new LogListAllocationContext(context, identifier);
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

		MapAllocationOptimizer optimizer = new RuleBasedMapOptimizer(WINDOW_SIZE, CONVERGENCE_RATE);

		MapAllocationContext context = new ReactiveMapAllocationContext(type, optimizer, WINDOW_SIZE,
				FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);
		
		if(System.getProperty("log") != null) {
			MapAllocationContext logcontext = new LogMapAllocationContext(context, identifier);
			optimizer.setContext(logcontext);
			return logcontext;
		} else {
			optimizer.setContext(context);
			return context;
		}
		
	}

}
