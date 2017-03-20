package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface ListAllocationContextUpdatable {
	
	void updateCollectionType(CollectionTypeEnum collectionTypeEnum);
	
	void updateInitialCapacity(int analyzedInitialCapacity);

}
