package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface AllocationContextUpdatable {
	
	void optimizeInitialCapacity(int analyzedInitialCapacity);
	
	void noInitialCapacityConvergence();
	
	void optimizeCollectionType(CollectionTypeEnum collecton);
	
	void noCollectionTypeConvergence();

}
