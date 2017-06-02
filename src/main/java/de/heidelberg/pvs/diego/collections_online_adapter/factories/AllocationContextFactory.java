package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class AllocationContextFactory {
	

	/*
	 * LISTS
	 */
	public static ListAllocationContext buildListContext(CollectionTypeEnum type, String identifier) {

		return null;
	}

	/*
	 * SETS
	 */
	public static <E> SetAllocationContext buildSetContext(CollectionTypeEnum type, String identifier) {
		return null;
	}

	/*
	 * MAPS
	 */

	public static <K, V> MapAllocationContext buildMapContext(CollectionTypeEnum type, String identifier) {
		return null;

	}

}
