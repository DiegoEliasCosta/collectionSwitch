package de.heidelberg.pvs.diego.collections_online_adapter.context;

public class ContextFactory {
	
	public static ListAllocationContext getListContext(CollectionTypeEnum collectionType, int specifiedInitialCapacity) {
		return new FirstSamplesAllocationContext(collectionType, specifiedInitialCapacity);
	}

}
