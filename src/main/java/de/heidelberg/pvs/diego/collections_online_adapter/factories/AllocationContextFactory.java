package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveSetAllocationContext;

public class AllocationContextFactory {
	
	public static <E> ListAllocationContext<E> buildListContext(CollectionTypeEnum type) {
		return new AdaptiveListAllocationContext<>(type);
	}
	
	public static <E> SetAllocationContext<E> buildSetContext(CollectionTypeEnum type) {
		return new AdaptiveSetAllocationContext<>(type);
	}
	
	public static <K, V> MapAllocationContext<K, V> buildMapContext(CollectionTypeEnum type) {
		return new AdaptiveMapAllocationContext<>(type);
	}

}
