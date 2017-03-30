package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveSetAllocationContext;

public class AllocationContextFactory {
	
	// Default Values
	public static final int WINDOW_SIZE = 10;
	public static final int CONVERGENCE_RATE = 7;
	public static final int FULL_ANALYSIS_THRESHOLD = 100;
	public static final int SLEEPING_FREQUENCY = 10;
	public static final int DIVERGENCE_ROUNDS_THRESHOLD = 2;

	/*
	 * LISTS
	 */
	public static ListAllocationContext buildListContext(CollectionTypeEnum type) {
		return new AdaptiveListAllocationContext(type, WINDOW_SIZE, FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);
	}
	

	/*
	 * SETS
	 */
	public static <E> SetAllocationContext buildSetContext(CollectionTypeEnum type) {
		return new AdaptiveSetAllocationContext(type, WINDOW_SIZE, FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);
	}
	
	/*
	 * MAPS
	 */
	public static <K, V> MapAllocationContext buildMapContext(CollectionTypeEnum type) {
		return new AdaptiveMapAllocationContext(type, WINDOW_SIZE, FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);
	}
	

}
